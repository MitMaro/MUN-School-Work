

size_1 = 3
items_1 = 0
increase_count_1 = 0
size_2 = 3
items_2 = 0
increase_count_2 = 0

def push1(item):
	global items_1, size_1, increase_count_1
	if items_1 + 1 > size_1:
		increase_count_1 += 1
		size_1 += 3
		print "Overflow, increasing size. Now:", size_1

	#print "Pushing item #", item, "  Items:", items_1
	items_1 += 1


def pop1():
	global items_1
	#print "Poping item #", items_1
	items_1 -= 1


def push2(item):
	global items_2, size_2, increase_count_2
	if items_2 + 1 > size_2:
		increase_count_2 += 1
		size_2 *= 3
		print "Overflow, increasing size. Now:", size_2

	#print "Pushing item #", item, "  Items:", items_2
	items_2 += 1


def pop2():
	global items_2
	#print "Poping item #", items_2
	items_2 -= 1


print "\nINCREASE CONSTANT"
for i in range(1, 11):
	push1(i)
for i in range(5):
	pop1()
for i in range(11, 101):
	push1(i)
print "Increase Count:", increase_count_1
print "    Final Size:", size_1
print ""

print "INCREASE BY FACTOR 3"
for i in range(1, 11):
	push2(i)
for i in range(5):
	pop2()
for i in range(11, 101):
	push2(i)
print "Increase Count:", increase_count_2
print "    Final Size:", size_2
print ""