(ns hotel-watcher.core
  (:use [clojure.pprint])
  (:require [clj-json.core :as json]
            [clj-http.client :as http]
            [net.cgrand.enlive-html :as html]
            [clojure.string :as string])
  (:gen-class))

(defn get-page [url]
  (-> url http/get :body))

(defn parse-to-html [page]
  (html/html-snippet page))

(defn extract-hotel-name [html]
  (-> html
      (html/select [:.page-title-text])
      first
      :content
      first
      string/trim))

(defn extract-room-title [node]
  (-> node
      (html/select [:.room-title])
      first
      :content
      first
      string/trim))

(defn extract-room-price [node]
  (-> node
      (html/select [:.offer-price])
      first
      :content
      first
      string/trim))

(defn room-information [node]
  {:title (extract-room-title node)
   :price (extract-room-price node)})

(defn extract-rooms-information [html]
  (let [rooms (html/select html [:#wrap-rooms :.room-box])]
    (map room-information rooms)))

(defn extract-hotel-information [html]
  {:hotel  (extract-hotel-name html)
   :rooms (into [] (extract-rooms-information html))})

(defn get-hotel-information [url]
  (-> url
      get-page
      parse-to-html
      extract-hotel-information))

(defn -main
  "Extract data from Hotel Urbano..."
  [input output & args]
  (println "Start...")
  (as-> input x
        (slurp x)
        (json/parse-string x)
        (get x "urls")
        (mapv get-hotel-information x)
        (into [] x)
        (hash-map :hotels x)
        (json/generate-string x)
        (spit output x))
  (println "End..."))