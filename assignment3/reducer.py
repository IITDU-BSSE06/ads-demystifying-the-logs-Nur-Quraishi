#!/usr/bin/python

import sys

web_address = dict()

for line in sys.stdin:
	address = line.strip()
	web_address[address] = web_address.get(address, 0) + 1

fav = max(web_address, key=web_address.get)

print fav
