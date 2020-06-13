(ns github-jobs.model.category
  (:require [schema.core :as s])
  (:import (java.util UUID)))

(s/defschema CategoryDTO
  {:category/id   UUID
   :category/name s/Str})
