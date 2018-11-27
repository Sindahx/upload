#!/usr/bin/python2.7
# -*- coding: utf-8 -*-

import top.api,time
import pymysql.cursors

top.setDefaultAppInfo("24575668", "931df9b6522d22f3c3d7c66ae8998d6f")


def favoritesList():
    req = top.api.TbkUatmFavoritesGetRequest()

    req.page_no = 1
    req.page_size = 20
    req.fields = "favorites_title,favorites_id,type"
    req.type = 1
    try:
        resp = req.getResponse()
        # print(resp)
        return resp
    except Exception, e:
        print(e)

def favoritesItem(adzone_id,favorites_id):
    req = top.api.TbkUatmFavoritesItemGetRequest()

    req.platform = 1
    req.page_size = 20
    req.adzone_id = adzone_id
    req.unid = "3456"
    req.favorites_id = favorites_id
    req.page_no = 2
    req.fields = "num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type"
    try:
        resp = req.getResponse()
        print(resp)
    except Exception, e:
        print(e)

def getCoupon():
    req = top.api.TbkCouponGetRequest()

    req.me = "nfr%2BYTo2k1PX18gaNN%2BIPkIG2PadNYbBnwEsv6mRavWieOoOE3L9OdmbDSSyHbGxBAXjHpLKvZbL1320ML%2BCF5FRtW7N7yJ056Lgym4X01A%3D"
    req.item_id = 123
    req.activity_id = "sdfwe3eefsdf"
    try:
        resp = req.getResponse()
        print(resp)
    except Exception, e:
        print(e)

def couponList():
    req = top.api.TbkDgItemCouponGetRequest()
    req.adzone_id = 316806976
    req.platform = 1
    req.cat = ""
    req.page_size = 5
    req.q = ""
    req.page_no = 1
    try:
        resp = req.getResponse()
        print(len(resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon']))
        # print(resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon'][0])
        return resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon']
    except Exception, e:
        print(e)

def fromDbServer():
    # 连接MySQL数据库
    connection = pymysql.connect(host='157.122.68.23', port=3306, user='root', password='ycyl@3286', db='products',charset='utf8mb4', cursorclass=pymysql.cursors.DictCursor)
    # 通过cursor创建游标
    cursor = connection.cursor()
    # 执行数据查询
    sql = "SELECT * FROM `product_product_item` WHERE `employ`='0'"
    result = cursor.execute(sql)
    # result = cursor.fetchall()

    print(result)
    # for data in result:
    #     print(data)

    connection.close()


def main():
    fromDbServer()

    # favaoList = favoritesList()
    # print(favaoList)
    # favoritesItem(316806976,9486854)
    # list = couponList()

    # for i in range(len(list)):
    #     print(list[i]['shop_title'])
    #     print(list[i]['title'])
    #     print(list[i]['zk_final_price'])
    #     print(list[i]['pict_url'])
    #     print(list[i]['item_url'])
    #     print(list[i]['coupon_click_url'])
    #     print(list[i]['item_description'])
    #     print(list[i]['coupon_info'])
    #     print('-------------------------------------')
    #     time.sleep(5)


if __name__ == "__main__":
    main()