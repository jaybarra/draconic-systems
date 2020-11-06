(ns ds.routes.error
  (:require
   [ds.views.error :as ev]
   [ring.util.response :as resp]))

(defn not-found-handler
  [{:keys [scheme server-name uri] :as req}]
  (resp/not-found (ev/not-found (format "%s://%s%s"
                                        (name scheme)
                                        server-name
                                        uri))))
