#!/usr/bin/python

import sys

counter = 0

for line in sys.stdin:
    address = line.strip()
    if address == "/assets/js/the-associates.js":
	counter = counter + 1

print counter
