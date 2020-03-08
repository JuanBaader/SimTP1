import matplotlib.pyplot as plt
import matplotlib.axes as ax

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


drawDots("../data/st-6.dat", "../data/dyn-6.dat", "./results/neighborsN=100.0M=4.txt", 50)