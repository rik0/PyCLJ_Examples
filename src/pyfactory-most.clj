(ns pyfactory-most
  (:use [clojure.contrib.str-utils :only [str-join]])
  (:use [clojure.contrib.repl-utils :only [show]])
  (:use [clojure.contrib.seq :only [indexed find-first]])
  (:import
    [org.python.util PythonInterpreter]
    [org.python.core Py PyObject]
    [javax.swing JFrame JButton]
    [java.awt.event ActionListener]))

(defn make-factory
  [module klassname interface]
  (let [interpreter (PythonInterpreter.)
        import-command (str-join " " ["from" module "import" klassname])]
    (.exec interpreter import-command)
    (let [klass (.get interpreter klassname)]
      (fn [& args]
        (.__tojava__ (.__call__ klass
          (into-array PyObject
            (map #(Py/java2py %) args)))
          interface)))))

(defmacro pyclass [qualified-class jtype]
  (let [qualified-class-name (str qualified-class)
        [last-dot-position _] (find-first (fn [[_ e]] (= e \.))
                                          (indexed qualified-class-name))
        [module-name-seq dotted-klass-name-seq] (split-at last-dot-position qualified-class-name)
        klass-name (str-join "" (rest dotted-klass-name-seq))
        module-name (str-join "" module-name-seq)]
    `(def ~(symbol klass-name)
      (make-factory ~(str module-name) ~(str klass-name) ~jtype))))

;(println (macroexpand-1  '(pyclass example.PrintSomething java.awt.event.ActionListener)))

(pyclass example.PrintSomething java.awt.event.ActionListener)

(def evt-printer (PrintSomething))

(defn build-gui [title]
  (let [frame (JFrame. title)
        button (JButton. "CLICK")]
    (.addActionListener button evt-printer)
    (.. frame getContentPane (add button))
    (.pack frame)
    (.setVisible frame true)
    frame))

(build-gui "foo")

