(ns github-jobs.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :refer [body-params]]
            [github-jobs.controller :as controller]
            [github-jobs.schemata.job :as schemata]
            [github-jobs.adapter :as adapter]
            [ring.util.response :as ring-resp]))

(defn fetch-jobs
  [{:keys [query-params]
    {{conn :conn} :datomic} :context-deps}]
  (ring-resp/response
    (controller/get-jobs query-params conn)))

(defn save-new-job
  [{:keys [payload]
    {{conn :conn} :datomic} :context-deps}]
  (controller/save-job-async payload conn)
  {:status 201})

(defn update-job
  [{:keys [path-params payload] {{conn :conn} :datomic} :context-deps}]
  (controller/update-job-async path-params payload conn)
  {:status 200})

(defn delete-job
  [{:keys [path-params]
    {{conn :conn} :datomic} :context-deps}]
  (controller/delete-job-async path-params conn)
  {:status 200})

(def routes
  (route/expand-routes
    `[[["/api" ^:interceptors [adapter/service-error-handler]

        ["/job" ^:interceptors [(body-params)
                                http/json-body]
         {:get fetch-jobs}]

        ["/job" ^:interceptors [(body-params)
                                (adapter/coerce-body-request schemata/JobReference)
                                http/json-body]
         {:post save-new-job}]

        ["/job/:github-id" ^:interceptors [(body-params)
                                           (adapter/coerce-body-request schemata/JobUpdate)
                                           http/json-body]
         {:put update-job}]

        ["/job/:github-id"
         {:delete delete-job}]

        ]]]))
