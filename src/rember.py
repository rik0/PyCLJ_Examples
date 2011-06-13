import pyclj

@pyclj.clojure
def rember(a, lat):
    '''
    (defn rember 
      "Remove first occurrence of a from lat"
      [a lat]
      (cond
        (empty? lat) '()
        (= (first lat) a) (rest lat)
        :else (cons
          (first lat)
          (rember a (rest lat)))))
    '''



print rember(2, range(4))
print help(rember)
