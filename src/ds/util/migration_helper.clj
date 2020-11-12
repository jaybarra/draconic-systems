(ns ds.util.migration-helper
  (:require
   [clj-http.client :as client]
   [clojure.java.io :as io]
   [clojure.pprint]
   [clojure.string :as string]
   [clojure.tools.reader.edn :as edn]
   [ds.system :as sys]
   [taoensso.timbre :as log])
  (:import
   [java.lang System])
  (:gen-class))

(def migrations (->> "resources/db/couchdb"
                     io/file
                     file-seq
                     (filter #(.isFile %))))

(defn db-spec->basic-auth
  [{{:keys [user password]} :auth}]
  {:basic-auth [user password]})

(defn db-exists?
  [db db-name]
  (let [db-url (get-in db [:url])
        uri (str db-url "/" db-name)]
    (-> uri
        (client/get (merge (db-spec->basic-auth db)
                           {:throw-exceptions false}))
        :status
        (= 200))))

(defn run-command!
  [db command]
  (let [verb (:command command)
        opts (db-spec->basic-auth db)
        db-url (:url db)
        uri (str db-url "/" (:database command))
        action (condp = verb
                 :PUT (partial client/put)
                 :POST (partial client/post)
                 :DELETE (partial client/delete)
                 :GET (partial client/get))]
    (log/info "Running Migration" command)
    (action uri opts)))

(defn migrate-up!
  [db]
  (doseq [migration migrations]
    (let [command (:up (edn/read-string (slurp migration)))]
      (when-not (db-exists? db (:database command))
        (run-command! db command)))))

(defn migrate-down!
  [db]
  (doseq [migration (reverse migrations)]
    (let [command (:down (edn/read-string (slurp migration)))]
      (when (db-exists? db (:database command))
        (run-command! db command)))))

(defn -main
  [& args]
  (let [db (:ds/db sys/system-config)]
    (condp = (string/lower-case (string/trim (first args)))
      "up" (migrate-up! db)
      "down" (migrate-down! db)
      (System/exit -1))))

(comment
  (migrate-up! (:ds/db sys/system-config))

  (migrate-down! (:ds/db sys/system-config)))
