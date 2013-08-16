(ns com.cybervisiontech.cdr.realtime.storm
  (:use [backtype.storm clojure config])
  (:import [backtype.storm StormSubmitter LocalCluster])
  (:require [com.cybervisiontech.cdr.realtime.cdr-generator :as gen :only [gen-cdr]])
  (:gen-class))


(defspout cdr-generator ["calling-party" "caller-party" "starting-time" "duration" "biller-phone" "route-enter" "route-left"]
  [conf context collector]
  (let [cdr (gen/gen-cdr)]
    (spout
     (nextTuple []
       (emit-spout! collector [cdr]))
     (ack [id]
        ;; You only need to define this method for reliable spouts
        ;; (such as one that reads off of a queue like Kestrel)
        ;; This is an unreliable spout, so it does nothing here
          ))))


(defbolt dummy-hdfs-passer ["calling-party" "caller-party" "starting-time" "duration" "biller-phone" "route-enter" "route-left"] {:prepare true}
  [conf context collector]
  (bolt
   (execute [tuple]
         (emit-bolt! collector tuple) ;; For now we do nothing simply submit whole tuple
         (ack! collector tuple)
         )))

(defn mk-topology []
  (topology 
   {"1" (spout-spec cdr-generator :p 4)}
   {"2" (bolt-spec {"1" :shuffle #_"calling-party"}  ;;This bolt would make aggregation on calling party
                    dummy-hdfs-passer :p 10)}))


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

