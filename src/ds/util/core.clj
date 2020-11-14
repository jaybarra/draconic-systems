(ns ds.util.core
  (:require
   [camel-snake-kebab.core :as csk]
   [clojure.java.io :as io]
   [clojure.string :as string]
   [environ.core :refer [env]]))

(defn get-env-value-or-file
  "Reads an environment value or _file suffixed version."
  [m var & path]
  (let [env-var (csk/->kebab-case var)
        env-file-name (keyword (str (name env-var) "-file"))
        is-file? (env-file-name env)
        val (or (env env-var)
                (when is-file?
                  (string/trim (slurp (io/file env-file-name)))))]
    (if val
      (assoc-in m path val)
      m)))

(comment
  (get-env-value-or-file {} :couchdb_password :password))
