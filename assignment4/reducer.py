#!/usr/bin/python

import sys

web_address = dict()

for line in sys.stdin:
	path = line.strip()
	web_address[path] = web_address.get(path, 0) + 1

fav = max(web_address, key=web_address.get)

print web_address.get(fav, 0)
