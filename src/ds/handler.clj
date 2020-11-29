;;; handler.clj --- web request handlers
;;; Commentary: 
;;; Code:

(ns ds.handler
  (:require
   ;; Site Routes
   [ds.routes.about :as about]
   [ds.routes.characters :as characters]
   [ds.routes.error :as error]
   [ds.routes.home :as home]

   ;; API Routes
   [ds.routes.graphql :as graphql]
   [ds.routes.locker :as locker]

   [ds.middleware :as mw]
   [muuntaja.core :as m]
   [reitit.coercion.spec]
   [reitit.dev.pretty :as pretty]
   [reitit.ring.coercion :as coercion]
   [reitit.ring.middleware.exception :as exception]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring :as ring]
   [reitit.swagger :as swagger]
   [reitit.swagger-ui :as swagger-ui]))

(def routes
  [[["/api/lockers" locker/routes]
    ["/api/graphql" graphql/routes]]

   ["" {:no-doc true}
    ["/" home/routes]
    ["/about" about/routes]
    ["/characters" characters/routes]

    ["/swagger.json"
     {:get {:handler (swagger/create-swagger-handler)
            :swagger {:info {:title "Draconic Systems API"
                             :description "APIs reference for <a href=\"https://draconicsystems.com\">Draconic Systems</a>"}}}}]
    ["/api-docs/*" {:get (swagger-ui/create-swagger-ui-handler
                           {:config
                            {:operationsSorter "alpha"}})}]]])

(defn create-app [db]
  (ring/ring-handler
   (ring/router
    routes
    {:exception pretty/exception
     :data {:coercion reitit.coercion.spec/coercion
            :db db
            ;; :cache cache
            ;; middleware order matters
            :middleware [swagger/swagger-feature
                         muuntaja/format-negotiate-middleware
                         muuntaja/format-response-middleware
                         exception/exception-middleware
                         muuntaja/format-request-middleware
                         coercion/coerce-request-middleware
                         coercion/coerce-response-middleware
                         ;; custom middleware
                         mw/cors
                         mw/db]
            :muuntaja m/instance}})

   (ring/routes
    (ring/create-resource-handler {:path "/"})

    (ring/redirect-trailing-slash-handler)

    (ring/create-default-handler
     {:not-found error/not-found-handler}))))

;;; handler.clj ends here
