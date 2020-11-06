(ns ds.views.about
  (:require
   [ds.views.layout :refer [layout]]))

(defn about
  []
  (layout
    "About"
    [:div "I make good software."]))
