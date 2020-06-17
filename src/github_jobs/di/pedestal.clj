(ns github-jobs.di.pedestal
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.interceptor :as i]
            [clojure.pprint :refer :all]))

(defn- test?
  [service-map]
  (= :test (:env service-map)))

(defn- insert-datomic-interceptor
  "Returns an interceptor which associates datomic in the
  Pedestal context map."
  [datomic]
  (i/interceptor
    {:name  ::insert-context
     :enter (fn [context]
              (assoc-in context [:request :datomic] datomic))}))

(defn- add-pedestal-interceptor
  "Adds an interceptor to the pedestal which associates the
  component into the Pedestal context map. Must be called
  before io.pedestal.http/create-server.
  "
  [service-map datomic]
  (update-in service-map [::http/interceptors]
             #(vec (->> % (cons (insert-datomic-interceptor datomic))))))

(defrecord Pedestal
  [service-map service datomic]
  component/Lifecycle
  (start
    [this]
    (if service
      this
      (cond-> service-map
              true (add-pedestal-interceptor datomic)
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
