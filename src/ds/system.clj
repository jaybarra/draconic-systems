;;; system.clj --- system configuration
;;; Commentary: configure the system using integrant
;;; Code:

(ns ds.system
  (:require
   [ds.handler :as handler]
   [environ.core :refer [env]]
   [integrant.core :as ig]
   [ring.adapter.jetty :as jetty]
   [taoensso.timbre :as log])
  (:gen-class))


(def system-config
  {:ds/db nil
   :ds/app {:db (ig/ref :ds/db)}
   :ds/server {:handler (ig/ref :ds/app)
               :opts {:port (env :port 8080)
                      :join? false}}})

(defmethod ig/init-key :ds/server
  [_ {:keys [handler opts]}]
  (log/info "Server ready on port" (:port opts))
  (jetty/run-jetty handler opts))

(defmethod ig/init-key :ds/app
  [_ {:keys [db]}]
  (log/info "Initializing app")
  (handler/create-app db))

(defmethod ig/init-key :ds/db
  [_ _]
  (log/info "Initializing DB Connection")
  {:no-db true})

(defmethod ig/halt-key! :ds/server
  [_ jetty]
  (.stop jetty))

(defn -main
  [& _args]
  (ig/init system-config))

(comment
  ;; start
  (def system (ig/init system-config))
  ;; stop
  (ig/halt! system))

;;; system.clj ends here
