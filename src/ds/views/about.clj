(ns ds.views.about
  (:require
   [ds.views.layout :refer [layout]]
   [ds.views.components.page-source :refer [page-source-footer]]))

(defn deploy-structure-element
  []
  [:nav.panel.mt-3
   [:p.panel-heading "Here's roughly what is going on in here."]
   [:div.panel-block
    [:nav.level
     [:div.level-item.box.has-background-link-light
      [:article.media-content
       [:div.content {:class "content"}
        [:h2.title "Nginx"]]]]

     [:div.level-item.box.has-background-primary-light
      [:h2.title "Docker Swarm"]
      [:div.box.has-background-success-light.m-3
       [:h2.title "Clojure"]
       [:div.content
        [:p.title.is-3 "ring"]
        [:p.subtitle.is-4 "For for request handling."]]

       [:div.content
        [:p.title.is-3 "Reitit"]
        [:p.subtitle.is-4 "For routing."]]]

      [:div.box.has-background-danger-light.m-3
       [:h2.title "CouchDB"]]]]]])

(defn about
  []
  (layout
    "About"
    [:section "I write software."]
    [:section "This site is written in Clojure."]
    [:section "A CI/CD Pipeline handles deployment automatically after changes have been checked in."]

    (deploy-structure-element)

    (page-source-footer "ds.views.about")))
