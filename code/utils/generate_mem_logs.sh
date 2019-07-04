#!/usr/bin/bash

SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
valgrind_cmd="valgrind --time-unit=B --stacks=yes --tool=massif"

# Compile java files
javac ${SCRIPTPATH}/../list2/java-code/ex3/CreateNThreads.java

# Generate java related logs
for((n = 25; n <= 400; n = n*2)); do
  $valgrind_cmd	--massif-out-file=${SCRIPTPATH}/../../output/list2/ex3/goroutine_javathread/massif_out_java_$n.txt java -classpath ${SCRIPTPATH}/../list2/java-code/ex3/ CreateNThreads $n
  $valgrind_cmd --massif-out-file=${SCRIPTPATH}/../../output/list2/ex3/goroutine_javathread/massif_out_golang_$n.txt go run ${SCRIPTPATH}/../list2/go-code/ex3/goroutines.go $n
done
