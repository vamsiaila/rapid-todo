(ns validation.task)

(defn validate-add-task-to-user-tasks
  [body-params headers]
  (if (= (headers "authorization") nil)
    "Missing auth token."
    (let [{task-name :task-name priority :priority} body-params]
      (if (or (not= (type task-name) String) (< (count task-name) 3))
        "Task name must present and more than 3 characters."
        (if (or (not= (type priority) String) (and (not= priority "low") (not= priority "medium") (not= priority "high")))
          "Priority must present and its value cannot be other than 'low' or 'medium' or 'high'."
          nil
        )
      )
    )
  )
)

(defn validate-get-user-tasks
  [headers]
  (if (= (headers "authorization") nil)
    "Missing auth token."
    nil
  )
)

(defn validate-update-user-task
  [body-params headers]
  (if (= (headers "authorization") nil)
    "Missing auth token."
    (let [{task-id :task-id task-name :task-name priority :priority} body-params]
      (if (or (not= (type task-id) String) (not= (count task-id) 24))
        "Valid task id required."
        (if (or (not= (type task-name) String) (< (count task-name) 3))
          "Task name must present and more than 3 characters."
          (if (or (not= (type priority) String) (and (not= priority "low") (not= priority "medium") (not= priority "high")))
            "Priority must present and its value cannot be other than 'low' or 'medium' or 'high'."
            nil
          )
        )
      )
    )
  )
)

(defn validate-mark-user-task-as-complete
  [body-params headers]
  (if (= (headers "authorization") nil)
    "Missing auth token."
    (let [{task-id :task-id} body-params]
      (if (or (not= (type task-id) String) (not= (count task-id) 24))
        "Valid task id required."
        nil
      )
    )
  )
)

(defn validate-delete-user-task
  [path-params headers]
  (if (= (headers "authorization") nil)
    "Missing auth token."
    (let [{task-id :task-id} path-params]
      (if (or (not= (type task-id) String) (not= (count task-id) 24))
        "Valid task id required."
        nil
      )
    )
  )
)
