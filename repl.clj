(require '[clj-json.core :as json])
(require '[clj-http.client :as http])
(require '[net.cgrand.enlive-html :as html])
(require '[hotel-watcher.core :as c])

(def url "https://www.hotelurbano.com/hoteis/olimpia/thermas-olimpia-resort-12?utm_source=Hu&utm_medium=Home-SP&utm_campaign=thermas-olimpia-resort-12&utm_term=thermas-olimpia-resort-12&utm_content=thermas-olimpia-resort-12")

(def page (-> url http/get :body))

(def html (html/html-snippet page))

(def rooms (html/select html [:#wrap-rooms :.room-box]))

(def room (first rooms))

(-> "input.json" slurp json/parse-string (get "urls"))