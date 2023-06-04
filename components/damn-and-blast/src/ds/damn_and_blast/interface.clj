(ns ds.damn-and-blast.interface
  (:require
   [clojure.java.io :as io]
   [clojure.set :refer [difference]]
   [clojure.spec.alpha :as spec]
   [clojure.string :as string]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Specs
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def suits #{:spade :heart :club :diamond})

(spec/def ::suit suits)

(spec/def ::rank (spec/and int?
                           #(> % 1)
                           #(< % 15)))

(spec/def ::card (spec/keys :req [::rank ::suit]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def full-deck
  (for [suit suits
        rank (range 2 15)]
    {::rank rank
     ::suit suit}))

(defn card-rank->title
  [rank]
  (case rank
    14 "ace"
    13 "king"
    12 "queen"
    11 "jack"
    ;; else
    (str rank)))

(defn card-name
  [{rank :rank suit :suit}]
  (str (card-rank->title rank)  " of " (name suit) "s"))

(defn deal-player-hand
  [{:keys [deck] :as state} player num-cards]
  (let [hand (take num-cards deck)
        new-deck (difference (set deck) (set hand))]
    (-> state
        (assoc :deck new-deck)
        (assoc-in [:players player :hand] hand))))

(defn pick-trump
  [{:keys [deck] :as state}]
  (let [trump (take 1 deck)
        new-deck (difference (set deck) #{trump})]
    (-> state
        (assoc :deck new-deck)
        (assoc :trump trump))))

(defn play
  [state]
  (-> state
      (deal-player-hand :player1 3)
      (deal-player-hand :player2 3)
      pick-trump))

(defn print-card!
  [{rank :rank suit :suit}]
  (let [s (name suit)
        r (card-rank->title rank)]
    (printf (string/join
             "%n"
             [" ---------------"
              "| %-5s %7s |"
              "|               |"
              "|               |"
              "|               |"
              "|               |"
              "|               |"
              "|               |"
              "| %-7s %5s |"
              " ---------------\n"])
            r s s r)))

(comment
  (def game-state {:deck (shuffle full-deck)})
  (print-card! (first (take 1 (shuffle full-deck))))

  (doseq [c full-deck] (print-card! c))

  (play game-state)

  (deal-player-hand game-state :player1 3))
