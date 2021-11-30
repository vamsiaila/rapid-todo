(ns controller.list
  (:require [controller.response :as response-handler]))

(defn add-item-to-list
  [request]
  ; step-1 validate incoming request using validation package
  ; step-2 add item to database using database package
  ; step-3 return the item with id in response
  (response-handler/success-response {:_id "123456789" :value "shoot email" :priority "high" :isCompleted false})
  )

(defn get-list-items
  [request]
  ; step-1 validate incoming request using validation package
  ; step-2 get items from database using database package
  ; step-3 return the items in response
  (response-handler/success-response [{:_id "123456789" :value "shoot email" :priority "high" :isCompleted false}
                                      {:_id "987654321" :value "order food" :priority "medium" :isCompleted true}])
  )

(defn update-item-in-list
  [request]
  ; step-1 validate incoming request using validation package
  ; step-2 update item in database using database package
  ; step-3 return the updated item in response
  (response-handler/success-response {:_id "123456789" :value "shoot email to support" :priority "medium" :isCompleted false})
  )

(defn mark-item-in-list-as-complete
  [request]
  ; step-1 validate incoming request using validation package
  ; step-2 mark item as complete in database using database package
  ; step-3 return the updated item in response
  (response-handler/success-response {:_id "123456789" :value "shoot email to support" :priority "medium" :isCompleted true})
  )

(defn delete-item-in-list
  [request]
  ; step-1 validate incoming request using validation package
  ; step-2 delete item in database using database package
  ; step-3 return the boolean in response
  (response-handler/success-response {:deleted true})
  )
