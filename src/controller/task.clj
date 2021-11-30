(ns controller.task
  (:require [shared.response :as response-handler]
            [validation.task :as task-validator]
            [shared.response :as response-handler]
            [shared.auth :as auth]
            [database.task :as task-db]
            )
  )

(defn add-task-to-user-tasks
  [{body-params :body-params headers :headers}]
  ; step-1 validate incoming request using validation package
  (let [error (task-validator/validate-add-task-to-user-tasks body-params headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 validate token and get user-id
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; step-3 add task to database using database package
          (let [{exception :exception message :message data :data} (task-db/add-user-task-to-db body-params user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return the task with id in response
              (response-handler/success-response data)
              )
            )
          )
        )
      )
    )
  )

(defn get-user-tasks
  [{headers :headers}]
  ; step-1 validate incoming request using validation package
  (let [error (task-validator/validate-get-user-tasks headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 validate token and get user-id
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; step-3 retrieve all tasks of the user
          (let [{exception :exception message :message data :data} (task-db/get-user-tasks-from-db user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return the tasks in response
              (response-handler/success-response data)
              )
            )
          )
        )
      )
    )
  )

(defn update-user-task
  [{body-params :body-params headers :headers}]
  ; step-1 validate incoming request using validation package
  (let [error (task-validator/validate-update-user-task body-params headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 validate token and get user-id
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; step-3 update task in the database using database package
          (let [{exception :exception message :message data :data} (task-db/update-user-task-in-db body-params user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return updated task in response
              (response-handler/success-response data)
              )
            )
          )
        )
      )
    )
  )

(defn mark-user-task-as-complete
  [{body-params :body-params headers :headers}]
  ; step-1 validate incoming request using validation package
  (let [error (task-validator/validate-mark-user-task-as-complete body-params headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 validate token and get user-id
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; step-3 update task as complete in the database using database package
          (let [{exception :exception message :message data :data} (task-db/update-user-task-as-complete-in-db (:task-id body-params) user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return updated task in response
              (response-handler/success-response data)
              )
            )
          )
        )
      )
    )
  )

(defn delete-user-task
  [{headers :headers path-params :path-params}]
  ; step-1 validate incoming request using validation package
  (let [error (task-validator/validate-delete-user-task path-params headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 validate token and get user-id
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; step-3 delete task in the database using database package
          (let [{exception :exception message :message data :data} (task-db/delete-user-task-in-db (:task-id path-params) user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return delete status in response
              (response-handler/success-response data)
              )
            )
          )
        )
      )
    )
  )
