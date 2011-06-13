from clojure.lang import RT, Compiler, Keyword
import java.io
import functools

def clj_namespace():
    if __name__ == '__main__':
        return 'user'
    else:
        return __name__

def load_string(clojure_code):
    Compiler.load(java.io.StringReader(clojure_code))    

def clojure(fn):
    clojure_code ='(ns %s)\n%s' % (
        clj_namespace(),
        fn.__doc__)
    load_string(clojure_code)

    clojure_fnc = RT.var(clj_namespace(), fn.func_name)

    meta = clojure_fnc.meta()
    fn.__doc__ = meta.get(Keyword.intern('doc'))
    
    def aux(*args):
        return clojure_fnc.invoke(*args)
    functools.update_wrapper(aux, fn)

    return aux




    

