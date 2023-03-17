# import cv2 as cv
import numpy as np
# from PIL import Image
# import base64
# import io

# def main(data):
    
#     decode_data = base64.decode(data)
#     np_data = np.fromstring(decode_data, np.uint8)
#     img = cv.imdecode(np_data, cv.IMREAD_COLOR)

#     blur = cv.GaussianBlur(img, (7,7), cv.BORDER_DEFAULT)

#     pil_im = Image.fromarray(blur)

#     buff = io.BytesIO()
#     pil_im.save(buff, format="PNG")
#     img_str = base64.b64encode(buff.getvalue())

    # return "" + str(img_str, 'utf-8 ')

def main():
    data =  np.array([1,2,3,4,5,6,7,8,9,10])
    __str = ''
    for i in range(10):
        __str += str(data[i]) + ','
    return __str