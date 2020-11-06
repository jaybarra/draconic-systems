(ns ds.views.about
  (:require
   [ds.views.layout :refer [layout]]))

(defn about
  []
  (layout
    "About"
    [:section "I write software."]
    [:section "This site is written in Clojure."]
    [:section "It is deployed 100% hands-free."]

    [:nav {:class "panel"}
     [:p {:class "panel-heading"} "Here's roughly what is going on in here."]
     [:div {:class "panel-block"}
      [:nav {:class "level"}
       [:div {:class "level-item box has-background-link-light"}
        [:article {:class "media-content"}
         [:div {:class "content"}
          [:h2 {:class "title"} "Nginx"]]]]

       [:div {:class "level-item box has-background-primary-light"}
        [:h2 {:class "title"} "Docker Swarm"]
        [:div {:class "box has-background-success-light"}
         [:h2 {:class "title"} "Clojure"]
         [:div {:class "content"}
          [:p {:class "title is-3"} "ring"]
          [:p {:class "subtitle is-4"}"For for request handling."]]

         [:div {:class "content"}
          [:p {:class "title is-3"} "Reitit"]
          [:p {:class "subtitle is-4"} "For routing."]]]

        [:div {:class "box has-background-warn-light"}
         [:h2 {:class "title"} "CouchDB"]]]]]]))
