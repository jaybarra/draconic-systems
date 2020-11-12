(ns ds.views.characters
  (:require
   [ds.views.layout :refer [layout]]))

(defn characters-page
  [characters]
  (layout
    "Characters"
    [:section.container
     (for [c characters] [:div (get c :name "unknown")])]))
