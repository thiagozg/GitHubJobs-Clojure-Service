(ns github-jobs.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.interceptor.error :as error-int]
            [github-jobs.controller :as controller]
            [github-jobs.schemata-in.job :as s-in]
            [github-jobs.adapter :as adapter]
            [ring.util.response :as ring-resp]
            [taoensso.timbre :as timbre]))

(defn about-page
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))

(defn save-new-job
  [{:keys [payload datomic]}]
  (controller/save-job-async payload (:conn datomic))
  {:status 201})

(defn- log-error
  [ex function]
  (do (timbre/error ex) function))

(def ^:private service-error-handler
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

(def routes
  (route/expand-routes
    `[[["/api" ^:interceptors [service-error-handler]

        ; TODO: remove this later
        ["/about" ^:interceptors [(body-params/body-params)
                                  http/html-body]
         {:get about-page}]

        ["/job" ^:interceptors [(body-params/body-params)
                                (adapter/coerce-body-request s-in/JobReference)
                                http/json-body]
         {:post save-new-job}]

        ]]]))
