(ns database.list
  (:require [database.db :as db]
            [monger.collection :refer [find-one-as-map insert-and-return find-maps]]
            [shared.response :as exception]
            )
  (:import org.bson.types.ObjectId)
)

(def items-coll "items")

(defn add-item-to-db
  [{value :value priority :priority} user-id]
  (try
    (if (not= (find-one-as-map (db/get-connection) items-coll {:user-id (ObjectId. (str user-id)) :value value}) nil)
      {:exception exception/exception-validation :message "An item with same value already exist."}
      {:data
         {:item-id (->>
                    (insert-and-return
                      (db/get-connection)
                      items-coll
                      {:user-id (ObjectId. (str user-id))
                       :value value
                       :priority priority
                       :completed false
                      }
                    )
                    (:_id)
                    (str)
                  )
           :value value
           :priority priority
           :completed false
         }
     }
    )
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e) }
    )
  )
)

(defn get-items-from-db
  [user-id]
  (let [user-items (find-maps (db/get-connection) items-coll {:user-id (ObjectId. (str user-id))} {:user-id 0})]
    (if (= (count user-items) 0)
      {:exception exception/exception-not-found :message "No items available."}
      {:data user-items}
    )
  )
)

(defn update-item-in-db
  [])
