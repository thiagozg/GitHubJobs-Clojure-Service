(ns github-jobs.di.component
  (:require [com.stuartsierra.component :as component]
            [github-jobs.di.pedestal :as pedestal]
            [github-jobs.di.http-config :as http-config]))

(defn start-server [environment]
  (component/system-map
    :service-map (http-config/provides environment)
    :pedestal (component/using (pedestal/provides) [:service-map])))
