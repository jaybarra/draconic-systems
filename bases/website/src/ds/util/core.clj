(ns ds.util.core
  (:require
   [camel-snake-kebab.core :as csk]
   [clojure.string :as str]
   [environ.core :refer [env]]))

(defn get-env-value-or-file
  "Reads an environment value or _file suffixed version."
  [m var & path]
  (let [env-var (csk/->kebab-case var)
        env-file (env (keyword (str (name env-var) "-file")))
        val (or (env env-var)
                (when env-file
                  (-> env-file slurp str/trim)))]
    (if val
      (assoc-in m path val)
      m)))

(comment
  (get-env-value-or-file {} :couchdb_password :auth :password))
