(ns rapid-todo.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [reitit.ring :as ring]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [database.db :as db]
            [routes.user :as user-routes]
            [routes.task :as task-routes])
  (:gen-class))

(defn string-handler
  [_]
  {:status 200
   :body "on the code root"}
  )

(def app
  (ring/ring-handler
    (ring/router
      ["/api"
       ["" string-handler]
       ["/users" (user-routes/get-user-routes)]
       ["/tasks" (task-routes/get-task-routes)]
       ]
      {:data {:muuntaja m/instance
              :middleware [muuntaja/format-middleware]}}
      )
    (constantly {:status 404, :body "No such route exist"})
    )
  )

(defn -main
  []
  ; establishing mongodb connection
  (db/establish-connection)
  (println "mongodb connected")

  ; starting server on port 3000
  (run-jetty app {:port 3000
                  :join? false})
  (println "Server listening on port 3000\n")
  )
