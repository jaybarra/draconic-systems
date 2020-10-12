(ns ds.locker
  (:require
   [ds.middleware :as mw]
   [muuntaja.core :as m]
   [reitit.coercion.spec]
   [reitit.core :as r]
   [reitit.ring :as ring]))

(defn ok
  [_req]
  {:status 200 :body "ok"})


(def routes
  ["/lockers"
   {:swagger {:tags ["lockers"]}}

   [""
    {:post {:summary "Create a new locker"
            :parameters {:body {:duration int?}}
            :handler ok}}]

   ["/:id"
    {:get {:summary "Get a locker by id"
           :parameters {:path {:id string?}}
           :handler ok}

     :put {:summary "Update a locker"
           :parameters {:path {:id string?}
                        :body {:duration int?}}
           :handler ok}

     :delete {:summary "Delete a locker"
              :parameters {:path {:id string?}}
              :handler ok}}

    ["/add/:amount"
     {:get {:summary "Add or remove time"
            :handler ok
            :parameters {:path {:amount int?}}}}]]])
