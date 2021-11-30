(ns shared.auth
  (:require [buddy.sign.jwt :as jwt]
            [shared.response :as exception]))

(def secret-random-for-token "thisIsASecret")

(defn create-auth-token
  [user-id]
  (try
    {:token (jwt/sign {:user-id user-id} secret-random-for-token)}
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unknown-error :message (.getMessage e)})
    )
  )

(defn validate-token-and-return-user-id
  [token]
  (try
    (jwt/unsign token secret-random-for-token)
    (catch RuntimeException e
      (println (.getMessage e))
      {:exception exception/exception-unauthorized :message "not authorized to access"})
    )
  )
