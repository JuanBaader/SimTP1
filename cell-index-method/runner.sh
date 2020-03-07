#!/bin/bash

declare -a numbers=(0 10 20 30 40 50 100 200 300 400 500 600 700 800 900 1000)


for pb in "-pb" "" 
do
    # if [ $pb -eq 1 ]; then
    #     periodic="-pb"
    # else
    #     periodic=""
    # fi
    for M in {1..5} 
    do
        mkdir -p results/times
        echo $'#'Parts$'\t'M$'\t'Time >> results/times/resultsM=$M$' '$pb.txt
        for i in {1..15}
        do
            # echo "java -jar target/cell-index-method-version1.0.jar -df ../data/dyn-$i.dat -sf ../data/st-$i.dat -rc 1 $pb -M $M"
            time=$(java -jar target/cell-index-method-version1.0.jar -M $M -df ../data/dyn-$i.dat -sf ../data/st-$i.dat -rc 1 $pb)
            echo ${numbers[$i]}$'\t'$M$'\t'$time >> results/times/resultsM=$M$' '$pb.txt
        done
    done
done