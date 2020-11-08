(ns ds.views.character
  (:require
   [ds.views.layout :refer [layout]]))

(defn character-overview-page
  [_character-spec]
  (layout
    "Characters"
    [:div "picture to go here"]))
