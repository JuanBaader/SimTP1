#!/bin/python3

import matplotlib.pyplot as plt

def drawDots(staticFile, dynamicFile, results, selectedParticle):
    sf = open(staticFile, 'r')
    df = open(dynamicFile, 'r')
    rf = open(results, 'r')
    N = int(sf.readline())
    # reads L
    sf.readline()
    # reads t0
    df.readline()
    # reads ellapsed time
    rf.readline()
    # find selectedParticles' neighbours
    done = False
    line = 'sth'
    neighbours = []
    while not done and line:
        line = rf.readline()
        if line == "Particle {}:\n".format(selectedParticle):
            line = rf.readline()
            while line and "P" not in line:
                neighbours.append(int(line))
                line = rf.readline()
            done = True
    for i in range(N):
        staticLine = sf.readline().rstrip("\n")
        dynamicLine = df.readline().rstrip("\n")
        r = float(staticLine.split()[0])
        dynamicLine = dynamicLine.split()
        x, y = float(dynamicLine[0]), float(dynamicLine[1])
        if i == selectedParticle:
            color = 'blue'
        elif i in neighbours:
            color = 'red'
        else:
            color = 'green'
        plt.scatter(x, y, color=color)

    plt.show()


drawDots("../../data/st-15.dat", "../../data/dyn-15.dat", "../results/neighborsN=1000.0M=13.txt", 51)