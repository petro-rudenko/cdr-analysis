(defproject cdr-processing "0.1.0-SNAPSHOT"
  :description "CDR data analysis on Storm"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.json "0.2.2"]
                 [clj-time "0.5.1"]]
  :profiles {:dev {:dependencies [[storm "0.9.0-wip21"]]}})
