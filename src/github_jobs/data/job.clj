(ns github-jobs.data.job
  (:require [schema.core :as s]
            [github-jobs.model.job :refer [JobDTO]]
            [github-jobs.model.category :refer [CategoryDTO]]
            [datomic.api :as d]))

(s/defn get-all-jobs!
  [conn]
  (d/q '[:find [(pull ?job [*]) ...]
         :where [?job :job/id]]
       (d/db conn)))

; TODO: fetch jobs by title and/or categories
(s/defn get-jobs-by!
  [conn
   title :- s/Str
   category :- [s/Str]]
  (d/q '[:find [(pull ?job [*]) ...]
         :where [?job :job/id]]
       (d/db conn)))

(s/defn insert-job!
  [conn
   job :- JobDTO]
  (d/transact conn [job]))

(s/defn update-job!
  [conn
   github-job-id :- s/Str
   job :- JobDTO]
  ;; TODO update with github-job-id
  (d/transact conn [job]))

(s/defn retract-job!
  [conn
   job-id :- s/Str]
  (d/transact conn '[:db/retractEntity [:job/id job-id]]))

(s/defn upsert-category!
  [conn
   category :- CategoryDTO]
  (d/transact conn category))
