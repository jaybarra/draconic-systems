(ns ds.db
  (:require
   [clojure.spec.alpha :as spec]))

;; Specs =============================================================
(spec/def ::partition string?)

(spec/def ::database any?)

(spec/def ::query any?)

(spec/def ::db-query (spec/keys :req [::query ::table]
                                :opt [::partition]))

;; Protocol ==========================================================
(defprotocol DocumentStore
  "Document store protocol"
  (healthy? [this] "Perform a healthcheck.")

  (exec-query [this db-query] "Run a query.")

  (list-databases [this])

  (create-document! [this document-data] "Create a new document.")

  (read-document [this document-data] "Read a document.")

  (update-document! [this document-data] "Update a document.")

  (delete-document! [this document-id] "Delete a document."))
