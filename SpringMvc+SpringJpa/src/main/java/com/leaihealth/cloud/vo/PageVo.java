package com.leaihealth.cloud.vo;

/**
 * ClassName: com.leaihealth.cloud.vo.request
 * Version Information :
 * Date: 2018/9/14
 * COPYRIGHT (C) 2016 RETair.com. All Rights Reserved.
 */
public class PageVo {


    private Integer page = 1;

    private Integer limit = 10;




    public Integer getPage() {
        return page-1;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        if (limit == null) {
            return 10;
        } else {
            return limit;
        }
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
