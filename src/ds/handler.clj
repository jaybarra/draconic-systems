;;; handler.clj --- web request handlers
;;; Commentary: 
;;; Code:

(ns ds.handler
  (:require
   [ds.locker :as locker]
   [ds.middleware :as mw]
   [muuntaja.core :as m]
   [reitit.coercion.spec]
   [reitit.ring.coercion :as coercion]
   [reitit.ring.middleware.exception :as exception]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring :as ring]
   [reitit.swagger :as swagger]
   [reitit.swagger-ui :as swagger-ui]))

(defn ok
  [_req]
  {:status 200 :body "ok"})

(def not-found (constantly {:status 404 :body "Not Found"}))

(defn handle-ping
  [_req]
  {:status 200
   :body "ok"})

(def comment-routes
  ["/comments"
   {:swagger {:tags ["comments"]}}

   [""
    {:get {:summary "Get all comments"
           :handler ok}

     :post {:summary "Create a new comment"
            :parameters {:body {:name string?
                                :slug string?
                                :text string?
                                :parent-comment-id int?}}
            :responses {200 {:body string?}}
            :handler ok}}]

   ["/:slug"
    {:get {:summary "Get comments by slug"
           :parameters {:path {:slug string?}}
           :handler ok}}]

   ["/id/:id"
    {:put {:summary "Update a comment by the moderator"
           :parameters {:path {:id int?}}
           :handler ok}

     :delete {:summary "Delete a comment by the moderator"
              :parameters {:path {:id int?}}
              :handler ok}}]])

(def routes
  [["/swagger.json"
    {:get {:handler (swagger/create-swagger-handler)
           :no-doc true
           :swagger {:info {:title "Draconic Systems API"
                            :description "API for Draconic Systems"}}}}]
   comment-routes
   locker/routes])

(defn create-app [db]
  (ring/ring-handler
    (ring/router
      routes
      {:data {:coercion reitit.coercion.spec/coercion
              :db db
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
        {:path "/"}))))

;;; handler.clj ends here
