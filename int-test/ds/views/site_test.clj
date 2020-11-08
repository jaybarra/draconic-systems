(ns ds.view.site-test
  (:require
   [clj-http.client :as client]
   [clojure.test :refer :all]))

(deftest home-page-renders
  (let [{:keys [status body]} (client/get "http://localhost:3000")]
    (is (= 200 status))
    (is (not= nil body))))

(deftest about-page-renders
  (let [{:keys [status body]} (client/get "http://localhost:3000/about")]
    (is (= 200 status))
    (is (not= nil body))))

(deftest not-found-page-returns-correctly
  (let [{:keys [status body]} (client/get
                                "http://localhost:3000/iamnotarealpage"
                                {:throw-exceptions false})]
    (is (= 404 status))
    (is (not= nil body))))
