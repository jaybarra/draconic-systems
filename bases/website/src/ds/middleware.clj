(ns ds.middleware
  (:require
   [clojure.walk :as walk]
   [reitit.middleware :as middleware]
   [taoensso.timbre :as log]))

(def cors
  {:name ::cors
   :description "Inject responses with CORS headers"
   :compile (fn [{:keys [cors]} _]
              (fn [handler]
                (fn [req]
                  (let [response (handler req)
                        cors-headers (walk/stringify-keys cors)]
                    (log/debug "Injecting CORS headers to response" cors)
                    (update-in response [:headers] #(merge % cors-headers))))))})
