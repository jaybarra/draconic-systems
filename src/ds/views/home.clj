(ns ds.views.home
  (:require
   [ds.views.layout :refer [page-layout]]))

(def home
  (page-layout
    [:div {:class "container"
           :is-dark true
           :id "home-container"}
     [:h1 {:class "title"} "Draconic Systems"]

     [:div [:p "lets make magic happen"]]]))
