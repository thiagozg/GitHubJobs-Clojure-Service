(ns github-jobs.di.http-config
  (:require [github-jobs.service :as service]
            [io.pedestal.http :as http]))

(defn provides
  [environment]
  {:env               environment
   ::http/routes service/routes
   ::http/type   :jetty
   ::http/port   8890
   ::http/join?  false})
