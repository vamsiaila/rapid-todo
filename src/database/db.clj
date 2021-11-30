(ns database.db
  (:require [monger.core :as mg]))

(def connection (atom nil))

(defn establish-connection
  []
  (let [conn (mg/connect)
        db   (mg/get-db conn "rapid-todo")]
    (reset! connection db)
    )
  )

(defn get-connection
  []
  @connection
  )