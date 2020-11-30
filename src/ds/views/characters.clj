(ns ds.views.characters
  (:require
   [ds.views.components.page-source :as page-source]
   [ds.views.layout :refer [layout]]))

(defn characters-section
  [characters]
  [:nav.level
   (for [c characters]
     [:div.level-item.has-text-centered
      [:div
       [:p.heading (get c :title "Unknown")]
       [:p.title (get c :name "Unknown")]]])])

(defn characters-page
  [characters]
  (layout
   "Characters"
   [:div
    [:p "These values are pulled from the database"]]
   [:section.hero.is-primary
    [:div.hero-body
     [:div.container
      [:h1.title "Nanatsu no Taizai Characters"]]]]

   (characters-section characters)

   [:br]

   (page-source-dropdown-element "ds.db")
   (page-source-dropdown-element "ds.api.characters")
   (page-source-dropdown-element "ds.db.couchdb")

   [:br]

   (page-source/footer "ds.views.characters")))
