(ns github-jobs.controller
  (:require [github-jobs.data.job :as db]
            [github-jobs.model.job :refer [JobDTO]]
            [github-jobs.schemata.job :as schemata]
            [github-jobs.logic.job :refer [wire->new-job datom-job->wire]]
            [schema.core :as s]))

(s/defn get-jobs :- [schemata/JobReference]
  [db-conn]
  (->> db-conn
       db/get-all-jobs!
       (mapv datom-job->wire)))

(s/defn save-job-async
  [wire :- schemata/JobReference
   db-conn]
  ;; TODO: call topic of #CATEGORY-OF-JOB than save job, force to wait if will be necessary
  ;; this is not the best practice for this scenario, it's only something
  ;; I did to practice this kind of architecture

  ;; TODO: check return threading and if gets wrong, throw exception
  (->> wire
       wire->new-job
       (db/insert-job! db-conn)))

(s/defn update-job-async
  [wire :- schemata/JobUpdate
   job-github-id :- s/Str
   db-conn]
  (->> wire
       wire->new-job
       (db/update-job! db-conn job-github-id)))

(s/defn delete-job-async
  [job-github-id :- s/Str
   db-conn]
  (db/retract-job! db-conn job-github-id))
