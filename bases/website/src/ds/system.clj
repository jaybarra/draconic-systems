(ns ds.system
  (:require
   [clojure.java.io :as io]
   [ds.handler :as handler]
   [ds.util.core :as util]
   [integrant.core :as ig]
   [ring.adapter.jetty :as jetty]
   [taoensso.timbre :as log])
  (:gen-class))

(def system-config (-> "config.edn" io/resource slurp ig/read-string))

(defonce system-atom (atom {}))

(defmethod ig/init-key :ds/server
  [_ {:keys [handler] :as opts}]
  (log/info "Server ready on port" (:port opts))
  (let [server-opts (-> opts
                        (dissoc :handler)
                        (assoc :join? (get opts :join? false)))]
    (jetty/run-jetty handler server-opts)))

(defmethod ig/init-key :ds/app
  [_ opts]
  (log/info "Initializing Main Component")
  (handler/create-app opts))

(defmethod ig/init-key :ds/cors
  [_ cors]
  cors)

(defmethod ig/halt-key! :ds/server
  [_ server]
  (.stop server))

(defn -main []
  (let [running-system (ig/init system-config)]
    (swap! system-atom assoc :system running-system)))

(comment
  (-main)
  (ig/halt! (:system @system-atom)))
