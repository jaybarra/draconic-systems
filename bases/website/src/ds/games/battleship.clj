(ns ds.games.battleship
  (:require
   [clojure.pprint]
   [clojure.spec.alpha :as spec]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Specs
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(spec/def ::ship-type keyword)
(spec/def ::length (spec/and pos? int?))
(spec/def ::ship (spec/keys :req [::ship-type]
                            :req-un [::length]))

(spec/def ::coord (spec/tuple keyword?
                              (spec/and #(not (neg? %))
                                        int?)))

(def orientation? #{:up :down :left :right})
(spec/def ::orientation orientation?)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def lower-case-letters
  (map (comp keyword str char) (range (int \a) (inc (int \z)))))

(def grid (vec (for [x (take 10 lower-case-letters)
                     y (range 10)] :empty)))

(def kw->int (comp int first char-array name))

(defn kw->row
  "Converts a grid keyword to an integer.
   :a = 0"
  [x]
  (- (kw->int x) 97))

(defn row-int->kw
  [x]
  (-> x
      (+ 97)
      char
      str
      keyword))

(defn coord->index
  [[r c]]
  (+ c (* (kw->row r) 10)))

(defn index->coord
  [i]
  (let [col (mod i 10)
        row (/ (- i col) 10)]
    [(row-int->kw row) col]))

(def carrier {::ship-type :carrier
              :length 5})
(def battleship {::ship-type :battleship
                 :length 4})
(def cruiser {::ship-type :cruiser
              :length 3})
(def submarine {::ship-type :submarine
                :length 3})
(def destroyer {::ship-type :destroyer
                :length 2})

(def not-in? (complement contains?) )

(defn hit?
  [board coord]
  {:pre [(spec/valid? ::coord coord)]}
  (not-in? #{:empty :hit :miss} (nth board (coord->index coord))))

(defn print-board!
  [board]
  (println)
  (doseq [row (partition 10 board)]
    (println (map #(case %
                     :empty " - "
                     :hit "[x]"
                     :miss " o "
                     ;; else
                     "[ ]")
                  row))))

(defn game-over?
  [board]
  (->> board
       (remove #(= :empty %))
       (remove #(= :miss %))
       (remove #(= :hit %))
       count
       zero?))

(defn validate-placement!
  [{length :length} coord orientation]
  (when (or (and (= :right orientation)
                 (> (+ (second coord) length) 10))
            (and (= :left orientation)
                 (neg? (- (second coord) length)))
            (and (= :up orientation)
                 (neg? (- (kw->row (first coord)) length)))
            (and (= :down orientation)
                 (> (+ (kw->row (first coord)) length) 10)))

    (throw (ex-info "Invalid placement"
                    {:cause "Ship extends outside game board"}))))

(defn- right-coords
  [coord length]
  (map
   (fn [i]
     [(first coord)
      (+ i (second coord))])
   (range length)))

(defn- left-coords
  [coord length]
  (map
   (fn [i]
     [(first coord)
      (- (second coord) i)])
   (range length)))

(defn- down-coords
  [coord length]
  (map
   (fn [i]
     [(row-int->kw (+ (kw->row (first coord)) i))

      (second coord)])
   (range length)))

(defn- up-coords
  [coord length]
  (map
   (fn [i]
     [(row-int->kw (- (kw->row (first coord)) i))
      (second coord)])
   (range length)))

(defn generate-coords
  [ship coord orientation]
  (let [{:keys [length]} ship]
    (case orientation
      :right (right-coords coord (:length ship))
      :left (left-coords coord (:length ship))
      :down (down-coords coord (:length ship))
      :up (up-coords coord (:length ship)))))

(defn validate-no-collision!
  [board ship coord orientation]
  (let [positions (generate-coords ship coord orientation)]
    (when-not (every? #(= :empty (nth board (coord->index %)))
                      positions)
      (throw (ex-info "Space already occupied"
                      {:cause "Collision"})))))
(defn place-ship
  "Place a ship on a board and return the new board.
   If the ship cannot be placed an exception will be thrown."
  [board ship coord orientation]
  {:pre [(spec/valid? ::ship ship)
         (spec/valid? ::coord coord)
         (spec/valid? ::orientation orientation)]}
  (validate-placement! ship coord orientation)
  (validate-no-collision! board ship coord orientation)
  (loop [coords (generate-coords ship coord orientation)
         b board]
    (if-not (seq coords)
      b
      (recur (rest coords)
             (assoc-in b [(coord->index (first coords))] (::ship-type ship))))))

(comment
  (def sample-game (-> grid
                       (place-ship submarine [:a 0] :right)
                       (place-ship submarine [:b 0] :down)))

  (print-board! sample-game)

  (game-over? grid)
  (game-over? sample-game)

  (kw->row :a)
  (row-int->kw 0)

  (hit? sample-game [:a 0])
  (hit? sample-game [:b 1]))
