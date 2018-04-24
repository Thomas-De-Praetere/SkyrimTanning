from Lib.pyffi.formats.nif import NifFormat

import sys, argparse

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
            
def getMesh( shape ):
    data = findNodeInTree(shape, NifFormat.NiTriShapeData)
    
    iNodes = data.vertices
    iUvs =  data.uv_sets[0]
    iTriangles = data.triangles
    
    return (iNodes, iTriangles, iUvs);
            
def load( file, skipBody ): 
    stream = open( file, 'rb')
    data = NifFormat.Data()
    data.read(stream)
    shapes = getNiTriShapes(data)
    
    completeFile = []
    
    for shape in shapes:
        if not (skipBody and isBodyShape(shape)):
            mesh = getMesh(shape)
    
            name = shape.name
            nodes = mesh[0]
            triangles = mesh[1]
            uvs =  mesh[2]
            
            completeFile.append((name, nodes, triangles, uvs))
            
    stream.close()
    return completeFile;

def format( niShapeData ):
    form = niShapeData[0].decode('ascii') + '::'
    nodes = str([n.as_list() for n in niShapeData[1]]) + '::'
    triangles = str([[tri.v_1, tri.v_2, tri.v_3] for tri in niShapeData[2]]) + '::'
    uv = str([[uv.as_list() for uv in niShapeData[3]]])
    return form + nodes + triangles + uv;

fileName = None
excludeBase = False

parser = argparse.ArgumentParser()
parser.add_argument("-i", "--iFile", help="Input File")
parser.add_argument("-e", "--exBase", help="exclude the base", action="store_true")
args = parser.parse_args()

if args.iFile:
    fileName = args.iFile
else:
    fileName = None

excludeBase = args.exBase

completeFile = []

if(fileName != None):
    completeFile = load(fileName, excludeBase)

first = True
out = ''
for data in completeFile:
    if not first:
        out += '::!::'
    first = False
    out += format(data)

print(out)
