(ns github-jobs.controller
  (:require [github-jobs.data.job :as db]
            [github-jobs.schemata.job :as schemata]
            [github-jobs.logic.job :as logic-job]
            [schema.core :as s]
            [schema.coerce :as coerce]))

(s/defn get-jobs :- [schemata/JobReference]
  [wire
   db-conn]
  (->> wire
       (db/get-jobs! db-conn)
       (mapv logic-job/datom-job->wire)))

(s/defn save-job-async
  [wire :- schemata/JobReference
  db-conn]
;; TODO: call topic of #CATEGORY-OF-JOB than save job, force to wait if will be necessary
;; this is not the best practice for this scenario, it's only something
;; I did to practice this kind of architecture

  ;; TODO: check return threading and if gets wrong, throw exception
  (->> wire
       logic-job/wire->new-dto
       (db/insert-job! db-conn)))

(s/defn update-job-async
  [{github-id :github-id}
   wire :- schemata/JobUpdate
   db-conn]
  (->> wire
       logic-job/wire->update-dto
       (db/update-job! db-conn (coerce/string->uuid github-id))))

(defn delete-job-async
  [{github-id :github-id}
   db-conn]
  (db/retract-job! db-conn (coerce/string->uuid github-id)))
