(ns ds.routes.home
  (:require
   [ds.views.home :as hv]
   [ring.util.response :as resp]))

(defn home-handler
  [_req]
  (resp/response (hv/home)))

(def routes
  ["" {:get {:handler home-handler}}])
