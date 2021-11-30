(ns routes.user)

(defn temp-user-handler
  [_]
  {:status 200
   :body "to be replaced with the real handler"})

(defn get-user-routes
  []
  ["/register" {:post temp-user-handler}]
  ["/login" {:post temp-user-handler}]
  ["/verify" {:get temp-user-handler}]
  )
