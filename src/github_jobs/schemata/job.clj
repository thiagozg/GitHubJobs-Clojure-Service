(ns github-jobs.schemata.job
  (:require [schema.core :as s]))

(s/defschema JobReference
  {:id       s/Str ; ID from GitHub Api
   :title    s/Str
   :url      s/Str
   :category [s/Str]})

(s/defschema JobUpdate
  {(s/optional-key :title)    s/Str
   (s/optional-key :url)      s/Str
   (s/optional-key :category) [s/Str]})