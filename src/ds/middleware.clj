(ns ds.middleware)

(def db
  "Inject :db object into requests."
  {:name ::db
   :compile (fn [{:keys [db]} _]
              (fn [handler]
                (fn [req]
                  (handler (assoc req :db db)))))})
