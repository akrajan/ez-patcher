(ns com.akr.ez-patcher.core
  (:use [clojure.string :only (replace-first)])
  (:require [cheshire.core :refer [parse-string]]
            [clojure.reflect :refer [reflect resolve-class]]
            [clojure.set :as set]
            [clojure.pprint :refer [pprint]]
            [clojure.core.match :refer [match]])
  (:import [clojure.lang Reflector]
           [java.lang.reflect Field ParameterizedType])
  (:gen-class :name com.pro.akr.ezPatcher.Patcher
              :methods [[patch [Object String] Object]
                        [toArray [String Class] Object]]))


(gen-interface
 :name com.pro.akr.ezPatcher.Comparator
 :methods [[isSame [Object] Boolean]])


(defn param-type [x]
  (cond
   (and (class? x) (. x isEnum)) :enum
   (class? x) :class
   :else :object))

(defmulti instantiate param-type)

(defmethod instantiate :class [cls]
  (let [member-type (symbol (replace-first (str cls) #".* " ""))
        new-obj-statement `(new ~member-type)]
    (eval new-obj-statement)))

(defmethod instantiate :object [x] x)

(defmethod instantiate :enum [x] x)

(defn setter-name [property]
  (let [x (subs property 0 1)
        xs (subs property 1)]
    (str "set" (clojure.string/upper-case x) xs)))


(defn getter-name [property]
  (let [x (subs property 0 1)
        xs (subs property 1)]
    (str "get" (clojure.string/upper-case x) xs)))


(defn fetch-method [reflector method-name]
  (first (set/select #(= (:name %) (symbol method-name)) (:members reflector))))


(defn construct [klass & args]
  (.newInstance
    (.getConstructor klass (into-array java.lang.Class (map type args)))
    (object-array args)))


(defn get-nested-object [obj reflector property]
  (let [cur-val (Reflector/invokeInstanceMethod obj
                                                (getter-name property)
                                                (to-array []))]
    (or cur-val
        (let [member-type (:type (fetch-method reflector property))]
          (eval `(new ~member-type))))))


(declare update-with)

(defn construct [klass & args]
  (clojure.lang.Reflector/invokeConstructor klass (into-array Object args)))

(defn obj->class-name [obj]
  (symbol (replace-first (str (class obj)) #".* " "")))

(defn enum? [reflector]
  (contains? (or (:bases reflector) #{})
             'java.lang.Enum))

(defn get-class-by-name [klass-name]
  (try
    (. Class forName (str klass-name))
    (catch Exception e
      nil)))

(defn is-enum-class-name? [enum-name]
  (if-let [klass (get-class-by-name enum-name)]
    (enum? (reflect klass))))

(defn set-val [obj reflector prop value]
  (let [current-value (Reflector/invokeInstanceMethod obj
                                                      (getter-name prop)
                                                      (to-array []))
        setter (setter-name prop)
        setter-meta (fetch-method reflector setter)
        property-type (first (:parameter-types setter-meta))
        is-enum-property? (is-enum-class-name? property-type)]
    ;; (println prop " Value: " current-value " -> " value)
    ;; (println prop " Type : " (class value) " -> " property-type)
    ;; (println "class-name "(obj->class-name value) " enum-check = " is-enum-property?)

    (let [final-val
          (match [value (obj->class-name value) property-type is-enum-property?]

                 [nil _ _ _] nil
                 [_ 'clojure.lang.PersistentArrayMap _ _] (let [nested-object (get-nested-object obj reflector prop)]
                                                          (update-with nested-object value)
                                                          nested-object)
                 [_ 'clojure.lang.PersistentVector _ _] (let [generic-classes (.. (class obj)
                                                                                (getDeclaredField prop)
                                                                                (getGenericType)
                                                                                (getActualTypeArguments))
                                                            klass (first generic-classes)
                                                            implements-comparator? (instance? com.pro.akr.ezPatcher.Comparator (instantiate klass))
                                                            existing-array (into [] (or current-value []))
                                                            new-array (for [x value
                                                                            :let [obj (instantiate klass)]]
                                                                        (do
                                                                          (update-with obj x)
                                                                          (if implements-comparator?
                                                                            (if-let [matching-element (some (fn [elem]
                                                                                                              (and (. elem isSame obj)
                                                                                                                   elem))
                                                                                                            existing-array)]
                                                                              (do (update-with matching-element x)
                                                                                  matching-element)
                                                                              obj)
                                                                            obj)))]
                                                        (java.util.ArrayList. new-array))

                 [_ 'java.lang.Double  'java.lang.Float     _]   (float value)
                 [_ 'java.lang.Integer 'java.lang.Double    _]   (double value)
                 [_ 'java.lang.Float   'java.lang.Double    _]   (double value)
                 [_ 'java.lang.Long    'java.lang.Integer   _]   (int value)
                 [_ 'java.lang.Integer 'java.lang.Long      _]   (long value)
                 [_ 'java.lang.String  'java.lang.Character _]   (first value)
                 [_ 'java.lang.String  _               true]   (let [klass (get-class-by-name property-type)]
                                                                 (Reflector/invokeStaticMethod klass "valueOf" (to-array [value])))
                 :else (-> property-type
                             (resolve)
                             (cast value)))]

      (Reflector/invokeInstanceMethod obj
                                      setter
                                      (to-array [final-val])))
    obj))


(defn update-with [obj hash]
  (let [obj (instantiate obj)
        r (reflect obj)]
    (doseq [[k v] hash]
      (set-val obj r k v))
    obj))

(defn create-array-with [arr klass]
  (for [x arr
        :let [obj (instantiate klass)]]
    (do
      (update-with obj x)
      obj)))

(defn -patch [self obj string]
  (let [hsh (parse-string string)]
    (cond (= (empty hsh) {}) (update-with obj hsh)
          (= (empty hsh) []) (throw "Array class unspecified.")
          :else hsh)))


(defn -toArray [self string klass]
  (let [hsh (parse-string string)]
    (cond (= (empty hsh) []) (create-array-with hsh klass)
          :else (throw "Not an array."))))
