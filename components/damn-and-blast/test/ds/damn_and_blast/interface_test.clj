(ns ds.damn-and-blast.interface-test
  (:require
   [clojure.test :as test :refer :all]
   [clojure.spec.alpha :as spec]
   [clojure.spec.test.alpha :as stest]
   [clojure.test :refer :all]
   [ds.damn-and-blast.interface :as damn-and-blast]))

(deftest full-deck-test
  (is (= 52 (count (set damn-and-blast/full-deck))))

  (testing "each card is valid"
    (is (true? (every? #(spec/valid? ::damn-and-blast/card %) damn-and-blast/full-deck)))))
