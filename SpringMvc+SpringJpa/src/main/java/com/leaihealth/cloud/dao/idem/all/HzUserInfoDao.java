package com.leaihealth.cloud.dao.idem.all;

import com.leaihealth.cloud.dao.idem.GenericDao;
import com.leaihealth.cloud.dao.idem.system.UserInfoDao;
import com.leaihealth.cloud.entity.idem.HzUserInfo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface HzUserInfoDao<T, ID extends Serializable> extends UserInfoDao, GenericDao<HzUserInfo, Integer>,JpaSpecificationExecutor {

    HzUserInfo findByLoginNameAndPassword(String loginName, String password);
}
