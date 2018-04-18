from PIL import Image, ImageDraw

def printUvs(uvs, triangles, fileName, size):
    print('Writing ' + str(len(uvs)) + ' to ' + str(fileName) +' of size '+ str(size) + '.')
    image = Image.new("RGBA",size,(256,256,256,256));
    draw = ImageDraw.Draw(image)
    for tri in triangles:
        draw.polygon([(int(uv.as_list()[0]*size[0]), int(uv.as_list()[1]*size[1])) for uv in [uvs[i] for i in [tri.v_1, tri.v_2, tri.v_3]]],(0,0,0,50),(0,0,0,50))
    '''for uv in uvs:
        uvl = uv.as_list()
        image.putpixel((int(uvl[0]*size[0]), int(uvl[1]*size[1])), (0,0,0,256))
    '''
    image.save(fileName, "PNG")
    return;

def printUvsPixel(uvs, fileName, size):
    print('Writing ' + str(len(uvs)) + ' to ' + str(fileName) +' of size '+ str(size) + '.')
    image = Image.new("RGBA",size,(256,256,256,256));
    draw = ImageDraw.Draw(image)
    for uv in uvs:
        uvl = uv.as_list()
        image.putpixel((int(uvl[0]*size[0]), int(uvl[1]*size[1])), (0,0,0,256))
    
    image.save(fileName, "PNG")
    return;

#printUvs([(16,16), (10,10), (5,5)], 'C:\\Users\\thoma\\Downloads\\test.png')