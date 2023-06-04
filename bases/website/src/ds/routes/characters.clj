(ns ds.routes.characters
  (:require
   [ds.api.characters :as c]
   [ds.views.characters :as cv]
   [ring.util.response :as resp]))

(defn get-characters
  [{:keys [db] :as req}]
  (let [characters (c/get-characters db)]
    (resp/response (cv/characters-page characters))))

(def routes
  [""
   {:get {:handler get-characters}}])
