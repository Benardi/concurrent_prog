#!/usr/bin/bash

header="RSS,elapsed,cpu.sys,.user,minor_pagefault,major_pagefault,inv_cxt_sw,vol_cxt_sw,version,execution" 

echo ${header} | tee ../output/perf_result.csv
for i in {1..50} ; do
	/usr/bin/time -f "%M,%E,%S,%U,%F,%R,%c,%w,unprotected,$i" ../c-code/unprotected_ex1 2>&1 >/dev/null 
	/usr/bin/time -f "%M,%E,%S,%U,%F,%R,%c,%w,protected,$i" ../c-code/protected_ex1 2>&1 >/dev/null

done 2>&1 | tee --append ../output/perf_result.csv
