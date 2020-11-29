(ns ds.ai.neural-network-test
  (:require
   [clojure.test :refer :all]
   [ds.ai.neural-network :as nn]
   [ds.util.math :refer [fuzzy=]]))

(deftest process-neuron-test
  (is (fuzzy= 0.0001
              0.32
              (nn/process-neuron [1 1 0]
                                 [0.3 0.1 0.1]
                                 0.8
                                 (fn [x] x)))))
