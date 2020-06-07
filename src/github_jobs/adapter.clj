(ns github-jobs.adapter
  (:require [io.pedestal.interceptor :as i]
            [schema.core :as s]
            [io.pedestal.interceptor.helpers :as interceptor]))

(defn coerce-body-request
  [schema]
  (interceptor/on-request ::payload
                          (fn [request]
                            (dissoc
                              (->> (:json-params request)
                                   (s/validate schema)
                                   (assoc request :payload))
                              :json-params))))
