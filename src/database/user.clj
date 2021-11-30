(ns database.user
  (:require [database.db :as db]
            [monger.collection :refer [find-one-as-map insert-and-return]]
            [shared.response :as exception]))
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
      )})
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e) })
    )
  )
