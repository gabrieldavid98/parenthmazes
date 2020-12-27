(ns parenthmazes.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def weapon-damage 
  {:fists 10.0, :staff 35.0, :sword 100.0, :cast-iron-saucepan 150.0})

(def weapon-fn-map
  {:fists (fn [health] (if (< health 100) (- health 10) health))
   :staff (partial + 35)
   :sword #(- % 100)
   :cast-iron-saucepan #(- % 100 (rand-int 50))
   :sweet-potato identity})

(defn strike
  "With one argument, strike a target with a default :fists `weapon`. With two
   argument, strike a target with `weapon` and return the target entity"
  
  ([target] (strike target :fists))
  
  ([target weapon]
   (let [weapon-fn (weapon weapon-fn-map)]
     (update target :health weapon-fn))))

(def enemy 
  {:name "Zulkaz", :health 250, :armor 0.8, :camp :trolls})

(def ally {:name "Carla", :health 80, :camp :gnomes})

(defn mighty-strike
  "Strike a `target` with all weapons!"
  [target]
  (let [weapon-fn (apply comp (vals weapon-fn-map))]
    (update target :health weapon-fn)))

(def player 
  {:name "Lea" 
   :health 200 
   :position {:x 10 :y 10 :facing :north}})

(defmulti move (comp :facing :position))

(defmethod move :north 
  [entity]
  (update-in entity [:position :y] inc))

(defmethod move :south 
  [entity]
  (update-in entity [:position :y] dec))

(defmethod move :west 
  [entity]
  (update-in entity [:position :x] inc))

(defmethod move :east 
  [entity]
  (update-in entity [:position :x] dec))

(defmethod move :default [entity] entity)

