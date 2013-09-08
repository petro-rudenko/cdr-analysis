(ns com.cybervisiontech.cdr.realtime.spouts
  (:require [backtype.storm [clojure
                             :refer [emit-spout! defspout ack! spout]]]
            [com.cybervisiontech.cdr.realtime.cdr-generator :as gen :only [gen-cdr]])
  (:gen-class))


(defspout cdr-generator ["calling-party" "caller-party" "starting-time" "duration" "biller-phone" "route-enter" "route-left"]
  [conf context collector]
  (let [cdr (gen/gen-cdr)]
    (spout
     (nextTuple []
       (emit-spout! collector cdr))
     (ack [id]
        ;; You only need to define this method for reliable spouts
        ;; (such as one that reads off of a queue like Kestrel)
        ;; This is an unreliable spout, so it does nothing here
          ))))
