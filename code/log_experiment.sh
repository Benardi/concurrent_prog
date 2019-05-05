#!/usr/bin/bash
for i in {1..10} ; do
    echo "program_name:$1"
    echo "time_execution:$(date +%Y-%m-%d-%H:%M:%S)"
    time $1 &>/dev/null 
done 2>&1 | tee --append timing.log
