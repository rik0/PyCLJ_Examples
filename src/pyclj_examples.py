from clojure.lang import RT, Compiler
import java.io

Compiler.load(java.io.StringReader("(println 'foo)"))

source = '''
(ns rember)

(defn rember [a lat]
  (cond
   (empty? lat) '()
   (= (first lat) a) (rest lat)
   :else (cons
          (first lat)
          (rember a (rest lat)))))
'''

Compiler.load(java.io.StringReader(source))

rember = RT.var('rember', 'rember')
print rember.invoke(2, range(4))
