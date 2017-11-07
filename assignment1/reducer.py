#!/usr/bin/python

import sys

counter = 0

for line in sys.stdin:
    address = line.strip()
    if address == "10.99.99.186":
	counter = counter + 1

print counter
