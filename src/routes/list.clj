(ns routes.list
  (:require [controller.list :as list-controller]))

(defn temp-list-handler
  [_]
  {:status 200
   :body "to be replaced with the real handler"})

(defn get-list-routes
  []
  [["/add" {:post list-controller/add-item-to-list}]
  ["/get" {:get list-controller/get-list-items}]
  ["/update" {:put list-controller/update-item-in-list}]
  ["/complete" {:put list-controller/mark-item-in-list-as-complete}]
  ["/delete/:item-id" {:delete list-controller/delete-item-in-list}]]
  )