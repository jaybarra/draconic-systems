(ns ds.util.core
  (:require
   [camel-snake-kebab.core :as csk]
   [clojure.string :as string]
   [environ.core :refer [env]]))

(defn get-env-value-or-file
  "Reads an environment value or _file suffixed version."
  [m var & path]
  (let [env-var (csk/->kebab-case var)
        env-file (keyword (str (name env-var) "-file"))
        is-file? (not (nil? (env env-file)))
        val (or (env env-var)
                (when is-file?
                  (-> env-file slurp string/trim)))]
    (if val
      (assoc-in m path val)
      m)))

(comment
  (get-env-value-or-file {} :couchdb_password :auth :password))
