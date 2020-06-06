(ns github_jobs.system
  (:require [com.stuartsierra.component :as component]
            [com.stuartsierra.component.repl
             :refer [reset set-init start stop system]]
            [io.pedestal.http :as http]
            [github-jobs.di.pedestal :as pedestal]
            [github-jobs.routes-hello-word :as routes]
            [github-jobs.service :as service]))

(defn new-system
  [env]
  (component/system-map
    :service-map
    {:env          env
     ::http/routes service/routes
     ::http/type   :jetty
     ::http/port   8890
     ::http/join?  false}

    :pedestal
    (component/using (pedestal/provides-pedestal) [:service-map])))
