
value = 42

another = 'forty-two'

def func(x, y):
    return x * y

class SpamAndEggs(object):
    def __init__(self, eggs):
        self.eggs = eggs

    def hasSpam(self):
        return True

    def getEggs(self):
        return self.eggs

    def __repr__(self):
        return 'Spam and %s eggs.' % self.eggs


spam_and_eggs = SpamAndEggs(2)
