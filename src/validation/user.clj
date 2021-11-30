(ns validation.user)

(defn validate-register
  [body-params]
  (let [{name :name email :email password :password } body-params]
    (if (or (not= (type name) String) (< (count name) 3))
    "Name must present and more than 3 characters."
    (if (or (not= (type email) String) (< (count email) 3))
      "Email must present and valid."
      (if (or (not= (type password) String) (< (count password) 8))
        "Password must present and contain at least 8 characters."
        nil)))
    )
  )
