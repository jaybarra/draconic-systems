(ns ds.api.lockers)

(defn find-by-id
  "Returns a locker by id."
  [_db id]
  {:id id})
