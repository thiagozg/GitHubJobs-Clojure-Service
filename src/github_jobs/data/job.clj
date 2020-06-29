(ns github-jobs.data.job
  (:require [schema.core :as s]
            [github-jobs.model.job :refer [JobDTO]]
            [github-jobs.model.category :refer [CategoryDTO]]
            [datomic.api :as d]))

(defn get-jobs!
  [conn
   {:keys [title
           category]}]
  (let [query-base {:query '{:find  [[(pull ?job [*]) ...]]
                             :in    [$]
                             :where [[?job :job/id _]]}
                    :args  [(d/db conn)]}]
    (cond-> query-base
            title (->
                    (update-in [:query :in] conj '?title)
                    (update-in [:query :where] conj '[?job :job/title ?title])
                    (update-in [:args] conj title))
            category (->
                    (update-in [:query :in] conj '?category)
                    (update-in [:query :where] conj '[?job :job/category ?category])
                    (update-in [:args] conj category))
            true d/query)))

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
