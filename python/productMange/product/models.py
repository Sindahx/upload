# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.

class UserInfo(models.Model):
    userid = models.TextField(null=True)
    userPwd = models.TextField(null=True)

class Product_item(models.Model):
    small_images = models.TextField(null=True)
    shop_title = models.TextField(null=True)
    user_type = models.TextField(null=True)
    zk_final_price = models.DecimalField(null=True, max_digits=10, decimal_places=2)
    title = models.TextField(null=True)
    nick = models.TextField(null=True)
    seller_id = models.BigIntegerField(null=True)
    volume = models.TextField(null=True)
    pict_url = models.TextField(null=True)
    item_url = models.TextField(null=True)
    coupon_total_count = models.TextField(null=True)
    commission_rate = models.DecimalField(null=True, max_digits=10, decimal_places=2)
    coupon_info = models.TextField(null=True)
    category = models.TextField(null=True)
    num_iid = models.BigIntegerField(null=True)
    coupon_remain_count = models.TextField(null=True)
    coupon_start_time = models.TextField(null=True)
    coupon_end_time = models.TextField(null=True)
    coupon_click_url = models.TextField(null=True)
    item_description = models.TextField(null=True)
    employ = models.IntegerField(null=True)

    def __unicode__(self):
        return self.title
