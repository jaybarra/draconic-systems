(ns ds.views.components.page-source
  (:require
   [clojure.java.io :as io]
   [clojure.string :as string]))

(defn raw-source
  "Return a namespace source as a string."
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
  "Return the namespace code as a `<pre>` element."
  [page-ns]
  [:pre (raw-source page-ns)])

(defn footer
  "Return the namespace code as a footer element."
  [page-ns]
  [:footer#page-meta.footer
   [:div.content
    (pre page-ns)]])

(defn page-source-dropdown-element
  [page-ns]
  (let [page-ns-id (str (string/replace page-ns #"\." "-") "-dropdown")
        dd-ele (keyword (str "div#" page-ns-id ".dropdown"))
        dd-ele-content (keyword (str "div#" page-ns-id "-content.dropdown-menu"))]

    [dd-ele
     [:div.dropdown-trigger
      [:button.button {:aria-haspopup "true"
                       :aria-controls (str page-ns-id "-content")}
       [:span page-ns]
       [:span.icon.is-small
        [:i.fas.fa-angle-down {:aria-hidden "true"}]]]]

     [dd-ele-content {:role "menu"}
      [:div.dropdown-content
       (pre page-ns)]]]))
