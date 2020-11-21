(ns ds.views.home
  (:require
   [ds.views.layout :refer [layout]]
   [ds.views.components.page-source :refer [page-source-footer]]))

(defn home
  []
  (layout
    "Draconic Systems"
    [:div#home-container.container
     [:h1.title "Draconic Systems"]
     [:p "APIs and Software"]
     [:section
      [:p "This is a hobby site. Expect changes and intereseting things."]]]

    (page-source-footer "ds.views.home")))
