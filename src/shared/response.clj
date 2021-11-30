(ns shared.response)

(defn success-response
  [data]
  {:status 200
   :body data})

(defn exception-validation
  [message]
  {:status 400
   :body {:error message}})

(defn exception-not-found
  [message]
  {:status 404
   :body {:error message}})

(defn exception-unauthorized
  [message]
  {:status 401
   :body {:error message}})

(defn exception-unknown-error
  [stack]
  {:status 500
   :body {:error "Something went wrong. please try again" :stack stack}})