import argparse
import sys

header = "impl,nthreads,snapshot,time,total,mem_heap_B,mem_heap_extra_B,mem_stacks_B"
preamble = None
out_l = ""

def write_header(destination_file_path):
    with open(destination_file_path,"w") as f:
       f.write(header + '\n')



def write_content(source_file_path, destination_file_path):
    with open(source_file_path,"r") as logs:
      with open(destination_file_path,"a") as out_f:
        i = 0
        total_mem = 0
        for line in logs:
            if "snapshot=" in line:
                out_l = preamble + line[9:][:-1] #extract value and remove \n

            if "time=" in line :  
                out_l += ',' + line.split('=')[-1][:-1] + ',{}' # to inject total memory value 
                
            if ("mem_heap_B=" in line or  
               "mem_heap_extra_B=" in line):
                value = line.split('=')[-1][:-1]
                out_l += ',' + value
                total_mem += float(value)

            if "mem_stacks_B" in line:
                value = line.split('=')[-1][:-1]
                out_l += ',' + value
                total_mem += float(value)
                out_l = out_l.format(total_mem)
                total_mem = 0
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
parser.add_argument("--dest", type=str,
                    help="path of resulting csv file")
args = parser.parse_args()

impl = args.impl
nthreads = args.nthreads
source_f = args.source
dest_f = args.dest

if args.no_header is False:
    write_header(dest_f)

preamble = "{},{},".format(impl,nthreads)

write_content(source_f,dest_f)
