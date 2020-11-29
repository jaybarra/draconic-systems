(ns ds.ai.neural-network
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.math.numeric-tower :as math]
   [ds.util.math :as dsm]))

(def fast-mult (memoize *))

(defn process-neuron
  "Processes neuron."
  [inputs weights bias act-fn]
  {:pre [(= (count inputs) (count weights))]}
  (let [raw-output
        (act-fn (* bias
                   (reduce + (map * inputs weights))))
        error (- (dsm/sigmoid raw-output))]
    raw-output))




(comment
  ;; UCI Dataset Repository
  ;; https://archive.ics.uci.edu/ml/datasets

  ;; iris data from UCI
  #_(slurp "https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data")

  (def inputs (repeatedly 3 rand))
  (def weights (repeatedly 3 #(- 1.0 (rand 2.0))))
  (def bias 1.0)

  (def act-fn (fn [x]
                (if (> (dsm/fast-sigmoid x) 0.5) 1 0)))

  (process-neuron inputs weights bias act-fn))
