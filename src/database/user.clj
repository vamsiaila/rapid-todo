(ns database.user
  (:require [database.db :as db]
            [monger.collection :refer [find-one-as-map insert-and-return]]
            [shared.response :as exception]
  )
  (:import org.bson.types.ObjectId)
)

(def users-coll "users")

(defn add-user-to-db
  [{name :name email :email password :password}]
  (try
    (if (not= (find-one-as-map (db/get-connection) users-coll {:email email}) nil)
    {:exception exception/exception-validation :message "Account already exist with this email."}
    {:user-id (->>
      (insert-and-return (db/get-connection) users-coll {:name name :email email :password password})
      (:_id)
      (str)
      ) :name name})
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e) })
    )
  )

(defn match-user-credentials-with-db
  [{email :email password :password}]
  (try
    (let [{user-id :_id name :name} (find-one-as-map (db/get-connection) users-coll {:email email :password password})]
      (if (= user-id nil)
        {:exception exception/exception-validation :message "Invalid email or password."}
        {:user-id (str user-id) :name name})
      )
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e) })
    )
  )

(defn find-user-with-id-in-db
  [user-id]
  (try
    (let [{name :name} (find-one-as-map (db/get-connection) users-coll {:_id (ObjectId. (str user-id))})]
      (if (= name nil)
        {:exception exception/exception-unauthorized :message "User not found."}
        {:name name})
      )
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e) })
    )
  )
