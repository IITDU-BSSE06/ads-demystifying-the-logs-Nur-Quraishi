#!/usr/bin/python

import sys

for line in sys.stdin:
    myData = line.strip().split(" ")
    if len(myData) == 10:
        print myData[0]
