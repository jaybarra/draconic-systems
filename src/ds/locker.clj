(ns ds.locker
  (:require
   [ds.api.locker :as lockers]
   [taoensso.timbre :as log]
   [ring.util.response :as resp]))

(defn get-by-id
  [{:keys [parameters]}]
  (let [id (-> parameters :path :id)]
    (if id
      (resp/response {:locker-id id})
      (resp/not-found))))

(def routes
  ["/lockers"
   {:swagger {:tags ["lockers"]}}

   ["/:id"
    {:get {:summary "Get a locker by id"
           :parameters {:path {:id string?}}
           :handler get-by-id}}]])
