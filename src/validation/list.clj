(ns validation.list)

(defn validate-add-item-to-list
  [body-params headers]
  (if (= (headers "authorization") nil)
    "Missing auth token."
    (let [{value :value priority :priority} body-params]
      (if (or (not= (type value) String) (< (count value) 3))
        "Value must present and more than 3 characters."
        (if (or (not= (type priority) String) (and (not= priority "low") (not= priority "medium") (not= priority "high")))
          "Priority must present and its value cannot be other than 'low' or 'medium' or 'high'."
          nil
        )
      )
    )
  )
)

(defn validate-get-list-items
  [headers]
  (if (= (headers "authorization") nil)
    "Missing auth token."
    nil
  )
)

(defn validate-update-item-in-list
  [body-params headers]
  (if (= (headers "authorization") nil)
    "Missing auth token."
    (let [{item-id :item-id value :value priority :priority} body-params]
      (if (or (not= (type item-id) String) (not= (count item-id) 24))
        "Valid item id required."
        (if (or (not= (type value) String) (< (count name) 3))
          "Value must present and more than 3 characters."
          (if (or (not= (type priority) String) (and (not= priority "low") (not= priority "medium") (not= priority "high")))
            "Priority must present and its value cannot be other than 'low' or 'medium' or 'high'."
            nil
          )
        )
      )
    )
  )
)

(defn validate-mark-item-in-list-as-complete
  [body-params headers]
  (if (= (headers "authorization") nil)
    "Missing auth token."
    (let [{item-id :item-id} body-params]
      (if (or (not= (type item-id) String) (not= (count item-id) 24))
        "Valid item id required."
        nil
      )
    )
  )
)

(defn validate-delete-item-in-list
  [path-params headers]
  (if (= (headers "authorization") nil)
    "Missing auth token."
    (let [{item-id :item-id} path-params]
      (if (or (not= (type item-id) String) (not= (count item-id) 24))
        "Valid item id required."
        nil
      )
    )
  )
)
