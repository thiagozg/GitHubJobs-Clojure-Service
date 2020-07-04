(ns github-jobs.data.job
  (:require [schema.core :as s]
            [github-jobs.model.job :refer [JobDTO]]
            [github-jobs.model.category :refer [CategoryDTO]]
            [datomic.api :as d]))

(defn get-jobs!
  [conn
   {:keys [title
           category
           github-id]}]
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
            github-id (->
                        (update-in [:query :in] conj '?github-id)
                        (update-in [:query :where] conj '[?job :job/github-id ?github-id])
                        (update-in [:args] conj github-id))
            true d/query)))

(s/defn insert-job!
  [conn
   job :- JobDTO]
  (d/transact conn [job]))

(s/defn find-job!
  [conn
   github-id]
  (let [jobs-founded (get-jobs! conn {:github-id github-id})]
    (if (not-empty jobs-founded)
      (first jobs-founded)
      (throw (IndexOutOfBoundsException.
               (format "This github id: %s was not founded!" github-id))))))

(s/defn update-job!
  [conn
   github-id :- s/Uuid
   job :- JobDTO]
  ;; TODO update with github-job-id
  (d/transact conn [job]))

(s/defn retract-job!
  [conn
   github-id :- s/Uuid]
  (find-job! conn github-id)
  (d/transact conn [[:db/retractEntity [:job/github-id github-id]]]))

(s/defn upsert-category!
  [conn
   category :- CategoryDTO]
  (d/transact conn category))
