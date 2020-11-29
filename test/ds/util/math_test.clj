(ns ds.util.math-test
  (:require
   [clojure.test :refer :all]
   [ds.util.math :as math]))

(deftest sigmoid-test
  (are [x y] (= y (math/sigmoid x))
    0.0 0.5

    -0.5 0.3775406687981454
    0.5 0.6224593312018546


    -1.0 0.2689414213699951
    1.0 0.7310585786300049

    -10.0 4.539786870243442E-5
    10.0 0.9999546021312976))

(deftest fast-sigmoid-test
  (are [x y] (= y (math/fast-sigmoid x))
    0.0 0.5

    -0.5 0.3775406687981454
    0.5 0.6224593312018546


    -1.0 0.2689414213699951
    1.0 0.7310585786300049

    -10.0 4.539786870243442E-5
    10.0 0.9999546021312976))

(deftest fuzzy=-test
  (are [t x y e] (= e (math/fuzzy= t x y))
    0.1 10.1 10.11 true
    0.1 10.1 10.09 true
    0.1 10.1 10.2 true
    0.1 10.1 10.21 false))
