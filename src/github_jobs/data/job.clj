(ns github-jobs.data.job
  (:require [schema.core :as s]
            [github-jobs.model.job :refer [JobDTO]]
            [github-jobs.model.category :refer [CategoryDTO]]
            [datomic.api :as d]))

(s/defn upsert-job!
  [conn
   job :- JobDTO]
  (d/transact conn [job]))

(s/defn upsert-category!
  [conn
   category :- CategoryDTO]
  (d/transact conn category))

(s/defn get-all-jobs! :- [JobDTO]
  [db]
  (d/q '[:find (pull ?job [*])
         :where [?job :job/id]]
       db))

(s/defn retract-job!
  [conn
   job-id :- s/Str]
  (d/transact conn '[:db/retractEntity [:job/id job-id]]))
