package com.leaihealth.cloud.service;

import com.leaihealth.cloud.entity.idem.HzUserInfo;
import com.leaihealth.cloud.util.BusException;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户业务对象操作接口
 *
 * @author zhuxiange 2013-1-31 下午05:15:48
 */
public interface UserInfoService {


    void logout(String sessionToken);


    HzUserInfo login (String loginName,String password,HttpServletRequest request) throws BusException;

}
