from numpy import random

def generateFiles(index, number, length):
    radius = .25
    
    staticFile = open('./data/st-' + str(index) + '.dat', 'w')
    dynamicFile = open('./data/dyn-' + str(index) + '.dat', 'w')

    staticFile.write('{}\n'.format(number))
    staticFile.write('{}\n'.format(length))
    dynamicFile.write('0\n')

    prop = 1
    for x in range(0,number):
        staticFile.write('{} {}\n'.format(radius, prop))
        dynamicFile.write('{} {}\n'.format(random.uniform(0, length), random.uniform(0,length)))

numbers = range(10, 51, 10)
numbers.extend(range(100, 1000, 100))
numbers.extend([10000, 100000])
i = 0
for x in numbers:
    i += 1
    generateFiles(i, numbers[i-1], 20)