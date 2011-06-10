import clj

py_list = ['a', 'b', 'c']
my_list = clj.ImmutableList(py_list)

print my_list.peek(), type(my_list.peek()), type(py_list[0])
print my_list.pop()
print my_list.count()
print my_list.empty()
print my_list.seq()
print my_list.cons('d')
print my_list.equiv(py_list)
print my_list.equiv(['a', 'b', 'c'])

print my_list[0]
print my_list[1]
print my_list[2]
print my_list[2.4]


try:
    print my_list[3]
except IndexError, e:
    print e
try:
    print my_list['a']
except TypeError, e:
    print e

try:
    my_list[0] = 1
except TypeError, e:
    print e
