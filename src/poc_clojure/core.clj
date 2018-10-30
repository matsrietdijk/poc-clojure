(ns poc-clojure.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [ring.middleware.reload :as middleware]))

(def development?
  true) ; TODO: Get the current application environment from ENV variables/configuration

(defn handler
  [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body   "Hello, world!"})

(def app
  (if development?
    (middleware/wrap-reload #'handler)
    (handler)))

(defn -main
  "Application entry point"
  [& args]
  (let [port 8080] ; TODO: Get port from ENV variables/configuration
    (server/run-server app {:port port})
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))
