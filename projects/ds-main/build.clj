(ns build
  (:refer-clojure :exclude [test])
  (:require [clojure.tools.build.api :as build]))

(def app 'ds/ds-server)
(def main 'ds.system)
(def version (format "1.0.%s" (build/git-count-revs nil)))
(def class-dir "target/classes")

(defn clean
  [_]
  (println "Cleaning output directory")
  (build/delete {:path "target"}))


(defn- uber-opts
  [opts]
  (assoc opts
         :main main
         :basis (build/create-basis {})
         :class-dir class-dir
         :uber-file (format "target/%s-standalone.jar" (name app))
         :ns-compile [main]))

(defn uber
  [opts]
  (clean nil)
  (let [opts (uber-opts opts)]
    (println "Copying source")
    (build/copy-dir {:src-dirs ["src" "resources"] :target-dir class-dir})

    (println (str "Compiling " main))
    (build/compile-clj opts)

    (println (format "Building uberjar [%s:%s] as [%s]" app version (:uber-file opts)))
    (build/uber (uber-opts opts)))
  opts)
