(ns ds.routes.error
  (:require
   [ds.views.error :as ev]
   [ring.util.response :as resp]))

(defn not-found-handler
  [request]
  (resp/not-found (ev/not-found request)))
