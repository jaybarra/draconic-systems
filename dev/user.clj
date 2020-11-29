(ns user
  (:require
   [clojure.java.io :as io]
   [ds.system :as system]
   [integrant.core :as ig]
   [integrant.repl :as ig-repl]))

(ig-repl/set-prep! (fn [] (update-in system/system-config
                                     [:ds/server]
                                     #(merge % (:ds/server (-> "config.dev.edn" io/resource slurp ig/read-string))))))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(comment
  (go)
  (halt)
  (reset)
  (reset-all))
