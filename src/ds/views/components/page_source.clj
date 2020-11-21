(ns ds.views.components.page-source
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(defn raw-source
  "Return a name spaces source as a string."
  [page-ns]
  (when-let [page-file
             (-> page-ns
                 (string/replace #"\." "/")
                 (string/replace #"-" "_")
                 (str ".clj")
                 io/resource)]
    (with-open [rdr (io/reader page-file)]
      (slurp rdr))))

(defn pre
  "Return the namespce code as a pre element."
  [page-ns]
  [:pre (raw-source page-ns)])

(defn footer
  "Return the namespce code as a footer element."
  [page-ns]
  [:footer#page-meta.footer
   [:div.content
    (pre page-ns)]])
