(ns github-jobs.model.job
  (:require
    [schema.core :as s]))

(s/defschema JobDTO
  {:job/id        s/Uuid
   :job/github-id s/Uuid
   :job/title     s/Str
   :job/url       s/Str
   :job/category  [s/Str]}) ; TODO: change later to be a "ref" of CategoryDTO