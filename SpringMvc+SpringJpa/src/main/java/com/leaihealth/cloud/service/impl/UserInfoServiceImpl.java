package com.leaihealth.cloud.service.impl;

import com.leaihealth.cloud.config.IdemDatabaseConfig;
import com.leaihealth.cloud.entity.idem.HzUserInfo;
import com.leaihealth.cloud.security.SecurityUtil;
import com.leaihealth.cloud.service.UserInfoService;
import com.leaihealth.cloud.util.BusException;
import com.leaihealth.cloud.util.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * *用户信息业务实现类)
 *
 * @author zhuxiange 2013-1-31 下午03:54:45
 */

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private static final Map<String, String> loginNameSessionCache = new ConcurrentHashMap<>();

    @Override
    @Transactional(IdemDatabaseConfig.TRANSACTION_MANAGER_BEAN_NAME)
    public void logout(final String sessionToken) {
        final HzUserInfo user = SecurityUtil.getUser(sessionToken);
        if (user != null) {
            UserInfoServiceImpl.logger.info("User logout: loginName={}", user.getLoginName());
            SecurityUtil.logout(sessionToken);
        }
//        loginNameSessionCache.remove(sessionToken);
    }


    /**
     * getUserInfo 获取用户信息
     *
     * @param
     * @return SystemUser
     * @throws BusException
     */
    @Override
    public HzUserInfo login(String loginName, String password, HttpServletRequest request) throws BusException {

        String token = SecurityUtil.login(loginName, password);
        HzUserInfo user = SecurityUtil.getUser();
        user.setToken(token);
        user.setSessionToken(token);

        if (user == null) {
            logger.warn("Login failure because not found: loginName={}", loginName);
            throw new BusException(ErrorCode.UNKNOWN_ACCOUNT_OR_INCORRECT_PASSWORD);
        }


        final String oldToken = loginNameSessionCache.get(loginName);
        if (StringUtils.isNotBlank(oldToken)) {
            logger.debug("Found old seesion token: loginName={}, token={}", loginName, oldToken);
            if (SecurityUtil.isLogin(oldToken)) {
                SecurityUtil.logout(oldToken);
                logger.debug("Logged out the user: loginName={}, token={}", loginName, oldToken);
            }
            loginNameSessionCache.remove(oldToken);
        }

        loginNameSessionCache.put(user.getLoginName(), token);

        return user;
    }

}
