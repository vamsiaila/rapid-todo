(ns controller.user
  (:require [shared.response :as response-handler]
            [shared.auth :as auth]
            [validation.user :as user-validator]
            [database.user :as user-db]))

(defn register-user
  [{body-params :body-params}]
  ; step-1 validate incoming request using validation package
  (let [error (user-validator/validate-register body-params)]
    (if (not= error nil)
      (response-handler/exception-validation error)
      ; step-2 add user to database using database package
      (let [{exception :exception message :message user-id :user-id} (user-db/add-user-to-db body-params)]
        (if (not= exception nil)
          (exception message)
          ; step-3 generate token with user id as payload
          (let [{exception :exception message :message token :token} (auth/create-auth-token user-id)]
            (if (not= exception nil)
              (exception message)
              ; step-4 return the token in response
              (response-handler/success-response {:token token}))
            )
          )
        )
      )
    )
  )

(defn login-user
  [request]
  ; step-1 validate incoming request using validation package
  ; step-2 get user from database using database package
  ; step-3 generate token with user id as payload
  ; step-4 return the token in response
  (response-handler/success-response {:token "123456789"})
  )

(defn verify-user-token
  [request]
  ; step-1 validate incoming request using validation package
  ; step-3 verify token
  ; step-4 return the boolean in response
  (response-handler/success-response {:valid true})
  )
