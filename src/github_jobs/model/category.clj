(ns github-jobs.model.category
  (:require [schema.core :as s]))

(s/defschema CategoryDTO
  {:category/id   s/Uuid
   :category/name s/Str})
