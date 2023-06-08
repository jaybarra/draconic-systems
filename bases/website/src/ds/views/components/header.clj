(ns ds.views.components.header)

(defn navbar-element
  "Returns the navbar element"
  []
  [:nav.has-shadow.is-spaced
   [:div.container
    [:div.navbar-brand
     [:a.navbar-item {:href "/"}
      [:span
       [:i.fas.fa-home.fa-2x]]]

     [:a.navbar-item {:href "/"} "Home"]

     [:a.navbar-item {:href "/about"} "About"]

     [:div.navbar-end
      [:div.navbar-item
       [:a.button.is-secondary
        {:rel "noopener noreferrer nofollow"
         :target "_blank"
         :href "https://github.com/jaybarra/draconic-systems"}
        [:i.fab.fa-github.fa-2x {:aria-hidden "true"}]]]]]]])
