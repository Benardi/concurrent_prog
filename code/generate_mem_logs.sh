#!/usr/bin/bash

out_dir=../output/goroutine_javathread/
valgrind_cmd="valgrind --time-unit=B --stacks=yes --tool=massif"

# Compile java files
javac CreateNThreads.java

# Generate java related logs
for((n = 25; n <= 400; n = n*2)); do
  $valgrind_cmd	--massif-out-file=${out_dir}massif_out_java_$n.txt java CreateNThreads $n
  $valgrind_cmd --massif-out-file=${out_dir}massif_out_golang_$n.txt go run ../go-code/goroutines.go $n
done
