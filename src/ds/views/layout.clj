(ns ds.views.layout
  (:require
   [ds.views.components.header :as header]
   [hiccup.page :refer [html5
                        include-css]]))

(def BULMA_CSS "https://cdn.jsdelivr.net/npm/bulma@0.9.0/css/bulma.min.css")

(def FONT_AWESOME_JS "https://use.fontawesome.com/releases/v5.14.0/js/all.js")

;; TODO read favicons from resources dir
(def apple-favicons ["57x57"
                     "60x60"
                     "72x72"
                     "76x76"
                     "114x114"
                     "120x120"
                     "144x144"
                     "152x152"
                     "180x180"])

(def favicons ["32x32"
               "96x96"
               "16x16"])

(defn- head
  "Generates HTML head block."
  ([]
   (head "Draconic Systems"))
  ([title]
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]

    ;; android favicons
    [:link {:href "/android-icon-192x192.png"
            :sizes "192x192"
            :type "image/png"
            :rel "icon"}]

    ;; apple favicons
    (for [f apple-favicons]
      [:link {:rel "apple-touch-icon"
              :sizes f
              :href (format "/apple-icon-%s.png" f)}])

    ;; standard favicons
    (for [f favicons]
      [:link {:rel "icon"
              :type "image/png"
              :sizes f
              :href (format "/favicon-%s.png" f)}])

    [:link {:rel "manifest"
            :href "/manifest.json"}]

    ;; CSS
    (include-css BULMA_CSS
                 "styles/site.css")

    ;; Scripts
    [:script {:src FONT_AWESOME_JS
              :defer true}]

    [:title title]]))

(defn- body
  "Generates HTML body block."
  [content]
  [:body
   (header/navbar-element)

   [:div#root.container content]])

(defn layout
  "Default page layout."
  [title & content]
  (html5 {:lang "en"}
         (head title)
         (body content)))
