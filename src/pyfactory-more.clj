(ns pyfactory-more
  (:use [clojure.contrib.str-utils :only [str-join]])
  (:use [clojure.contrib.repl-utils :only [show]])
  (:import
    [org.python.util PythonInterpreter]
    [org.python.core Py PyObject]))


(defn make-factory
  [module klassname]
  (let [interpreter (PythonInterpreter.)
        import-command (str-join " " ["from" module "import" klassname])]
    (.exec interpreter import-command)
    (let [klass (.get interpreter klassname)]
      (fn [& args]
        (.__call__ klass
          (into-array PyObject
            (map #(Py/java2py %) args)))))))


(def spam-and-eggs (make-factory "example" "SpamAndEggs"))

(def inst (spam-and-eggs 1))

(println inst)
(println (.invoke inst "hasSpam"))
