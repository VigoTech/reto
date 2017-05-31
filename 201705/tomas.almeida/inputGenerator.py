#!/usr/bin/env python

import random
import sys

floor = int(sys.argv[1])
ceil  = int(sys.argv[2])
number = int(sys.argv[3])
print str(random.sample(range(floor, ceil),number))[1:-1].replace(',','')
