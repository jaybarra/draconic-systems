(ns ds.middleware)

(def db
  "Inject :db object into requests."
  {:name ::db
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
  "Inject requests with CORS headers"
  {:name ::cors
   :compile (fn [{:keys [cors]} _]
              (fn [handler]
                (fn [req]
                  (clojure.pprint/pprint cors)
                  (handler
                   (update-in req [:headers] cors)))))})
