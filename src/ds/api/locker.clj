(ns ds.api.locker
  (:require
   [clojure.pprint]
   ;;[ds.cache :as cache]
   ))

(defn get-by-id!
  "Returns a locker by id."
  [_context id]
  {:item id})

#_(defn get-by-id!
    "Returns a locker by id."
    [context id]
    (clojure.pprint/pprint context)
    (let [conn (:cache context)
          item (cache/fetch conn id)]
      (if item
        {:item id}
        (do
          (cache/save conn id id)
          {:unknown-id id}))))
