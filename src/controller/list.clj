(ns controller.list
  (:require [shared.response :as response-handler]
            [validation.list :as list-validator]
            [shared.response :as response-handler]
            [shared.auth :as auth]
            [database.list :as list-db]
  )
)

(defn add-item-to-list
  [{body-params :body-params headers :headers}]
  ; step-1 validate incoming request using validation package
  (let [error (list-validator/validate-add-item-to-list body-params headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 validate token and get user-id
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; step-3 add item to database using database package
          (let [{exception :exception message :message data :data} (list-db/add-item-to-db body-params user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return the item with id in response
              (response-handler/success-response data)
            )
          )
        )
      )
    )
  )
)

(defn get-list-items
  [{headers :headers}]
  ; step-1 validate incoming request using validation package
  (let [error (list-validator/validate-get-list-items headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 validate token and get user-id
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; step-3 retrieve all items of the user
          (let [{exception :exception message :message data :data} (list-db/get-items-from-db user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return the items in response
              (response-handler/success-response data)
            )
          )
        )
      )
    )
  )
)

(defn update-item-in-list
  [{body-params :body-params headers :headers}]
  ; step-1 validate incoming request using validation package
  (let [error (list-validator/validate-update-item-in-list body-params headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 validate token and get user-id
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; TODO: update item
          ()
        )
      )
    )
  )
)

(defn mark-item-in-list-as-complete
  [{body-params :body-params headers :headers}]
  ; step-1 validate incoming request using validation package
  (let [error (list-validator/validate-mark-item-in-list-as-complete body-params headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 validate token and get user-id
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; TODO: mark item as complete
          ()
        )
      )
    )
  )
)

(defn delete-item-in-list
  [{headers :headers path-params :path-params}]
  (println path-params)
  ; step-1 validate incoming request using validation package
  (let [error (list-validator/validate-delete-item-in-list path-params headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 validate token and get user-id
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; TODO: delete item from db
          ()
        )
      )
    )
  )
)
