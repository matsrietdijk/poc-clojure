(ns poc-clojure.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.middleware.reload :as middleware]))

(def development?
  true) ; TODO: Get the current application environment from ENV variables/configuration

(defn index-handler
  [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body   "Hello, world!"})

(defn not-found-handler
  [req]
  {:headers {"Content-Type" "text/plain"}
   :body    "Not found."})

(defroutes routes
  (GET "/" [] index-handler)
  (route/not-found not-found-handler))

(def app
  (if development?
    (middleware/wrap-reload #'routes)
    (routes)))

(defn -main
  "Application entry point"
  [& args]
  (let [port 8080] ; TODO: Get port from ENV variables/configuration
    (server/run-server app {:port port})
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))
