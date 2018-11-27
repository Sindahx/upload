#!/usr/bin/python2.7
# -*- coding: utf-8 -*-

import xlrd,sys,json,urllib2,os



def down_image(url,file_name):
    headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0'}
    req = urllib2.Request(url=url, headers=headers)
    binary_data = urllib2.urlopen(req).read()
    temp_file = open(file_name, 'wb')
    temp_file.write(binary_data)
    temp_file.close()

def getRows(file_path):
    workbook = xlrd.open_workbook(file_path)
    sheet_names = workbook.sheet_names()
    table = workbook.sheets()[0]
    rows = []
    print(sheet_names[0])
    sheet2 = workbook.sheet_by_name(sheet_names[0])
    # rows = sheet2.cell()
    rows = sheet2.row_values(2)  # 获取第四行内容
    return rows

if __name__ == "__main__":

    img_dir = "D:\\Downloads\\domain_images\\"
    file_path = "d:/test.xlsx"
    # if not os.path.isdir(img_dir):
    #     os.mkdir(img_dir)
    # os.chdir(img_dir)
    # print(os.getcwd())

    workbook = xlrd.open_workbook(file_path)
    table = workbook.sheets()[0]

    nrows = table.nrows
    for i in range(nrows):
        if(i>0):
            print(table.row_values(i)[2])
            path_url = table.row_values(i)[2]
            pic_name = path_url.split('/')[-1]
            down_image(path_url,img_dir+pic_name)
    # print(rows)
