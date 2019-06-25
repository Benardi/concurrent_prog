import argparse
import sys

header = "impl,nthreads,snapshot,time,mem_heap_B,mem_heap_extra_B,mem_stacks_B"
preamble = None
out_l = ""

def write_header():
    with open("q3-l2-results.csv","w") as f:
       f.write(header + '\n')



def write_content(source_file_path):
    with open(source_file_path,"r") as logs:
      with open("q3-l2-results.csv","a") as out_f:
        i = 0
        for line in logs:
            if "snapshot=" in line:
                out_l = preamble + line[9:][:-1] #extract value and remove \n

            if ("time=" in line or  
               "mem_heap_B=" in line or  
               "mem_heap_extra_B=" in line):
                out_l += ',' + line.split('=')[-1][:-1] 
                
            if "mem_stacks_B" in line:
                out_l += ',' + line.split('=')[-1][:-1]
                out_f.write(out_l + '\n')

parser = argparse.ArgumentParser()
parser.add_argument("--no-header",action='store_true',
                    help="Don't overwrite output file with header")
parser.add_argument("--nthreads", type=int,
                    help="number of threads to be mentioned in csv")
parser.add_argument("--impl", type=str,
                    help="name of impl to be mentioned in csv")
parser.add_argument("--source", type=str,
                    help="path of memory profiling source file")
args = parser.parse_args()

if args.no_header is False:
    write_header()

impl = args.impl
nthreads = args.nthreads
source_f = args.source

preamble = "{},{},".format(impl,nthreads)

write_content(source_f)
