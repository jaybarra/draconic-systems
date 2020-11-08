(ns ds.api.lockers_test
  (:require
   [clj-http.client :as client]
   [clojure.test :refer :all]))

(deftest get-lockers-by-api
  (let [{:keys [status headers body]} (client/get "http://localhost:3000/api/lockers/1")]
    (is (= 200 status))
    (is (= "application/json;charset=utf-8" (get headers "Content-Type")))
    (is (not= nil body))))
