(ns ds.api.characters
  (:require
   [clojure.spec.alpha :as spec]))

;; Specs =======================================================================
(spec/def ::id string?)
(spec/def ::first-name string?)
(spec/def ::last-name string?)
(spec/def ::powers (spec/coll-of string?))
(spec/def ::character (spec/keys :req [::id
                                       ::first-name
                                       ::last-name
                                       ::powers]))

;; Functions  ==================================================================
(defn fetch-by-id
  "Search db for character with the given id."
  [_db _id]
  nil)

(defn persist-to-db!
  "Save a character to the database."
  [_db _character]
  nil)

(defn update-in-db!
  "Update a character."
  [_db _character]
  nil)

(defn delete-by-id!
  "Remove a character."
  [_db _id]
  nil)
