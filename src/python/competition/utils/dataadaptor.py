__author__="Sergey Karakovskiy, sergey at idsia fullstop ch"
__date__ ="$Apr 30, 2009 1:53:54 PM$"

import numpy
    
def extractObservation(data):
    """
     parse the array of strings and return array 22 by 22 of doubles
    """
    obsLength = 487
    levelScene = numpy.empty(shape = (22,22), dtype = numpy.int)
    data = data.split(' ')
    if (data[0] == 'FIT'):
        status = int(data[1])
        fitness = float(data[2])
#        print "S: %s, F: %s " % (data[1], data[2])
        return status, fitness
    elif(data[0] == 'O'):
        mayMarioJump = (data[1] == 'true')
        isMarioOnGround = (data[2] == 'true')
        assert len(data) == obsLength, "Error in data size given% %d! Required: %d" % (len(data), obsLength)
        k = 0
        for i in range(22):
            for j in range(22):
                levelScene[i,j] = int(data[k + 3])
                k += 1
        return (mayMarioJump, isMarioOnGround, levelScene)
    else:
        raise "Wrong format or corrupted observation..."


if __name__ != "__main__":
    print "Module DataAdaptor loaded.";