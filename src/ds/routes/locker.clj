(ns ds.routes.locker
  (:require
   [ds.api.lockers :as lockers]
   [ring.util.response :as resp]))

(defn get-by-id
  [{:keys [db parameters]}]
  (let [id (-> parameters :path :id)]
    (if-let [locker (lockers/find-by-id db id)]
      (resp/response locker)
      (resp/not-found))))

(def routes
  [""
   {:swagger {:tags ["lockers"]}}
   ["/:id"
    {:get {:summary "Get a locker by id"
           :parameters {:path {:id string?}}
           :handler get-by-id}}]])
