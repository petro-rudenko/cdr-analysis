(ns com.cybervisiontech.cdr.realtime.TopologySubmitter
  (:require [com.cybervisiontech.cdr.realtime.topology :refer [mk-topology]]
            [backtype.storm [config :refer :all]])
  (:import [backtype.storm StormSubmitter LocalCluster])
  (:gen-class))


(defn run-local! []
  (let [cluster (LocalCluster.)]
    (.submitTopology cluster "cdr-storm" {TOPOLOGY-DEBUG true} (mk-topology))
    ;(Thread/sleep 10000)
    ;(.shutdown cluster)
    ))


(defn submit-topology! [name]
  (StormSubmitter/submitTopology
   name
   {TOPOLOGY-DEBUG true
    TOPOLOGY-WORKERS 3}
   (mk-topology)))


(defn -main
  ([]
   (run-local!))
  ([name]
   (submit-topology! name)))

