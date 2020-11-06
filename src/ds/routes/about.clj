(ns ds.routes.about
  (:require
   [ds.views.about :as view]
   [ring.util.response :as resp]))

(defn about-handler
  [_req]
  (resp/response (view/about)))

(def routes
  [""
   {:swagger {:no-doc true}
    :get {:handler about-handler}}])
