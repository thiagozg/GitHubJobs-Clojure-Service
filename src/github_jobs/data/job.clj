(ns github-jobs.data.job
  (:require [schema.core :as s]
            [github-jobs.model.job :as model-job]
            [github-jobs.model.category :as model-category]
            [datomic.api :as d]))

(s/defn upsert-job
  [conn
   job :- model-job/JobDTO]
  (d/transact conn job))

(s/defn upsert-category
  [conn
   category :- model-category/CategoryDTO]
  (d/transact conn category))
