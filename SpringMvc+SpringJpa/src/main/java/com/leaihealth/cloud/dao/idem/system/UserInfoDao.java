/**
 * ClassName: UserInfoDao.java
 * <p>
 * Version Information:
 * <p>
 * Date: 2018/06/01
 * <p>
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.dao.idem.system;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.leaihealth.cloud.entity.idem.HzUserInfo;

/**
 * The customized generic repository.
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/01
 */
@Repository
public interface UserInfoDao {

    /**
     * 根据角色id 获取所有用户
     * @param releId
     * @return
     */
    List<HzUserInfo> getUserByRoleId(Long releId);

}
