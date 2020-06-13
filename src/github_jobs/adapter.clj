(ns github-jobs.adapter
  (:require [schema.utils :as s-utils]
            [schema.coerce :as coerce]
            [io.pedestal.interceptor.helpers :as interceptor]))

(defn- coerce-and-validate! [schema matcher data]
  (let [coercer (coerce/coercer schema matcher)
        result (coercer data)]
    (if (s-utils/error? result)
      (throw (IllegalArgumentException. (format "Value does not match schema: %s"
                                                (s-utils/error-val result))))
      result)))

(defn coerce-body-request
  [schema]
  (interceptor/on-request ::payload
                          (fn [request]
                            (dissoc
                              (->> (:json-params request)
                                   (coerce-and-validate! schema coerce/json-coercion-matcher)
                                   (assoc request :payload))
                              :json-params))))
