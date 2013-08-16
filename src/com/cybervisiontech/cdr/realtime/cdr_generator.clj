(ns com.cybervisiontech.cdr.realtime.cdr-generator
  [:require [clojure.data.json :as json]]
  [:require [clj-time.core :as time]]
  [:use [clj-time.format :only [formatter unparse]]]
  [:require [clojure.string :as str :only [replace]]])

(def country-codes 
  ^{:doc "Map country name -> dial code"}
  (into {} 
        (let [records (json/read-str (slurp "resources/CountryCodes.json"))] ; Read json data from file
          (->> 
           records
           (filter #((comp not empty?) (% "dial_code"))) ; Filter non empty dial codes
           (map (fn [rec] [(rec "name") (str/replace (rec "dial_code") #"[+\s]" "")])))))) ;Replace + and spaces in code number

 


(defn gen-ndigit-number 
  "Returns random phone number for specific country.
   i.e. for USA (country code +1) it would be 10 digits number
   for Ukraine (+380) only 9 digits number"
  [^String county-code & num-digits]
  (let [code-length (- (if (nil? num-digits) 11 (first num-digits)) (.length county-code))
        format-str (format "%%0%dd" code-length)
        number (format format-str (biginteger (*' (rand) (Math/pow 10 code-length))))]
    number))


(defn gen-phone
  "Generates random phone number. By default use USA code.
   TODO: add more sophisticated behaviour (e.g. some number take from DB)"
  ([country] (gen-ndigit-number (country-codes country)))
  ([] (gen-ndigit-number "1")))


(defn random-cdr []
  (let [calling-party (gen-phone)
        caller-party (gen-phone)
        time-format (formatter "yyyy-MM-dd HH:MM:SS")
        starting-time (unparse time-format (time/now))
        duration (rand-int (* 3600 24))
        biller-phone calling-party
        route-enter (rand-int 100)
        route-left (if (> (rand) 0.5) route-enter (rand-int 100))]
    [calling-party caller-party
     starting-time duration biller-phone
     route-enter route-left]))


(defn ddos-cdr []
  [])

(defn fraud-cdr []
  [])


(defn gen-cdr 
  ([] (random-cdr))
  ([behavior] 
     (case behavior
       :ddos (ddos-cdr)
       :fraud (fraud-cdr)
       (random-cdr))))
