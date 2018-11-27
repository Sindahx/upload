#!/usr/bin/python2.7
# -*- coding: utf-8 -*-
import itchat,time
import sys,urllib2
import top.api
import pymysql.cursors

toUserName = 'Loveyou1234567890'
targetGroup = '省钱小白ty0005（禁广告）'
# targetGroup = '小白优惠群ty337 （禁广告）'
img_dir = "D:\\Downloads\\domain_images\\"
file_path = "d:/test.xlsx"
content = "【{0}】\n{1}\n原价：【{2}】\n领券：【{3}】包邮\n {4}\n----------\n长按复制这条信息，打开\n【手机淘宝】\n可领券下单{5}"

#database
db_host = '157.122.68.23'
db_port = 3306
db_user = 'root'
db_password = 'ycyl@3286'
db_table = 'products'


def init():
    reload(sys)
    sys.setdefaultencoding('utf-8')
    top.setDefaultAppInfo("24575668", "931df9b6522d22f3c3d7c66ae8998d6f")

    itchat.auto_login(hotReload=True)
    # itchat.auto_login()



def couponList():
    req = top.api.TbkDgItemCouponGetRequest()
    req.adzone_id = 316806976
    req.platform = 1
    req.cat = "16,18"
    req.page_size = 100
    req.q = "女装"
    req.page_no = 1
    try:
        resp = req.getResponse()
        # print(len(resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon']))
        # print(resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon'][0])
        return resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon']
    except Exception, e:
        print(e)


def getTaoKey(url,path,pro_name):
    req_ = top.api.TbkTpwdCreateRequest()
    req_.user_id = ""
    req_.text = pro_name
    req_.url = url
    req_.logo = path
    req_.ext = "{}"
    try:
        resp = req_.getResponse()
        return resp['tbk_tpwd_create_response']['data']['model']
    except Exception, e:
        print(e)

def down_image(url,file_name):
    headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0'}
    req = urllib2.Request(url=url, headers=headers)
    binary_data = urllib2.urlopen(req).read()
    temp_file = open(file_name, 'wb')
    temp_file.write(binary_data)
    temp_file.close()

def sendMessage(author,row,pic_path):
    # print(list[i]['shop_title'])
    # print(list[i]['title'])
    # print(list[i]['zk_final_price'])
    # print(list[i]['pict_url'])
    # print(list[i]['item_url'])
    # print(list[i]['coupon_click_url'])
    # print(list[i]['item_description'])
    # print(list[i]['coupon_info'])

    shangdian_name = row['shop_title']
    sp_class = row['item_description']
    product_des = row['title']
    price = row['zk_final_price']
    discounts = row['coupon_info']
    discounts_url = row['coupon_click_url']
    # pro_name = row['item_description']
    pic_url = row['pict_url']
    # print(discounts_url)
    tao_key = getTaoKey(discounts_url,pic_url,product_des)
    content_now =  content.format(shangdian_name,sp_class,price,discounts,product_des,tao_key)


    itchat.send_image(pic_path, author['UserName'])
    # itchat.send_image(pic_path, toUserName='filehelper')
    time.sleep(2)
    itchat.send(content_now, author['UserName'])
    # itchat.send(content_now, toUserName='filehelper')

# print(author)
# itchat.send(u'lalala ,我要测是消息啦!',author['UserName'])


# conetnt = "【%s】%s\n原价：【%s】\n领券后：【%s】包邮\n %s\n----------\n长按复制这条信息，打开【手机淘宝】，可领券下单￥%s￥"


# def fromExcel(author):
#     workbook = xlrd.open_workbook(file_path)
#     table = workbook.sheets()[0]
#
#     nrows = table.nrows
#
#     for i in range(nrows):
#         if (i > 0):
#             item = getItem(table)
#             pic_url = item['pict_url']
#             # print(table.row_values(i)[2])
#             # path_url = table.row_values(i)[2]
#             pic_name = pic_url.split('/')[-1]
#             down_image(pic_url, img_dir + pic_name)
#             sendMessage(author,item, img_dir + pic_name)
#             time.sleep(300)
#
#
# def getItem(table):
#     item = {}
#     item['shop_title'] = table[12]
#     item['title'] = table[1]
#     item['zk_final_price'] = table[6]
#     item['pict_url'] = table[2]
#     item['item_url'] = table[10]
#     item['coupon_click_url'] = table[21]
#     item['item_description'] = table[10]
#     item['coupon_info'] = table[10]
#     return item

def updateProduct(num_iid):
    print(type(num_iid))
    # 连接MySQL数据库
    connection = pymysql.connect(host=db_host, port=db_port, user=db_user, password=db_password, db=db_table,
                                 charset='utf8mb4', cursorclass=pymysql.cursors.DictCursor)
    # 通过cursor创建游标
    cursor = connection.cursor()
    # 执行数据查询
    sql = "update product_product_item set employ=1 where num_iid = '"+str(num_iid)+"'"
    result = cursor.execute(sql)
    # result = cursor.fetchall()
    connection.commit()
    connection.close()

    return result

def selectCount():
    # 连接MySQL数据库
    connection = pymysql.connect(host=db_host, port=db_port, user=db_user, password=db_password, db=db_table,
                                 charset='utf8mb4', cursorclass=pymysql.cursors.DictCursor)
    # 通过cursor创建游标
    cursor = connection.cursor()
    # 执行数据查询
    sql = "SELECT * FROM `product_product_item` WHERE `employ`='0'"
    result = cursor.execute(sql)
    # result = cursor.fetchall()
    connection.close()

    return result

def fromDbServer():
    # 连接MySQL数据库
    connection = pymysql.connect(host=db_host, port=db_port, user=db_user, password=db_password, db=db_table,
                                 charset='utf8mb4', cursorclass=pymysql.cursors.DictCursor)
    # 通过cursor创建游标
    cursor = connection.cursor()
    # 执行数据查询
    sql = "SELECT * FROM `product_product_item` WHERE `employ`='0' limit 0,10"
    cursor.execute(sql)
    result = cursor.fetchall()
    connection.close()

    return result
    # print(result)
    # for data in result:
    #     print(data)



def fromInterface(author):
    # coupList = couponList()
    coupList = fromDbServer()
    for i in range(len(coupList)):
        pic_url = coupList[i]['pict_url']
        print(pic_url)
        pic_name = pic_url.split('/')[-1]
        fixed = pic_name.split('.')[-1]
        updateProduct(coupList[i]['num_iid'])
        if fixed != "gif" and fixed != "GIF":
            down_image(pic_url, img_dir + pic_name)
            sendMessage(author, coupList[i], img_dir + pic_name)
            time.sleep(300)


def main():
    author = itchat.search_friends(name=toUserName)
    friendList = itchat.get_friends(update=True)
    print(friendList)

    target = itchat.search_chatrooms(name=targetGroup)
    # target = itchat.
    # fromExcel(author)
    # print(target)
    # while True:
    #     count = selectCount()
    #     if(count < 100):
    #         # itchat.send("", author['UserName'])
    #         itchat.send("商品信息少于100条，请及时添加", author['UserName'])
    #     if (count > 0):
    #         fromInterface(target)
    #     if(count == 0):
    #         itchat.send("商品信息发放完毕，请及时添加", author['UserName'])
    #         time.sleep(300)




if __name__ == "__main__":
    init()
    main()
    itchat.run()