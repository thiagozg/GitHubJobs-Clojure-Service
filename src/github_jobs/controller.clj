(ns github-jobs.controller
  (:require [github-jobs.data.job :as db]
            [github-jobs.schemata-in.job :as s-in]
            [github-jobs.logic.job :refer [wire->new-job-dto]]
            [schema.core :as s]))

(s/defn save-job-async
  [wire :- s-in/JobReference
   db-conn]
  ;; TODO: call topic of #CATEGORY-OF-JOB than save job, force to wait if will be necessary
  ;; this is not the best practice for this scenario, it's only something
  ;; I did to practice this kind of architecture

  ;; TODO: check return threading and if gets wrong, throw exception
  (->> wire
       wire->new-job-dto
       (db/upsert-job! db-conn)))

(s/defn update-job-async
  [wire :- s-in/JobUpdate
   db-conn]
  (->> wire
       wire->new-job-dto
       (db/upsert-job! db-conn)))

(s/defn delete-job-async
  [job-github-id :- s/Str
   db-conn]
  (db/retract-job! db-conn job-github-id))
