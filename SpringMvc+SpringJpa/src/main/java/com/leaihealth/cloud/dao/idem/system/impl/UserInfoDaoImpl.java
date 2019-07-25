package com.leaihealth.cloud.dao.idem.system.impl;

import com.leaihealth.cloud.dao.idem.impl.AbstractDaoImpl;
import com.leaihealth.cloud.dao.idem.system.UserInfoDao;
import com.leaihealth.cloud.entity.idem.*;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;

/**
 * Created by Administrator on 2018/7/31/031.
 */
public class UserInfoDaoImpl<T, ID> extends AbstractDaoImpl<HzUserInfo> implements UserInfoDao {

    @Override
    public List<HzUserInfo> getUserByRoleId(Long releId) {

        final JPAQuery<HzUserInfo> query = super.createQuery();

        return query.fetch();
    }
}
