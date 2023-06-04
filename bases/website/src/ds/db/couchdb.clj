;;; couchdb.clj --- couchdb
;;; Commentary: functions for interatcting with couchDB
;;; Code:
(ns ds.db.couchdb
  (:require
   [clj-http.client :as client]
   [clojure.spec.alpha :as spec]
   [ds.db :as db]
   [muuntaja.core :as m]
   [taoensso.timbre :as log]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Muuntaja instance
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def m (m/create))

(defn query->json
  [query]
  (->> query
       ::db/query
       (m/encode m "application/json")
       slurp))

(defn db-spec->basic-auth
  [db-spec]
  (let [user (get-in db-spec [:auth :user])
        pass (get-in db-spec [:auth :password])]
    {:basic-auth [user pass]}))

(defrecord CouchDBStore [db-spec]
  db/DocumentStore

  (healthy? [this]
    (let [{:keys [url]} (:db-spec this)
          opts (db-spec->basic-auth (:db-spec this))]
      (try
        (-> url
            (client/get opts)
            :status
            (= 200))
        (catch Exception e
          (log/error "Error communicating with CouchDB" (.getMessage e))))))

  (list-databases [this]
    (let [{:keys [url]} (:db-spec this)
          opts (db-spec->basic-auth (:db-spec this))
          uri (str url "/_dbs")]
      (m/decode-response-body (client/get uri opts))))

  (exec-query [this {::db/keys [database] :as query}]
    (when-not (spec/valid? ::db/db-query query)
      (throw (ex-info (str "Invalid query syntax")
                      (spec/explain-data ::db/db-query query))))

    (let [{:keys [url]} (:db-spec this)
          uri (str url "/" database "/_find")
          request (merge {:body (query->json query)
                          :headers {"Content-Type" "application/json"
                                    "Accept" "application/json"}}
                         (db-spec->basic-auth (:db-spec this)))]
      (-> uri
          (client/post request)
          m/decode-response-body)))

  (create-document! [_ document]
    (log/info "creating document")
    nil)

  (read-document [_ document]
    (log/info "reading document")
    nil)

  (update-document! [_ document]
    (log/info "updating document")
    nil)

  (delete-document! [_ document]
    (log/info "deleting document")
    nil))

(defn create-db
  "Returns a configured CouchDB connection."
  [cfg]
  (->CouchDBStore cfg))
