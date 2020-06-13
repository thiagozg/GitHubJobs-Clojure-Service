(ns github-jobs.model.job
  (:require [github-jobs.model.category :as model]
            [schema.core :as s])
  (:import (java.util UUID)))

(s/defschema JobDTO
  {:job/id        UUID
   :job/github-id UUID
   :job/title     s/Str
   :job/url       s/Str
   :job/category  [s/Str]}) ; TODO: change later to be a "ref" of CategoryDTO