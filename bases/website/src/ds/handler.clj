(ns ds.handler
  (:require
   ;; Site Routes
   [ds.routes.about :as about]
   [ds.routes.error :as error]
   [ds.routes.home :as home]
   [ds.middleware :as mw]
   [muuntaja.core :as m]
   [reitit.dev.pretty :as pretty]
   [reitit.ring.middleware.exception :as exception]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring :as ring]
   [taoensso.timbre :as log]))

(def routes
  [["/" home/routes]
   ["/about" about/routes]])

(defn create-app [opts]
  (ring/ring-handler
   (ring/router
    routes
    {:exception pretty/exception
     :data {:middleware [muuntaja/format-negotiate-middleware
                         muuntaja/format-response-middleware
                         exception/exception-middleware
                         muuntaja/format-request-middleware

                         ;; custom middleware
                         mw/cors]
            :muuntaja m/instance}})
   (ring/routes
    (ring/create-resource-handler {:path "/"})
    (ring/redirect-trailing-slash-handler)
    (ring/create-default-handler
     {:not-found error/not-found-handler}))))
