(ns github-jobs.data.schemas
  (:require [datomic.api :as d]))

(def ^:private job-schema
  [{:db/ident       :job/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity}
   {:db/ident       :job/github-id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/doc         "GitHub ID reference"
    :db/unique      :db.unique/value}
   {:db/ident       :job/title
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Job Title"}
   {:db/ident       :job/url
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "URL link to Job Description"}
   {:db/ident       :job/category
    :db/valueType   :db.type/string                         ; TODO: change later to "ref"
    :db/cardinality :db.cardinality/many
    :db/doc         "Category choose by user"}])

(def ^:private category-schema
  [{:db/ident       :category/id
    :db/valueType   :db.type/uuid
    :db/cardinality :db.cardinality/one
    :db/unique      :db.unique/identity}
   {:db/ident       :category/name
    :db/valueType   :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc         "Category Name"}])

(defn create
  [conn]
  (d/transact conn category-schema)
  (d/transact conn job-schema))
