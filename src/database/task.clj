(ns database.task
  (:require [database.db :as db]
            [monger.collection :refer [find-one-as-map insert-and-return find-maps update-by-id remove-by-id]]
            [shared.response :as exception]
            )
  (:import org.bson.types.ObjectId)
  )

(def tasks-coll "tasks")

(defn add-user-task-to-db
  [{task-name :task-name priority :priority} user-id]
  (try
    (if (not= (find-one-as-map (db/get-connection) tasks-coll {:user-id (ObjectId. (str user-id)) :task-name task-name}) nil)
      {:exception exception/exception-validation :message "A task with same name already exist."}
      {:data
       {:task-id (->>
                   (insert-and-return
                     (db/get-connection)
                     tasks-coll
                     {:user-id (ObjectId. (str user-id))
                      :task-name task-name
                      :priority priority
                      :completed false
                      }
                     )
                   (:_id)
                   (str)
                   )
        :task-name task-name
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

(defn get-user-tasks-from-db
  [user-id]
  (try
    (let [user-tasks (find-maps (db/get-connection) tasks-coll {:user-id (ObjectId. (str user-id))} {:user-id 0})]
      (if (= (count user-tasks) 0)
        {:exception exception/exception-not-found :message "No tasks available."}
        {:data user-tasks}
        )
      )
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e) }
      )
    )
  )

(defn update-user-task-in-db
  [{task-id :task-id task-name :task-name priority :priority} user-id]
  (try
    (let [task (find-one-as-map (db/get-connection) tasks-coll {:_id (ObjectId. (str task-id)) :user-id (ObjectId. (str user-id))})]
      (if (= task nil)
        {:exception exception/exception-not-found :message "Task not found."}
        (let [task-name-exist (find-one-as-map (db/get-connection) tasks-coll {:_id { "$ne" (ObjectId. (str task-id)) }
                                                                           :user-id (ObjectId. (str user-id))
                                                                           :task-name task-name})]
          (if (not= task-name-exist nil)
            {:exception exception/exception-validation :message "A task with same name already exist."}
            (do
              (update-by-id (db/get-connection) tasks-coll (ObjectId. (str task-id)) {"$set" {:task-name task-name :priority priority}})
              {:data {:task-id task-id :task-name task-name :priority priority :completed (:completed task)}}
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

(defn update-user-task-as-complete-in-db
  [task-id user-id]
  (try
    (let [{task-name :task-name priority :priority completed :completed} (find-one-as-map (db/get-connection) tasks-coll {:_id (ObjectId. (str task-id)) :user-id (ObjectId. (str user-id))})]
      (if (= task-name nil)
        {:exception exception/exception-not-found :message "Task not found."}
        (if (= completed true)
          {:exception exception/exception-validation :message "Task already marked as complete."}
          (do
            (update-by-id (db/get-connection) tasks-coll (ObjectId. (str task-id)) {"$set" {:completed true}})
            {:data {:task-id task-id :task-name task-name :priority priority :completed true}}
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

(defn delete-user-task-in-db
  [task-id user-id]
  (try
    (let [task (find-one-as-map (db/get-connection) tasks-coll {:_id (ObjectId. (str task-id)) :user-id (ObjectId. (str user-id))})]
      (if (= task nil)
        {:exception exception/exception-not-found :message "Task not found."}
        (do
          (remove-by-id (db/get-connection) tasks-coll (ObjectId. (str task-id)))
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

