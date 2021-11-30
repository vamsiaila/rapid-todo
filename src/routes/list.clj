(ns routes.list)

(defn temp-list-handler
  [_]
  {:status 200
   :body "to be replaced with the real handler"})

(defn get-list-routes
  []
  ["/add" {:post temp-list-handler}]
  ["/get" {:get temp-list-handler}]
  ["/update" {:put temp-list-handler}]
  ["/delete" {:delete temp-list-handler}]
  )