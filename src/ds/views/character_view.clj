(ns ds.views.character-view
  (:require
   [ds.views.layout :refer [page-layout]]))


(defn character-avatar
  []
  [:div {:class "box"}
   [:img {:alt "user-avatar"
          :src "https://via.placeholder.com/100"}]])

(defn character-overview-page
  []
  (page-layout "Characters" [:div (character-avatar)]))
