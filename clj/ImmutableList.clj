(ns clj.ImmutableList
  (:gen-class
    :name clj.ImmutableList
    :extends org.python.core.PyObject
    :implements [clojure.lang.IPersistentList]
    :state state
    :init init
    :constructors {[java.util.Collection], []})
  (:import [org.python.core PyObject PyInteger Py]))

(defn -init [coll]
  [[coll] (apply list coll)])

(defmacro delegate-to-state [sym]
  `(defn ~(symbol (str "-" sym)) [this#]
      (~sym (.state this#))))

(delegate-to-state peek)
(delegate-to-state pop)
(delegate-to-state count)
(delegate-to-state empty)
(delegate-to-state seq)

(defn -equiv [this other]
  (= (.state this) other))

(defn -cons [this other]
  (cons (.state this) other))

(defn -__finditem__ [this index]
  (let [index (if (instance? Number index)
                  index
                  (Py/tojava index Number))]
    (try
      (Py/java2py (nth (.state this) index))
      (catch IndexOutOfBoundsException e
        (throw (Py/IndexError (.toString e)))))))
