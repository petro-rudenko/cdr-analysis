(ns com.cybervisiontech.cdr.realtime.bolts
  (:require [backtype.storm [clojure
                             :refer [emit-bolt! defbolt ack! bolt]]]))


(defbolt dummy-hdfs-passer ["calling-party" "caller-party" "starting-time" "duration" "biller-phone" "route-enter" "route-left"] {:prepare true}
  [conf context collector]
  (bolt
   (execute [tuple]
         (emit-bolt! collector tuple) ;; For now we do nothing simply submit whole tuple
         (ack! collector tuple)
         )))
