# -*- coding: utf-8 -*-
'''
Created on 2012-7-3

@author: lihao
'''
import top.api


'''
这边可以设置一个默认的appkey和secret，当然也可以不设置
注意：默认的只需要设置一次就可以了

'''
top.setDefaultAppInfo("24575668", "931df9b6522d22f3c3d7c66ae8998d6f")

'''
使用自定义的域名和端口（测试沙箱环境使用）
a = top.api.UserGetRequest("gw.api.tbsandbox.com",80)

使用自定义的域名（测试沙箱环境使用）
a = top.api.UserGetRequest("gw.api.tbsandbox.com")

使用默认的配置（调用线上环境）
a = top.api.UserGetRequest()

'''
req = top.api.TbkDgItemCouponGetRequest()

req.adzone_id = 316806976
req.platform = 1
req.cat = "16,18"
req.page_size = 1
req.q = "女装"
req.page_no = 1
try:
    resp = req.getResponse()
    # print(resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon'][0]['small_images']['string'])
    item_string = resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon'][0]['small_images']['string']
    print(resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon'][0]['pict_url'])
    for i in range(len(item_string)):
        print(item_string[i])
except Exception, e:
    print(e)