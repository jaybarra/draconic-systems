;;; system.clj --- system configuration
;;; Commentary: configure the system using integrant
;;; Code:

(ns ds.system
  (:require
   [clojure.java.io :as io]
   [ds.db.couchdb :as db]
   [ds.handler :as handler]
   [environ.core :refer [env]]
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

(defn add-env-or-default
  [m env-var & path]
  (let [val (or (env env-var)
                (env (-> env-var
                         name
                         (str "_FILE")
                         keyword
                         io/file
                         slurp)))]
    (if val
      (assoc-in m path val)
      m)))

(defmethod ig/init-key :ds/db
  [_ cfg]
  (let [opts (-> cfg
                 (add-env-or-default :COUCHDB_USER :auth :user)
                 (add-env-or-default :COUCHDB_PASSWORD :auth :pasword))
        db-store (db/create-db opts)]
    (if (.healthy? db-store)
      (log/info "Initializing DB Component:" "success")
      (log/error "Initializing DB Component:" "error"))
    db-store))

(defmethod ig/halt-key! :ds/db
  [_ _db-store]
  nil)

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
