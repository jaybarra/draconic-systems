(ns ds.games.battleship-test
  (:require
   [clojure.test :refer :all]
   [ds.games.battleship :as game])
  (:import
   [clojure.lang ExceptionInfo]))

(deftest coord->index-test
  (are [coord idx] (= idx (game/coord->index coord))
    [:a 0] 0
    [:b 0] 10
    [:a 5] 5
    [:b 5] 15
    [:k 9] 109))

(deftest index->coord-test
  (are [idx coord] (= coord (game/index->coord idx))
    0 [:a 0]
    10 [:b 0]
    5 [:a 5]
    15 [:b 5]
    109 [:k 9]))

(deftest hit-test
  (let [board (game/place-ship game/grid game/cruiser [:b 0] :down)]
    (is (false? (game/hit? board [:a 0])))
    (is (true? (game/hit? board [:b 0])))))

(deftest place-ship-test
  (let [board (game/place-ship game/grid
                               game/carrier
                               [:a 0]
                               :right)]
    (is (every? #(= :carrier (nth board %)) (range 5)))
    (is (= :empty (nth board 5))))

  (testing "Boundary checking"
    (are [ship coord orientation]
        (thrown-with-msg? ExceptionInfo
                          #"Invalid placement"
                          (game/place-ship game/grid
                                           ship
                                           coord
                                           orientation))
      game/carrier [:a 9] :right
      game/cruiser [:a 0] :left
      game/submarine [:k 0] :down
      game/destroyer [:a 0] :up)))

(deftest ^:kaocha/pending print-board-test
  (let [board (game/place-ship game/grid game/cruiser [:c 3] :down)]
    (game/print-board! board)))

(deftest gameover-test
  (is (true? (game/game-over? game/grid)))
  (is (false? (game/game-over?
               (game/place-ship game/grid
                                game/carrier
                                [:a 0]
                                :right)))))

(deftest generate-positions-test
  (is (= [[:a 0] [:a 1]]
         (game/generate-coords game/destroyer [:a 0] :right)))
  (is (= [[:c 4] [:c 3] [:c 2]]
         (game/generate-coords game/cruiser [:c 4] :left)))
  (is (= [[:e 2] [:f 2] [:g 2]]
         (game/generate-coords game/submarine [:e 2] :down)))
  (is (= [[:e 7] [:d 7] [:c 7] [:b 7] [:a 7]]
         (game/generate-coords game/carrier [:e 7] :up))))

(deftest validate-no-collisions-test
  (let [board (game/place-ship game/grid game/cruiser [:c 3] :down)]
    (is (thrown-with-msg? ExceptionInfo
                          #"Space already occupied"
                          (game/place-ship board game/cruiser [:c 3] :down)))))
