(ns ds.api.scores
  (:require
   [clojure.spec.alpha :as spec]))

(spec/def ::note #{:a :a# :ab
                   :b     :bb
                   :c :c#
                   :d :d# :db
                   :e     :eb
                   :f :f#
                   :g :g# :gb})

(spec/def ::key #{:g :d :a :e :b :f# :c# :f :bb :eb :db :gb :cb})

(spec/def ::title string?)
(spec/def ::score any?)
(spec/def ::composer string?)

(def time-signature-regex #"\d+\/\d+")
(spec/def ::time-signature (spec/and
                             string?
                             #(re-matches time-signature-regex %)))

(spec/def ::song (spec/keys :req [::title
                                  ::score]
                            :opt [::key
                                  ::composer
                                  ::time-signature]))
