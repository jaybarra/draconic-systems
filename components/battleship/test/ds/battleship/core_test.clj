(ns ds.battleship.core-test
  (:require
   [clojure.test :as test :refer :all]
   [ds.battleship.core :as battleship])
  (:import
   [clojure.lang ExceptionInfo]))

(deftest coord->index-test
  (are [coord idx] (= idx (battleship/coord->index coord))
    [:a 0] 0
    [:b 0] 10
    [:a 5] 5
    [:b 5] 15
    [:k 9] 109))

(deftest index->coord-test
  (are [idx coord] (= coord (battleship/index->coord idx))
    0 [:a 0]
    10 [:b 0]
    5 [:a 5]
    15 [:b 5]
    109 [:k 9]))

(deftest hit-test
  (let [board (battleship/place-ship battleship/grid battleship/cruiser [:b 0] :down)]
    (is (false? (battleship/hit? board [:a 0])))
    (is (true? (battleship/hit? board [:b 0])))))

(deftest place-ship-test
  (let [board (battleship/place-ship battleship/grid
                                     battleship/carrier
                                     [:a 0]
                                     :right)]
    (is (every? #(= :carrier (nth board %)) (range 5)))
    (is (= :empty (nth board 5))))

  (testing "Boundary checking"
    (are [ship coord orientation]
         (thrown-with-msg? ExceptionInfo
                           #"Invalid placement"
                           (battleship/place-ship battleship/grid
                                                  ship
                                                  coord
                                                  orientation))
      battleship/carrier [:a 9] :right
      battleship/cruiser [:a 0] :left
      battleship/submarine [:k 0] :down
      battleship/destroyer [:a 0] :up)))

(deftest gameover-test
  (is (true? (battleship/game-over? battleship/grid)))
  (is (false? (battleship/game-over?
               (battleship/place-ship battleship/grid
                                      battleship/carrier
                                      [:a 0]
                                      :right)))))

(deftest generate-positions-test
  (is (= [[:a 0] [:a 1]]
         (battleship/generate-coords battleship/destroyer [:a 0] :right)))
  (is (= [[:c 4] [:c 3] [:c 2]]
         (battleship/generate-coords battleship/cruiser [:c 4] :left)))
  (is (= [[:e 2] [:f 2] [:g 2]]
         (battleship/generate-coords battleship/submarine [:e 2] :down)))
  (is (= [[:e 7] [:d 7] [:c 7] [:b 7] [:a 7]]
         (battleship/generate-coords battleship/carrier [:e 7] :up))))

(deftest validate-no-collisions-test
  (let [board (battleship/place-ship battleship/grid battleship/cruiser [:c 3] :down)]
    (is (thrown-with-msg? ExceptionInfo
                          #"Space already occupied"
                          (battleship/place-ship board battleship/cruiser [:c 3] :down)))))
