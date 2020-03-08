import matplotlib.pyplot as plt
import os

def plotFiles(times, figno):
    plt.figure(figno)
    i = 1
    legends = []
    for file in times: 
        points = []
        d = open("../results/times/" + file, 'r')
        s = d.readline()
        for line in d:
            s = line
            s = s.split()
            map(float, s)
            points.append(s)
        x = [float(point[0]) for point in points]
        y = [float(point[1]) for point in points]
        plt.plot(x, y)
        legend = "M=" + str(i)
        legends.append(legend)
        i+=1
    plt.xlabel('N')
    plt.ylabel('Time')
    plt.legend(legends)

times = os.listdir("../results/times")

times.sort()
bfFiles = filter(lambda x: "bf" in x, times)
nonBfFiles = filter(lambda x: "bf" not in x, times)
plotFiles(bfFiles, 1)
plotFiles(nonBfFiles, 2)
plt.show()

