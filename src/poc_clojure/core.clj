(ns poc-clojure.core
  (:gen-class)
  (:require [org.httpkit.server :as server]))

(defn app
  [req]
  {:status 200
   :header {"Content-Type" "text/plain"}
   :body   "Hello, world!"})

(defn -main
  "Application entry point"
  [& args]
  (let [port 8080]
    (server/run-server app {:port port})
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))
