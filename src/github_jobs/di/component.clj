(ns github-jobs.di.component
  (:require [com.stuartsierra.component :as component]
            [github-jobs.di.pedestal :as pedestal]
            [github-jobs.di.http-config :as http-config]
            [github-jobs.di.context-deps :as context-deps]
            [github-jobs.di.datomic :as datomic]))

(defn start-server [environment]
  (component/system-map
    :datomic (datomic/provides)
    :service-map (http-config/provides environment)
    :context-deps (component/using (context-deps/provides) [:datomic])
    :pedestal (component/using (pedestal/provides) [:service-map :context-deps])))
