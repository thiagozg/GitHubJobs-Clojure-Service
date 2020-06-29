(ns github-jobs.adapter
  (:require [schema.utils :as s-utils]
            [schema.coerce :as coerce]
            [io.pedestal.interceptor.helpers :as interceptor]
            [io.pedestal.interceptor.error :as error-int]
            [taoensso.timbre :as timbre]))

(defn- log-error
  [ex function]
  (do (timbre/error ex) function))

(def service-error-handler
  (error-int/error-dispatch
    [context ex]

    [{:exception-type :java.lang.IllegalArgumentException}]
    (log-error
      ex (assoc context :response {:status 412
                                   :body   {:message "The request body does not match with contract"}}))

    :else
    (log-error
      ex (assoc context :response {:status 500
                                   :body   "Internal Server Error"}))))

(defn- coerce-and-validate! [schema matcher data]
  (let [coercer (coerce/coercer schema matcher)
        result (coercer data)]
    (if (s-utils/error? result)
      (throw (IllegalArgumentException. (format "Value does not match schema: %s"
                                                (s-utils/error-val result))))
      result)))

(defn coerce-body-request
  [schema]
  (interceptor/on-request ::payload-request
                          (fn [request]
                            (dissoc
                              (->> (:json-params request)
                                   (coerce-and-validate! schema coerce/json-coercion-matcher)
                                   (assoc request :payload))
                              :json-params))))
