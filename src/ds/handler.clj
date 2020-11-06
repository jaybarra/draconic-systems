;;; handler.clj --- web request handlers
;;; Commentary: 
;;; Code:

(ns ds.handler
  (:require
   [ds.locker :as locker]
   [ds.views.home :as hv]
   [ds.views.character-view :as cv]
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

(defn ok
  [_req]
  {:status 200 :body "ok"})

(def routes
  [["/" {:get (fn [_req] {:body hv/home})}]
   ["/index.html" {:get (fn [_req] {:body hv/home})}]

   ["/characters" {:get (fn [_req] {:body (cv/character-overview-page)})}]
   ["/api"
    locker/routes]
   ["/swagger.json"
    {:get {:handler (swagger/create-swagger-handler)
           :no-doc true
           :swagger {:info {:title "Draconic Systems API"
                            :description "API for Draconic Systems"}}}}]])

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
                           mw/db]
              :muuntaja m/instance}})
    (ring/routes
      (swagger-ui/create-swagger-ui-handler
        {:path "/swagger"})
      (ring/create-resource-handler
        {:path "/"})
      (ring/create-default-handler))))

;;; handler.clj ends here
