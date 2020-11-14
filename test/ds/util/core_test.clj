(ns ds.util.core-test
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]
   [clojure.test :refer :all]
   [ds.util.core :as util]
   [environ.core :refer [env]])
  (:import
   [java.io IOException]))

(defn file-writer-fixture
  [f]
  (try
    (io/delete-file "tmp_test_file.txt")
    (catch IOException _e)
    (finally (spit "tmp_test_file.txt" "mydata")))
  (f)
  (io/delete-file "tmp_test_file.txt"))

(use-fixtures :each file-writer-fixture)

(deftest file-reader-logic-test
  (is (= "mydata" (string/trim (slurp (io/file "tmp_test_file.txt"))))))

(deftest get-env-value-or-file
  (is (= nil
         (:test (util/get-env-value-or-file {} :nonsense-path-var :test))))

  (is (not= nil
            (:test (util/get-env-value-or-file {} :path :test))))

  (is (= (env :path)
         (get-in (util/get-env-value-or-file {} :path :nested :value) [:nested :value]))))
