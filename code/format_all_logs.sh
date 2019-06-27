#!/usr/bin/bash

i=25
logs_path="../output/goroutine_javathread/"
dest="../output/q3-l2-results.csv"

python3 ./format_mem_profiling.py --nthreads=$i --impl=golang\
	--source=${logs_path}massif_out_golang_$i.txt --dest=$dest

python3 ./format_mem_profiling.py --no-header --nthreads=$i --impl=java\
	--source=${logs_path}massif_out_java_$i.txt --dest=$dest

for((n = 50; n <= 400; n = n*2)); do

  python3 ./format_mem_profiling.py --no-header --nthreads=$n --impl=golang\
	  --source=${logs_path}massif_out_golang_$n.txt --dest=$dest

  python3 ./format_mem_profiling.py --no-header --nthreads=$n --impl=java\
	  --source=${logs_path}massif_out_java_$n.txt --dest=$dest

done

