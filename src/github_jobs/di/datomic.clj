(ns github-jobs.di.datomic
  (:require [com.stuartsierra.component :as component]
            [github-jobs.data.schemas :as db-schemas]
            [datomic.api :as d]
            [config.core :refer [env]]))

(defrecord Datomic
  [db-uri]
  component/Lifecycle
  (start [this]
    (d/create-database db-uri)
    (let [conn (d/connect db-uri)]
      (db-schemas/create conn)
      (merge this {:conn conn
                   :uri  db-uri})))
  (stop [this]
    (if (:conn this) (d/release (:conn this)))
    (dissoc this :uri :conn)))

(defn provides
  []
  (->>
    (:datomic-secret-password env)
    (str (:db-uri env))
    ->Datomic))

(comment
  (let [db-uri "datomic:dev://localhost:4334/github-jobs?password=datomic-secret-password"]
    (d/create-database db-uri)
    ;(d/connect db-uri)
    ))
