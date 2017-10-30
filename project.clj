(defproject hotel-watcher "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-json "0.5.3"]
                 [clj-http "3.7.0"]
                 [enlive "1.1.6"]]
  :main ^:skip-aot hotel-watcher.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
