(ns routes.task
  (:require [controller.task :as task-controller]))

(defn get-task-routes
  []
  [["/add" {:post task-controller/add-task-to-user-tasks}]
   ["/get" {:get task-controller/get-user-tasks}]
   ["/update" {:put task-controller/update-user-task}]
   ["/complete" {:put task-controller/mark-user-task-as-complete}]
   ["/delete/:task-id" {:delete task-controller/delete-user-task}]]
  )
