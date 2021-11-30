(ns database.list
  (:require [database.db :as db]
            [monger.collection :refer [find-one-as-map insert-and-return find-maps update-by-id remove-by-id]]
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
  (try
    (let [user-items (find-maps (db/get-connection) items-coll {:user-id (ObjectId. (str user-id))} {:user-id 0})]
      (if (= (count user-items) 0)
        {:exception exception/exception-not-found :message "No items available."}
        {:data user-items}
      )
    )
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e) }
    )
  )
)

(defn update-item-in-db
  [{item-id :item-id value :value priority :priority} user-id]
  (try
    (let [item (find-one-as-map (db/get-connection) items-coll {:_id (ObjectId. (str item-id)) :user-id (ObjectId. (str user-id))})]
      (if (= item nil)
        {:exception exception/exception-not-found :message "Item not found."}
        (let [value-exist (find-one-as-map (db/get-connection) items-coll {:_id { "$ne" (ObjectId. (str item-id)) }
                                                                           :user-id (ObjectId. (str user-id))
                                                                           :value value})]
          (if (not= value-exist nil)
            {:exception exception/exception-validation :message "An item with same value already exist."}
            (do
              (update-by-id (db/get-connection) items-coll (ObjectId. (str item-id)) {"$set" {:value value :priority priority}})
              {:data {:item-id item-id :value value :priority priority :completed (:completed item)}}
            )
          )
        )
      )
    )
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e) }
    )
  )
)

(defn update-item-as-complete-in-db
  [item-id user-id]
  (try
    (let [{value :value priority :priority completed :completed} (find-one-as-map (db/get-connection) items-coll {:_id (ObjectId. (str item-id)) :user-id (ObjectId. (str user-id))})]
      (if (= value nil)
        {:exception exception/exception-not-found :message "Item not found."}
        (if (= completed true)
          {:exception exception/exception-validation :message "Item already marked as complete."}
          (do
            (update-by-id (db/get-connection) items-coll (ObjectId. (str item-id)) {"$set" {:completed true}})
            {:data {:item-id item-id :value value :priority priority :completed true}}
          )
        )
      )
    )
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e) }
    )
  )
)

(defn delete-item-in-db
  [item-id user-id]
  (try
    (let [{value :value priority :priority completed :completed} (find-one-as-map (db/get-connection) items-coll {:_id (ObjectId. (str item-id)) :user-id (ObjectId. (str user-id))})]
      (if (= value nil)
        {:exception exception/exception-not-found :message "Item not found."}
        (do
          (remove-by-id (db/get-connection) items-coll (ObjectId. (str item-id)))
          {:data {:deleted true}}
        )
      )
    )
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e) }
    )
  )
)
