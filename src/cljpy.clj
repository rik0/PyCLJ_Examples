(ns cljpy
  (:use [clojure.contrib.str-utils :only [str-join]])
  (:import
    [org.python.util PythonInterpreter]))


(defn load-python-factory [module klass]
  (let [interpreter (PythonInterpreter.)
        import-command (str-join " " ["from" module "import" klass])]
    (.exec interpreter import-command)))

(defn clojurize [pyobj]
  (try
    (.__tojava__ pyobj)
    (catch IllegalArgumentException
      pyobj)))

(defn load-python-module [python-module]
  (let [interpreter (PythonInterpreter.)
        import-command (str-join " " ["import" python-module])]
    (.exec interpreter import-command)
    (.get interpreter python-module)))

(defn inject-python-module [clj-namespace module-object]
  (letfn []
    (let [python-dict (.fastGetDict module-object)]
      (doseq [t (.iteritems python-dict)]
        (intern (ns-name clj-namespace)
          (symbol (.toString (.__getitem__ t 0)))
          (.__getitem__ t 1))))))

(defn pyimport-f [python-module-name]
  (let [clojure-namespace (create-ns python-module-name)
        py-module (load-python-module (str python-module-name))]
    (inject-python-module clojure-namespace py-module)
    (println 'doo)
    clojure-namespace))


(defmacro pyimport [python-module-name]
  (pyimport-f python-module-name))



;(defn test []
;  (pyimport example)
;  (println example/value))

;(test)

(pyimport example)

(println example/value)
(println example/another)
(println example/spam_and_eggs)

(println (.toString example/spam_and_eggs))
(println (.getEggs example/spam_and_eggs))
(println (.hasSpam example/spam_and_eggs))
