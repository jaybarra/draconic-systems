(ns ds.views.error
  (:require
   [ds.views.layout :refer [layout]]))

(defn not-found
  [request]
  (let [{:keys [scheme server-name server-port uri]} request]
    (layout
      "Not Found"
      [:div
       [:p "The page you are looking for isn't here but might be in the future."]
       [:p "You must abandon your quest for now"]

       [:section
        [:pre scheme "://" server-name (when-not (= 80 server-port) (str ":" server-port)) uri]]])))
