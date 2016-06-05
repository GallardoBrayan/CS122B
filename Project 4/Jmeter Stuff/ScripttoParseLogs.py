#!/bin/bash
from sys import argv
# store the command parameters in variable
script = argv[0]
# open a file reader

# read the file line by line
Main_One_thread = [0,0,0]
Main_ten_thread= [0,0,0]
Main_ten_threads_https= [0,0,0]
Main_10_threads_NOPS = [0,0,0]
Main_10_threads_NOPOOL = [0,0,0]
balance_One_thread = [0,0,0]
balance_ten_thread= [0,0,0]
balance_ten_threads_https= [0,0,0]
balance_10_threads_NOPS = [0,0,0]
balance_10_threads_NOPOOL = [0,0,0]

for x in argv[1:]:
    logFile = open(x, 'r')
    for line in logFile:
        # Maybe you can decide to somehow 'mark' those log records that you care about?
        if 'Main_One_thread' in line:
            # Clean the string, remove the mark, and tokenize it
            times = (line.strip().split(':'))[1]
            times = (times.strip().split(','))
            Main_One_thread[0] += int(times[0])
            Main_One_thread[1] += int(times[1])
            Main_One_thread[2] += 1
        if 'Main_10_threads ' in line:
            # Clean the string, remove the mark, and tokenize it
            times = (line.strip().split(':'))[1]
            times = (times.strip().split(','))
            Main_ten_thread[0] += int(times[0])
            Main_ten_thread[1] += int(times[1])
            Main_ten_thread[2] += 1
        if 'Main_10_threads_https ' in line:
            # Clean the string, remove the mark, and tokenize it
            times = (line.strip().split(':'))[1]
            times = (times.strip().split(','))
            Main_ten_threads_https[0] += int(times[0])
            Main_ten_threads_https[1] += int(times[1])
            Main_ten_threads_https[2] += 1
        if 'Main_10_threads_NOPS ' in line:
            # Clean the string, remove the mark, and tokenize it
            times = (line.strip().split(':'))[1]
            times = (times.strip().split(','))
            Main_10_threads_NOPS[0] += int(times[0])
            Main_10_threads_NOPS[1] += int(times[1])
            Main_10_threads_NOPS[2] += 1
        if 'Main_10_threads_NOPOOL ' in line:
            # Clean the string, remove the mark, and tokenize it
            times = (line.strip().split(':'))[1]
            times = (times.strip().split(','))
            Main_10_threads_NOPOOL[0] += int(times[0])
            Main_10_threads_NOPOOL[1] += int(times[1])
            Main_10_threads_NOPOOL[2] += 1
        if 'balance_One_thread' in line:
            # Clean the string, remove the mark, and tokenize it
            times = (line.strip().split(':'))[1]
            times = (times.strip().split(','))
            balance_One_thread[0] += int(times[0])
            balance_One_thread[1] += int(times[1])
            balance_One_thread[2] += 1
        if 'balance_10_threads ' in line:
            # Clean the string, remove the mark, and tokenize it
            times = (line.strip().split(':'))[1]
            times = (times.strip().split(','))
            balance_ten_thread[0] += int(times[0])
            balance_ten_thread[1] += int(times[1])
            balance_ten_thread[2] += 1
        if 'balance_10_threads_https ' in line:
            # Clean the string, remove the mark, and tokenize it
            times = (line.strip().split(':'))[1]
            times = (times.strip().split(','))
            balance_ten_threads_https[0] += int(times[0])
            balance_ten_threads_https[1] += int(times[1])
            balance_ten_threads_https[2] += 1
        if 'balance_10_threads_NOPS ' in line:
            # Clean the string, remove the mark, and tokenize it
            times = (line.strip().split(':'))[1]
            times = (times.strip().split(','))
            balance_10_threads_NOPS[0] += int(times[0])
            balance_10_threads_NOPS[1] += int(times[1])
            balance_10_threads_NOPS[2] += 1
        if 'balance_10_threads_NOPOOL ' in line:
            # Clean the string, remove the mark, and tokenize it
            times = (line.strip().split(':'))[1]
            times = (times.strip().split(','))
            balance_10_threads_NOPOOL[0] += int(times[0])
            balance_10_threads_NOPOOL[1] += int(times[1])
            balance_10_threads_NOPOOL[2] += 1
print("Main_One_thread:\n\t Ave TJ: ", Main_One_thread[0]/Main_One_thread[2],"\n\t Ave TJ: ",
        Main_One_thread[1]/Main_One_thread[2])
