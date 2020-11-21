(ns ds.db.couchdb-test
  (:require
   [clojure.test :refer :all]
   [ds.db.couchdb :as couchdb])
  (:import
   [clojure.lang ExceptionInfo]))

(deftest exec-query-test
  (testing "query validation"
    (let [db (couchdb/create-db {:url "http://localhost:5984" :auth {:user "foo" :password "bar"}})]
      (is (thrown-with-msg? ExceptionInfo #"Invalid query"
                            (.exec-query db {}))))))
