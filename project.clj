(defproject cdr-processing "0.1.0-SNAPSHOT"
  :description "CDR data analysis on Storm, Hadoop, Spark and other clever words."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.json "0.2.2"]
                 [clj-time "0.5.1"]
                 [org.apache.hadoop/hadoop-core "1.2.1"]] ; For local mode since of ZK 3.4 problem
                 ;; [org.apache.hadoop/hadoop-hdfs "2.0.6-alpha"] ; For deployment
                 ;; [org.apache.hadoop/hadoop-common "2.0.6-alpha"]]
  :profiles {:dev {:dependencies [[storm "0.9.0-wip21"]]}})
