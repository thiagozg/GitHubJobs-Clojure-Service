(ns github-jobs.model.job
  (:require [schema.core :as s]))

(s/defschema NewDto
  {:job/id        s/Uuid
   :job/github-id s/Uuid
   :job/title     s/Str
   :job/url       s/Str
   :job/category  [s/Str]}) ; TODO: change later to be a "ref" of CategoryDTO


(s/defschema UpdateDto
  {(s/optional-key :job/title)    s/Str
   (s/optional-key :job/url)      s/Str
   (s/optional-key :job/category) [s/Str]}) ; TODO: change later to be a "ref" of CategoryDTO