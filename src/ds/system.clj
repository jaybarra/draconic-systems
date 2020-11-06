;;; system.clj --- system configuration
;;; Commentary: configure the system using integrant
;;; Code:

(ns ds.system
  (:require
   ;; [clojure.tools.reader.edn :as edn]
   ;; [ds.cache :as cache]
   [ds.handler :as handler]
   [integrant.core :as ig]
   [ring.adapter.jetty :as jetty]
   [taoensso.timbre :as log])
  (:gen-class))

(def system-config
  {:ds/db {:database-url "jdbc:sqlite:db/dev.sqlite"}

   ;; :ds/cache {:pool {}
   ;;            :spec {:uri "redis://localhost:6379/"}}

   :ds/app {:db (ig/ref :ds/db)
            ;; :cache (ig/ref :ds/cache)
            }

   :ds/server {:handler (ig/ref :ds/app)
               :port 3000
               :join? false}})

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
