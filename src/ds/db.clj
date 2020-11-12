(ns ds.db)

(defprotocol DocumentStore
  "Document store protocol"
  (healthy? [this])

  (exec-query [this database query] "Run a query")

  (create-document! [this database document] "Create a new document")

  (read-document [this database document] "Read a document.")

  (update-document! [this database document] "Update a document.")

  (delete-document! [this database document] "Delete a document."))
