(ns ds.views.components.page-source
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(defn page-source-pre
  [page-ns]
  (let [page-file
        (-> page-ns
            (string/replace #"\." "/")
            (string/replace #"-" "_")
            (str ".clj"))
        page-source (-> page-file
                        io/resource
                        slurp)]

    [:pre page-source ]))

(defn page-source-footer
  [page-ns]
  [:footer#page-meta.footer
   [:div.content
    (page-source-pre page-ns)]])
