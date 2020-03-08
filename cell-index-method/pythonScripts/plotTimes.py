#!/bin/python3

import matplotlib.pyplot as plt
import os
import re
import sys

def plotFiles(dir, times, figno, title):
    plt.figure(figno)
    i = 1
    legends = []
    p = re.compile("(.*)(M=[0-9]+)(.*)(b?f?)(.*)")
    for file in times:
        points = []
        d = open(dir + "/" + file, 'r')
        s = d.readline()
        for line in d:
            s = line
            s = s.split()
            map(float, s)
            points.append(s)
        x = [float(point[0]) for point in points]
        y = [float(point[1]) for point in points]
        plt.plot(x, y)
        legend = p.search(file)
        if 'bf' in legend.group(3):
            legend = 'Brute Force'
        else:
            legend = legend.group(2)
        legends.append(legend)
        i+=1
    plt.xlabel('N')
    plt.ylabel('Time')
    plt.title(title)
    plt.legend(legends)

if len(sys.argv) == 2:

    times = os.listdir(sys.argv[1])

    times.sort()
    pbFiles = filter(lambda x: "pb" in x, times)
    nonPbFiles = filter(lambda x: "pb" not in x, times)
    plotFiles(sys.argv[1], pbFiles, 1, 'Periodic Boundary')
    plotFiles(sys.argv[1], nonPbFiles, 2, 'Non-Periodic Boundary')
    plt.show()

