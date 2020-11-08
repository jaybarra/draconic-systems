(ns ds.routes.graphql
  (:require
   [ring.util.response :as resp]))

(defn get-graphql
  [_req]
  (resp/status 204))

(defn post-graphql
  [_req]
  (resp/status 204))

(def routes
  [""
   {:swagger {:tags ["GraphQL"]}
    :get {:handler get-graphql}
    :post {:handler post-graphql}}])
