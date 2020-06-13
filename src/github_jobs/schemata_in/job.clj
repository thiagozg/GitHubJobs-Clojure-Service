(ns github-jobs.schemata-in.job
  (:require [schema.core :as s]))

(s/defschema JobReference
  {:id       s/Str ; ID from GitHub Api
   :title    s/Str
   :url      s/Str
   :category [s/Str]})
