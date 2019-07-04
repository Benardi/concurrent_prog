#!/usr/bin/bash

SCRIPTPATH="$( cd "$(dirname "$0")" ; pwd -P )"
header="RSS,elapsed,cpu.sys,.user,minor_pagefault,major_pagefault,inv_cxt_sw,vol_cxt_sw,version,execution" 

echo ${header} | tee ${SCRIPTPATH}/../../output/list1/ex1/perf_result.csv
for i in {1..50} ; do
	/usr/bin/time -f "%M,%E,%S,%U,%F,%R,%c,%w,unprotected,$i" ${SCRIPTPATH}/../list1/c-code/ex1/unprotected_ex1 2>&1 >/dev/null 
	/usr/bin/time -f "%M,%E,%S,%U,%F,%R,%c,%w,protected,$i" ${SCRIPTPATH}/../list1/c-code/ex1/protected_ex1 2>&1 >/dev/null

done 2>&1 | tee --append ${SCRIPTPATH}/../../output/list1/ex1/perf_result.csv
