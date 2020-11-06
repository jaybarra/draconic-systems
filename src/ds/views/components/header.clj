(ns ds.views.components.header)

(def DS_ROOT "https://draconicsystems.com")

(defn navbar-element
  "Returns the navbar element"
  []
  [:nav {:class "has-shadow is-spaced"}
   [:div {:class "container"}
    [:div {:class "navbar-brand"}
     [:a {:class "navbar-item"
          :href DS_ROOT}
      [:span {:class "icon"}
       [:i {:class "fas fa-home fa-2x"}]]]

     [:a {:class "navbar-item" :href DS_ROOT} "Home"]

     [:a {:class "navbar-item" :href (format "%s/about" DS_ROOT)} "About"]]]])
