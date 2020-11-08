(ns ds.api.lockers)

(defn find-by-id
  "Returns a locker by id."
  [_db-spec id]
  {:id id})
