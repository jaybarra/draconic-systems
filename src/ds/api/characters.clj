(ns ds.api.characters
  (:require
   [clojure.spec.alpha :as spec]
   [ds.db :as db-store]
   [taoensso.timbre :as log]))

;; Specs =============================================================
(spec/def ::id string?)
(spec/def ::first-name string?)
(spec/def ::last-name string?)
(spec/def ::powers (spec/coll-of string?))
(spec/def ::character (spec/keys :req [::id
                                       ::first-name
                                       ::last-name
                                       ::powers]))

;; Functions  ========================================================
(defn get-characters
  [db]
  (try
    (:docs (db-store/exec-query db "characters" {:selector
                                                 {:_id {"$gt" nil}}
                                                 :skip 0
                                                 :limit 10}))
    (catch Exception e
      (log/error "An error occurred communicating with the database."
                 (.getMessage e)))))
