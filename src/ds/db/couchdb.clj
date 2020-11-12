(ns ds.db.couchdb
  (:require
   [clj-http.client :as client]
   [ds.db :as db]
   [muuntaja.core :as m]
   [taoensso.timbre :as log]))

;; Muuntaja instance
(def m (m/create))

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

  (exec-query [this db query]
    (let [{:keys [url]} (:db-spec this)
          uri (str url "/" db "/_find")
          request (merge {:body (slurp (m/encode m "application/json" query))
                          :headers {"Content-Type" "application/json"
                                    "Accept" "application/json"}}
                         (db-spec->basic-auth (:db-spec this)))]
      (-> uri
          (client/post request)
          m/decode-response-body)))

  (create-document! [_ db document]
    (log/info "creating document")
    nil)

  (read-document [_ db document]
    (log/info "reading document")
    nil)

  (update-document! [_ db document]
    (log/info "updating document")
    nil)

  (delete-document! [_ db document]
    (log/info "deleting document")
    nil))

(defn create-db
  "Returns a configured CouchDB connection."
  [cfg]
  (->CouchDBStore cfg))

(comment
  (.healthy? (create-db
               {:url "http://localhost:5984"
                :auth {:user "admin" :password "password"}})))
