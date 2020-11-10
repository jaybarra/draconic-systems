;;; system.clj --- system configuration
;;; Commentary: configure the system using integrant
;;; Code:

(ns ds.system
  (:require
   [clojure.java.io :as io]
   [ds.handler :as handler]
   [integrant.core :as ig]
   [ring.adapter.jetty :as jetty]
   [taoensso.timbre :as log])
  (:gen-class))

(def system-config (-> "config.edn" io/resource slurp ig/read-string))

(defmethod ig/init-key :ds/server
  [_ {:keys [handler] :as opts}]
  (log/info "Server ready on port" (:port opts))
  (jetty/run-jetty handler (-> opts
                               (dissoc :handler)
                               (assoc :join? false))))

(defmethod ig/init-key :ds/app
  [_ {:keys [db]}]
  (log/info "Initializing Main Component:" "success")
  (handler/create-app db))

(defmethod ig/init-key :ds/db
  [_ {:keys [database-url]}]
  (log/info "Initializing DB Component:" "success")
  {:url database-url})

(defmethod ig/init-key :ds/cache
  [_ _opts]
  (log/info "Initializing Cache Component:" "success")
  {:cache nil})

(defmethod ig/halt-key! :ds/server
  [_ server]
  (.stop server))

(defn -main
  [& _args]
  (ig/init system-config))

(comment
  ;; start
  (def system (ig/init system-config))

  ;; stop
  (ig/halt! system))

;;; system.clj ends here
