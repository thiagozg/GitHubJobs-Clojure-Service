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

(defn get-all-jobs
  [db]
  (d/q '[:find (pull ?job [*])
         :where [?job :job/id]]
       db))
