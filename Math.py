import math

def dist(node1, node2):
    val = 0
    for i in range(3):
        x1 = node1.as_list()[i]
        x2 = node2.as_list()[i]
        val += ((x1-x2)*(x1-x2))
    return math.sqrt(val)

def isSame( co1, co2 ):
    for i in range(0, len(co1)):
        if(co1[i]!=co2[i]):
            return False
    return True

def hasSameCoo( nodes ):
    a = 0
    iIs = []
    for i in range(0, len(nodes)):
        for j in range(i+1, len(nodes)):
            if (isSame(nodes[i].as_list(),nodes[j].as_list())):
                a+=1;
                iIs.append((i,j))
    return (a,iIs)