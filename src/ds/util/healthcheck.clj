(ns ds.util.healthcheck
  (:require
   [clj-http.client :as client]
   [ds.system :as sys])
  (:import
   [java.lang System])
  (:gen-class))

(defn healthy?
  "Returns true if the server is up and responding to requests."
  []
  (let [port (-> sys/system-config :ds/server :port str)
        url (str "http://localhost:" port)]
    (try
      (when (client/get url) true)
      (catch Exception _e false))))

(defn -main
  [& _args]
  (if (healthy?)
    (System/exit 0)
    (System/exit -1)))
