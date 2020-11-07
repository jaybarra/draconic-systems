(ns ds.views.character
  (:require
   [ds.views.layout :refer [layout]]))

(defn character-avatar
  []
  [:div.box
   [:img {:alt "user-avatar"
          :src "https://via.placeholder.com/100"}]])

(defn character-overview-page
  []
  (layout "Characters" [:div (character-avatar)]))
