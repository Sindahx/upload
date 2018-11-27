# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from django.shortcuts import render
from django.http import HttpResponse
import top.api
import json
from models import Product_item
# Create your views here.
import sys
reload(sys)
sys.setdefaultencoding('utf8')




def index(request):
    return render(request,"product.html")

def pro_list(request):

    page_no = request.GET.get('page')
    productName = request.GET.get('productName')
    productCat = request.GET.get('productCat')

    top.setDefaultAppInfo("24575668", "931df9b6522d22f3c3d7c66ae8998d6f")
    req = top.api.TbkDgItemCouponGetRequest()

    req.adzone_id = 316806976
    req.platform = 1
    req.cat = productCat
    req.page_size = 10
    req.q = productName
    req.page_no = page_no
    try:
        resp = req.getResponse()
        # print(resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon'][0]['small_images']['string'])
        item_string = resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon'][0]['small_images']['string']
        print(resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon'][0]['pict_url'])
        print(resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon'][0])
        proMap = resp['tbk_dg_item_coupon_get_response']['results']['tbk_coupon']
        for item in proMap:
            count = Product_item.objects.filter(num_iid=int(item['num_iid'])).count()
            if (count >= 1):
                item['checked'] = "checked"
            # print(item['num_iid'])
    except Exception, e:
        print(e)

    return render(request, "pro_list.html", {'list': proMap, 'productName': productName, 'productCat': productCat})

def saveProlist(request):

    pro_list = request.GET.get("list")
    arr = json.loads(pro_list)
    for item in arr:
        item_map = eval(item)

        count = Product_item.objects.filter(num_iid=int(item_map['num_iid'])).count()
        if (count >= 1):
            continue
        save(item_map)

    return HttpResponse(json.dumps({"status": 200,"result": "成功"}))

def checkedProlist(request):
    list = Product_item.objects.all(employ=1)
    return render(request, "checked_list.html", {'list': list})

def save(item_map):
    obj = Product_item()
    obj.num_iid = int(item_map['num_iid'])
    obj.category = item_map['category']
    obj.small_images = item_map['small_images']
    obj.shop_title = item_map['shop_title']
    obj.user_type = item_map['user_type']
    obj.zk_final_price = item_map['zk_final_price']
    obj.title = item_map['title']
    obj.nick = item_map['nick']
    obj.seller_id = int(item_map['seller_id'])
    obj.volume = item_map['volume']
    obj.pict_url = item_map['pict_url']
    obj.item_url = item_map['item_url']
    obj.coupon_total_count = item_map['coupon_total_count']
    obj.commission_rate = item_map['commission_rate']
    obj.coupon_info = item_map['coupon_info']
    obj.category = item_map['category']
    obj.coupon_remain_count = item_map['coupon_remain_count']
    obj.coupon_start_time = item_map['coupon_start_time']
    obj.coupon_end_time = item_map['coupon_end_time']
    obj.coupon_click_url = item_map['coupon_click_url']
    obj.item_description = item_map['item_description']
    obj.employ = 0
    obj.save()