/**
 * ClassName: JpaRealm.java
 *
 * Version Information:
 *
 * Date: 2018/06/06
 *
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.security;

import com.leaihealth.cloud.dao.idem.all.HzUserInfoDao;
import com.leaihealth.cloud.entity.idem.HzUserInfo;
import com.leaihealth.cloud.util.MD5Util;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * JPA realm.
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/06
 */
public class JpaRealm extends AuthorizingRealm {
    private static String REALM_NAME = "jpaRealm";

    @Autowired
    private HzUserInfoDao userInfoDao;

    /**
     * Constructor.
     */
    public JpaRealm() {
        this.setName(REALM_NAME);
    }

    /**
     * get authentication info
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(final AuthenticationToken authToken) throws AuthenticationException {
        final UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        final String password = new String(token.getPassword());

        HzUserInfo user  = this.userInfoDao.findByLoginNameAndPassword(token.getUsername(), MD5Util.getMD5ofStr(password));

        if (user == null) {
            throw new IncorrectCredentialsException("unknown operator_: " + token.getUsername());
        }

        return new SimpleAuthenticationInfo(user, password, this.getName());
    }

    /**
     * get authorization info
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
        final HzUserInfo user = SecurityUtil.getUser();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();

        info.setStringPermissions(user.getAuthorities());
        return info;
    }
}
