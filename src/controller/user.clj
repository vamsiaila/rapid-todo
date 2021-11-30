(ns controller.user
  (:require [controller.response :as response-handler]))

(defn register-user
  [request]
  ; step-1 validate incoming request using validation package
  ; step-2 add user in database using database package
  ; step-3 generate token with user id as payload
  ; step-4 return the token in response
  (response-handler/success-response {:token "123456789"})
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
