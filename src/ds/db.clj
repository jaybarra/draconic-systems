(ns ds.db)

(defprotocol DataStore
  (persist! [db item]))
