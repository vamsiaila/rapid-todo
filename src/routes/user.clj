(ns routes.user
  (:require [controller.user :as user-controller]))

(defn get-user-routes
  []
  [["/register" {:post user-controller/register-user}]
  ["/login" {:post user-controller/login-user}]
  ["/verify" {:get user-controller/verify-user-token}]]
  )
