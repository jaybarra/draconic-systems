(ns ds.games.damn-and-blast-test
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.spec.test.alpha :as stest]
   [clojure.test :refer :all]
   [ds.games.damn-and-blast :as game]))

(deftest full-deck-test
  (is (= 52 (count (set game/full-deck))))

  (testing "each card is valid"
    (is (true? (every? #(spec/valid? ::game/card %) game/full-deck)))))
