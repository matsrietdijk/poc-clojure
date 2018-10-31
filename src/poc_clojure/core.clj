(ns poc-clojure.core
  (:gen-class)
  (:require [org.httpkit.server :as server]
            [hiccup.page :as template]
            [hiccup.element :as elem]
            [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [ring.middleware.reload :as middleware]))

(def development?
  true) ; TODO: Get the current application environment from ENV variables/configuration

(defn api-index-handler
  [req]
  {:status  501
   :headers {"Content-Type" "application/json"}
   :body    "{}"})

(defn api-not-found-handler
  [req]
  {:headers {"Content-Type" "application/json"}
   :body    "{}"})

(defn index-handler
  [req]
  (template/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title "PoC | Clojure"]]
    [:body
     [:h1 "Hello, world!"]]))

(defn not-found-handler
  [req]
  (template/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:title "PoC | Clojure"]]
    [:body
     [:h1 "Not Found."]
     [:p "The page you requested could not be found. Go back to " (elem/link-to "/" "Home") "."]]))

(defroutes api-routes
  (GET "/" [] api-index-handler)
  (route/not-found api-not-found-handler))

(defroutes routes
  (GET "/" [] index-handler)
  (context "/api" [] api-routes)
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
