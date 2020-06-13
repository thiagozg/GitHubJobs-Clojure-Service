(ns github-jobs.di.datomic
  (:require [com.stuartsierra.component :as component]
            [github-jobs.data.schemas :as db-schemas]
            [datomic.api :as d]))

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
  [db-uri]
  (->Datomic db-uri))
