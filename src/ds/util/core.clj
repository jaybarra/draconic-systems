(ns ds.util.core
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]
   [environ.core :refer [env]]))

(defn get-env-value-or-file
  "Reads an environment value or _file suffixed version."
  [m env-var & path]
  (let [env-file-name (keyword (str (name env-var) "_file"))
        is-file? (env-file-name env)
        val (or (env env-var)
                (when is-file?
                  (string/trim (slurp (io/file env-file-name)))))]
    (if val
      (assoc-in m path val)
      m)))
