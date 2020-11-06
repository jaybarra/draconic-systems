(ns ds.views.home
  (:require
   [ds.views.layout :refer [layout]]))

(defn home
  []
  (layout
    "Draconic Systems"
    [:div {:class "container"
           :is-dark true
           :id "home-container"}
     [:h1 {:class "title"} "Draconic Systems"]

     [:div [:p "APIs and Software"]]]))
