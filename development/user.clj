(ns user
  (:require
   [clojure.java.io :as io]
   [ds.system :as system]
   [integrant.core :as ig]
   [integrant.repl :as ig-repl]))

(defn read-config
  [cfg-file]
  (-> cfg-file io/resource slurp ig/read-string))

(defn override-defaults
  [cfg alt-cfg]
  (loop [keylist (keys alt-cfg)
         new-cfg cfg]
    (if-not (seq keylist)
      new-cfg
      (recur (rest keylist)
             (let [k (first keylist)]
               (update-in new-cfg
                          [k]
                          #(merge % (k alt-cfg))))))))

(ig-repl/set-prep! (constantly (override-defaults
                                system/system-config
                                (read-config "config.dev.edn"))))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(comment
  (go)
  (halt)
  (reset)
  (reset-all))
