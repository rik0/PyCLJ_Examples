(ns pyfactory-base
  (:use [clojure.contrib.str-utils :only [str-join]])
  (:use [clojure.contrib.repl-utils :only [show]])
  (:import
    [org.python.util PythonInterpreter]
    [org.python.core Py]))


(defn make-factory
  [module klass]
  (let [interpreter (PythonInterpreter.)
        import-command (str-join " " ["from" module "import" klass])]
    (.exec interpreter import-command)
    (.get interpreter klass)))


(def spam-and-eggs
  (make-factory "example" "SpamAndEggs"))

(def inst
  (.__call__ spam-and-eggs
    (Py/java2py 1)))

(println inst)
(println (.invoke inst "hasSpam"))
