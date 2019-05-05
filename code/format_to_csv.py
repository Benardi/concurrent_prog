n = 1
header = "program,date,real,user,sys"
out_line = ""
SEP = ","

with open('timing.log') as f:
    with open('results.csv','w') as f_out:
        f_out.write(header + "\n")
        for line in f:
            if n != 0 and n != 3:
                if n == 1 :
                    out_line = out_line + line.split(":")[1][2:].rstrip()
                if n == 2:
                    out_line = out_line + SEP + line.split(":")[1].rstrip()
                elif n in range(4,6): 
                    out_line = out_line + SEP + line[-9:].rstrip()
                elif n == 6: 
                    out_line = out_line + SEP + line[-9:].rstrip()
                    f_out.write(out_line + "\n")
                    out_line = ''
            n = (n + 1) % 7

