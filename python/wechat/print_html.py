from Tkinter import Image

import selenium as webdriver


url = 'C:\Users\Administrator\Desktop\yemian\index.html'
driver = webdriver.PhantomJS()
driver.set_page_load_timeout(300)
driver.set_window_size(1280,800)
driver.get(url)
imgelement = driver.find_element_by_id('XXXX')
location = imgelement.location
size = imgelement.size
savepath = r'XXXX.png'
driver.save_screenshot(savepath)
im = Image.open(savepath)
left = location['x']
top = location['y']
right = left + size['width']
bottom = location['y'] + size['height']
im = im.crop((left,top,right,bottom))
im.save(savepath)