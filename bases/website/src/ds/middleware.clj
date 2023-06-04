(ns ds.middleware
  (:require
   [clojure.walk :as walk]
   [reitit.middleware :as middleware]
   [taoensso.timbre :as log]))

(def db
  "Inject :db object into requests."
  {:name ::db
   :description "Add database to request object."
   :compile (fn [{:keys [db]} _]
              (fn [handler]
                (fn [req]
                  (handler (assoc req :db db)))))})

#_(def cache
    "Inject :cache object into requests."
    {:name ::cache
     :compile (fn [{:keys [cache]} _]
                (fn [handler]
                  (fn [req]
                    (handler (assoc req :cache  cache)))))})
(def cors
  {:name ::cors
   :description "Inject responses with CORS headers"
   :compile (fn [{:keys [cors]} _]
              (fn [handler]
                (fn [req]
                  (let [response (handler req)
                        cors-headers (walk/stringify-keys cors)]
                    (update-in response [:headers] #(merge % cors-headers))))))})
