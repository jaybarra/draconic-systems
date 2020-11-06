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

    [:section
     [:nav {:class "level"}
      [:div {:class "level-item box has-background-link-light"}
       [:article {:class "media-content"}
        [:div {:class "content"} "Nginx"]]]

      [:div {:class "level-item box has-background-primary-light"} "Docker"
       [:div {:class "box has-background-success-light"}
        "Clojure"
        [:div {:class "content"} "ring"]
        [:div {:class "content"} "reitit"]]]]]))
