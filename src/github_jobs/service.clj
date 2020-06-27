(ns github-jobs.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [github-jobs.controller :as controller]
            [github-jobs.schemata-in.job :as s-in]
            [github-jobs.adapter :as adapter]
            [ring.util.response :as ring-resp]))

(defn about-page
  [request]
  (ring-resp/response (format "Clojure %s - served from %s"
                              (clojure-version)
                              (route/url-for ::about-page))))

(defn save-new-job
  [{:keys [payload]
    {:keys [datomic]} :context-deps}]
  (controller/save-job-async payload (:conn datomic))
  {:status 201})

(defn update-job
  [{:keys [path?? payload]
    {:keys [datomic]} :context-deps}]
  (controller/update-job-async payload (:conn datomic))
  {:status 200})

(defn delete-job
  [{:keys [path??]
    {:keys [datomic]} :context-deps}]
  (controller/update-job-async path?? (:conn datomic))
  {:status 200})

(def routes
  (route/expand-routes
    `[[["/api" ^:interceptors [adapter/service-error-handler]

        ; TODO: remove this later
        ["/about" ^:interceptors [(body-params/body-params)
                                  http/html-body]
         {:get about-page}]

        ["/job" ^:interceptors [(body-params/body-params)
                                (adapter/coerce-body-request s-in/JobReference)
                                http/json-body]
         {:post save-new-job}]

        ["/job" ^:interceptors [(adapter/coerce-path-param ???)
                                (body-params/body-params)
                                (adapter/coerce-body-request s-in/JobReference)
                                http/json-body]
         {:put update-job}]

        ["/job" ^:interceptors [(adapter/coerce-path-param ???)
                                http/json-body]
         {:delete delete-job}]

        ]]]))
