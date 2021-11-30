(defproject rapid-todo "0.1.0-SNAPSHOT"
  :description "This is a sample clojure rest api project. It does crud operations and authentication"
  :url "http://localhost:3000"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [ring "1.9.4"]
                 [metosin/reitit "0.5.15"]
                 [metosin/muuntaja "0.6.8"]
                 [com.novemberain/monger "3.1.0"]
                 [buddy/buddy-sign "3.4.1"]]
  :main ^:skip-aot rapid-todo.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
