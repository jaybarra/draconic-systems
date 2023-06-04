(ns ds.views.blog
  (:require
   [ds.views.layout :refer [layout]]))

(defn blog
  [{:keys [content title] :as blog-entry}]
  (layout
    "Blog"
    [:section.section
     [:h1.title "The Blog"]
     [:h2.subtitle (or title "Untitled Blog")]
     [:article.media
      [:div.media-content
       [:div.content
        [:p content]]]]]))
