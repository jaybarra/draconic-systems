(ns ds.views.components.header)

(def DS_ROOT "https://draconicsystems.com")

(defn navbar-element
  "Returns the navbar element"
  []
  [:nav.has-shadow.is-spaced
   [:div.container
    [:div.navbar-brand
     [:a.navbar-item {:href DS_ROOT}
      [:span.icon
       [:i.fas.fa-home.fa-2x]]]

     [:a.navbar-item {:href DS_ROOT} "Home"]

     [:a.navbar-item {:href (format "%s/about" DS_ROOT)} "About"]

     [:a.navbar-item {:href (format "%s/api-docs" DS_ROOT)} "API Docs"]

     [:div.navbar-end
      [:div.navbar-item
       [:a.button.is-secondary
        {:rel "noopener noreferrer nofollow"
         :target "_blank"
         :href "https://github.com/jaybarra"}
        [:i.fab.fa-github.fa-2x {:aria-hidden "true"}]]]

      [:div.navbar-item
       [:a.button.is-secondary
        {:rel "noopener noreferrer nofollow"
         :target "_blank"
         :href "https://clojure.org/"}
        [:img {:src "/img/clojure-logo-120.png"
               :alt "clojure-logo"
               :height "60px"
               :aria-hidden "true"}]]]]]]])
