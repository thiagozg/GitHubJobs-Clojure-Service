(ns github-jobs.logic.job
  (:require [github-jobs.model.job :as model]
            [schema.core :as s]
            [github-jobs.schemata.job :as schemata]
            [schema.coerce :as coerce])
  (:import (java.util UUID)))

(s/defn datom-job->wire :- schemata/JobReference
  [{:job/keys [github-id title url category]}]
  {:id       (.toString github-id)
   :title    title
   :url      url
   :category category})

(s/defn wire->new-job :- model/JobDTO
  [{:keys [id title url category]} :- schemata/JobReference]
  {:job/id        (UUID/randomUUID)
   :job/github-id (coerce/string->uuid id)
   :job/title     title
   :job/url       url
   :job/category  category})

(s/defn wire->update-job :- model/JobDTO
  [id :- s/Str
   {:keys [title url category]} :- schemata/JobUpdate]
  {:job/github-id (coerce/string->uuid id)
   :job/title     title
   :job/url       url
   :job/category  category})
