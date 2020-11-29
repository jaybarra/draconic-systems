(ns ds.web.core)

(defn dom-ready
  [handler
   (.addEventListener js/window "DOMContentLoaded" handler)])
