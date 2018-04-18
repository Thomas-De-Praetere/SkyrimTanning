from pyffi.formats.nif import NifFormat
import Math
import Printer

nodes = [];
uvs = [];
normals = [];
triangles = [];

def findNodeInTree(treeAble, clazz):
    for block in treeAble.tree():
        if isinstance(block, clazz):
            return block
    return None;

def findNodesInTree(treeAble, clazz):
    list = []
    for block in treeAble.tree():
        if isinstance(block, clazz):
            list.append(block)
    return list;

def getNiTriShapes( data ):
    list = []
    for root in data.roots:
        list.extend(findNodesInTree(root, NifFormat.NiTriShape))
    return list;        

def isBodyShape( node ):
    node = findNodeInTree(node, NifFormat.BSShaderTextureSet)
    if(node is not None):
        texture = node.textures[0].lower()
        if b'femalebody' in texture:
            return True;
    else:
        return False;       
            
def getNodeUvNormalsAndTriangles( shape ):
    data = findNodeInTree(shape, NifFormat.NiTriShapeData)
    
    iNodes = data.vertices
    iUvs =  data.uv_sets[0]
    iNormals = data.normals
    iTriangles = data.triangles
    
    return (iNodes, iUvs, iNormals, iTriangles);
            
def loadBody():
    global nodes
    global uvs
    global normals
    global triangles
    
    stream = open('C:\\Users\\thoma\\Downloads\\femalebody_1.nif', 'rb')
    data = NifFormat.Data()
    data.read(stream)
    shape = getNiTriShapes(data)[0]
    if not isBodyShape(shape): raise ArithmeticError("Not a Body")
    
    data = getNodeUvNormalsAndTriangles(shape)
    
    nodes = data[0]
    uvs =  data[1]
    normals = data[2]
    triangles = data[3]
    
    amountToMatch = Math.hasSameCoo(nodes)
    print('Equal coordinates: ' + str(amountToMatch[0]))
    toPrint = []
    for t in amountToMatch[1]:
        print(str(uvs[t[0]]) + "::" + str(uvs[t[1]]))
        toPrint.extend([uvs[t[0]],uvs[t[1]]])
    
    Printer.printUvsPixel(toPrint,'C:\\Users\\thoma\\Downloads\\testDouble.png',(2048,2048))
    
    print(len(nodes))
    print(len(uvs))
    print(len(normals))
    print(len(triangles))
    
    stream.close()
    return;

def getClosestNode( node ):
    closeI = -1
    closest = None
    dist = float("inf")
    for i, other in enumerate(nodes):
        if node != other:
            oDist = Math.dist(node, other)
            if(oDist < dist):
                closeI = i
                closest = other
                dist = oDist
    return (closeI, closest);

def getUvs(iNodeList):
    return [uvs[iNode[0]] for iNode in iNodeList]

loadBody();

Printer.printUvs(uvs, triangles,'C:\\Users\\thoma\\Downloads\\test.png',(2048,2048))

stream = open('C:\\Users\\thoma\\Downloads\\test2.nif', 'rb')

data = NifFormat.Data()

data.read(stream)
shapes = getNiTriShapes(data)
shapeNameToUv = dict()
for shape in shapes:
    name = shape.name.decode("ascii")
    print("Working on: " + name)
    if(not isBodyShape(shape)):
        nunt = getNodeUvNormalsAndTriangles(shape)
        nodes =  nunt[0]
        others = []
        for node in nodes:
            others.append(getClosestNode(node))

        shapeNameToUv[name] = getUvs(others)
        Printer.printUvs(shapeNameToUv[name], nunt[3],'C:\\Users\\thoma\\Downloads\\' + name + '.png',(2048,2048))
        Printer.printUvs(nunt[1], nunt[3],'C:\\Users\\thoma\\Downloads\\' + name + '_orig.png',(2048,2048))
        
stream.close()
