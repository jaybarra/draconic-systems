(ns ds.util.math
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.math.numeric-tower :as mt]))

(defn sigmoid
  "Sigmoid logistic curve."
  [x]
  (/ 1 (inc (mt/expt Math/E (- x)))))

(def fast-sigmoid "[[sigmoid]] with memoized values." (memoize sigmoid))

(defn fuzzy=
  [tolerance a b]
  (let [diff (Math/abs (- a b))]
    (< diff tolerance)))
