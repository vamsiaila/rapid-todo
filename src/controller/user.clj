(ns controller.user
  (:require [shared.response :as response-handler]
            [shared.auth :as auth]
            [validation.user :as user-validator]
            [database.user :as user-db]
  )
)

(defn register-user
  [{body-params :body-params}]
  ; step-1 validate incoming request using validation package
  (let [error (user-validator/validate-user-register body-params)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 add user to database using database package
      (let [{exception :exception message :message user-id :user-id name :name} (user-db/add-user-to-db body-params)]
        (if (not= exception nil)
          (exception message)
          ; step-3 generate token with user id as payload
          (let [{exception :exception message :message token :token} (auth/create-auth-token user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return the token in response
              (response-handler/success-response {:token token :name name})
            )
          )
        )
      )
    )
  )
)

(defn login-user
  [{body-params :body-params}]
  ; step-1 validate incoming request using validation package
  (let [error (user-validator/validate-user-login body-params)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 match user credentials with database using database package
      (let [{exception :exception message :message user-id :user-id name :name} (user-db/match-user-credentials-with-db body-params)]
        (if (not= exception nil)
          (exception message)
          ; step-3 generate token with user id as payload
          (let [{exception :exception message :message token :token} (auth/create-auth-token user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return the token in response
              (response-handler/success-response {:token token :name name})
            )
          )
        )
      )
    )
  )
)

(defn verify-user-token
  [{headers :headers}]
  ; step-1 validate incoming request using validation package
  (let [error (user-validator/validate-user-verify headers)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 verify token
      (let [{exception :exception message :message user-id :user-id} (auth/validate-token-and-return-user-id (headers "authorization"))]
        (if (not= exception nil)
          (exception message)
          ; step-3 find user in db
          (let [{exception :exception message :message name :name} (user-db/find-user-with-id-in-db user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return the name and id in response
              (response-handler/success-response {:user-id user-id :name name})
            )
          )
        )
      )
    )
  )
)
