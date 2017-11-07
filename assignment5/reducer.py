#!/usr/bin/python

import sys

web_address = dict()

for line in sys.stdin:
	path = line.strip()
	web_address[path] = web_address.get(path, 0) + 1

print len(web_address)
