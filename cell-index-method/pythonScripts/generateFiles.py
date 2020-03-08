#!/bin/python3

from pathlib import Path
from numpy import random
import sys

def generateFiles(index, number, length, destinationFolder):
    radius = .25
    
    Path(destinationFolder).mkdir(parents=True, exist_ok=True)
    staticFile = open(destinationFolder + '/st-' + str(index) + '.dat', 'w')
    dynamicFile = open(destinationFolder + '/dyn-' + str(index) + '.dat', 'w')

    staticFile.write('{}\n'.format(number))
    staticFile.write('{}\n'.format(length))
    dynamicFile.write('0\n')

    prop = 1
    for x in range(0,number):
        staticFile.write('{} {}\n'.format(radius, prop))
        dynamicFile.write('{} {}\n'.format(random.uniform(0, length), random.uniform(0,length)))

if len(sys.argv) == 2:
    numbers = list(range(10, 51, 10))
    numbers.extend(range(100, 1100, 100))
    i = 0
    for x in numbers:
        i += 1
        generateFiles(i, numbers[i-1], 20, sys.argv[1])
else: 
    print('Usage: generateFiles [destinationFolder]')