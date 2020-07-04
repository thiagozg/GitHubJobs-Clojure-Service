(ns github-jobs.logic.job
  (:require [github-jobs.model.job :as model-job]
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

(s/defn wire->new-dto :- model-job/NewDto
  [{:keys [id title url category]} :- schemata/JobReference]
  {:job/id        (UUID/randomUUID)
   :job/github-id (coerce/string->uuid id)
   :job/title     title
   :job/url       url
   :job/category  category})

(s/defn wire->update-dto :- model-job/UpdateDto
  [{:keys [title url category]} :- schemata/JobUpdate]
  (cond-> {}
          title (assoc :job/title title)
          url (assoc :job/url url)
          category (assoc :job/category category)))
