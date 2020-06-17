(ns github-jobs.di.pedestal
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.interceptor :as i]))

(defn- test?
  [service-map]
  (= :test (:env service-map)))

(defn- insert-context-deps-interceptor
  "Returns an interceptor which associates context dependencies
   in the Pedestal context map."
  [context-deps]
  (i/interceptor
    {:name  ::insert-context
     :enter (fn [context]
              (assoc-in context [:request :context-deps] context-deps))}))

(defn- add-pedestal-interceptor
  "Adds an interceptor to the pedestal which associates the
  component into the Pedestal context map. Must be called
  before io.pedestal.http/create-server."
  [service-map context-deps]
  (update-in service-map [::http/interceptors]
             #(vec (->> % (cons (insert-context-deps-interceptor context-deps))))))

(defrecord Pedestal
  [service-map service context-deps]
  component/Lifecycle
  (start
    [this]
    (if service
      this
      (cond-> service-map
              true (add-pedestal-interceptor context-deps)
              true http/create-server
              (not (test? service-map)) http/start
              true (partial assoc this :service))))

  (stop [this]
    (when (and service (not (test? service-map)))
      (http/stop service))
    (assoc this :service nil)))

(defn provides
  []
  (map->Pedestal {}))
