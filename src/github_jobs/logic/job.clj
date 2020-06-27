(ns github-jobs.logic.job
  (:require [github-jobs.model.job :as model]
            [schema.core :as s]
            [github-jobs.schemata-in.job :as s-in])
  (:import (java.util UUID)))

(s/defn wire->new-job-dto :- model/JobDTO
  [{:keys [id title url category]} :- s-in/JobReference]
  {:job/id        (UUID/randomUUID)
   :job/github-id (UUID/fromString id)
   :job/title     title
   :job/url       url
   :job/category  category})

(s/defn wire->update-job-dto :- model/JobDTO
  [id :- s/Str
   {:keys [title url category]} :- s-in/JobUpdate]
  {:job/github-id (UUID/fromString id)
   :job/title     title
   :job/url       url
   :job/category  category})
