#!/bin/bash
mvn package
sudo ./runner.sh
python3 pythonScripts/plotTimes.py ./results/times
python3 pythonScripts/drawDots.py ../data/st-13.dat ../data/dyn-13.dat ./results/neighborsN\=800.0M\=13pb.txt 713