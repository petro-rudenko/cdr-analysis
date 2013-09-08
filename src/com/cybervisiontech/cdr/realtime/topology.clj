(ns com.cybervisiontech.cdr.realtime.topology
  (:require [backtype.storm [clojure
                             :refer [topology spout-spec bolt-spec]]])
  (:require [com.cybervisiontech.cdr.realtime [spouts :refer :all] [bolts :refer :all]] :verbose)
  (:gen-class))



(defn mk-topology []
  (topology 
   {"1" (spout-spec cdr-generator :p 4)}
   {"2" (bolt-spec {"1" ["calling-party"]}  ;;This bolt would make aggregation on calling party
                    dummy-hdfs-passer :p 10)}))
