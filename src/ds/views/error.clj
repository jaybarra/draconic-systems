(ns ds.views.error
  (:require
   [ds.views.layout :refer [layout]]))

(defn not-found
  [& messages]
  (layout
    "Not Found"
    [:div
     [:p "The page you are looking for isn't here but might be in the future."]
     [:p "You must abandon your quest for now"]

     [:div {:class "section"}
      (for [msg messages] [:p msg])]]))
