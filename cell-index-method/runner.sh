#!/bin/bash

declare -a numbers=(0 10 20 30 40 50 100 200 300 400 500 600 700 800 900 1000)

rm -rf $(find -iregex .*.txt)
rm -rf $(find -iregex .*.xyz)

# for pb in "-pb" "" 
# do
    for bf in "-bf" ""
    do
        for M in {1..15} 
        do
            mkdir -p results/times
            echo $'#'Parts$'\t'Time >> results/times/resultsM=$M$pb$bf.txt
            for i in {1..15}
            do
                echo "java -jar target/cell-index-method-version1.0.jar -M $M -df ../data/dyn-$i.dat -sf ../data/st-$i.dat -rc 1 $pb $bf"
                time=$(java -jar target/cell-index-method-version1.0.jar -M $M -df ../data/dyn-$i.dat -sf ../data/st-$i.dat -rc 1 $pb $bf)
                echo ${numbers[$i]}$'\t'$time >> results/times/resultsM=$M$pb$bf.txt
            done
        done
    done
# done