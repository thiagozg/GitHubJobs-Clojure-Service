(ns github-jobs.di.datomic
  (:require [com.stuartsierra.component :as component]
            [datomic.api :as d]))

(defrecord Datomic
  [db-uri]
  component/Lifecycle
  (start [this]
    (d/create-database db-uri)
    (merge this {:conn (d/connect db-uri)
                 :uri  db-uri}))
  (stop [this]
    (if (:conn this) (d/release (:conn this)))
    (dissoc this :uri :conn)))

(defn provides
  [db-uri]
  (->Datomic db-uri))
