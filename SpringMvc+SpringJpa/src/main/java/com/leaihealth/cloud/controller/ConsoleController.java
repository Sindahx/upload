/**
 * ClassName: ConsoleController.java
 * <p>
 * Version Information:
 * <p>
 * Date: 2018/06/04
 * <p>
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.controller;

import com.leaihealth.cloud.converter.UserInfoConverter;
import com.leaihealth.cloud.entity.idem.HzUserInfo;
import com.leaihealth.cloud.service.UserInfoService;
import com.leaihealth.cloud.util.BusException;
import com.leaihealth.cloud.util.JsonResponse;
import com.leaihealth.cloud.vo.response.ResponseUserInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * The controller for login/logout.
 *
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/04
 */
@RestController
@RequestMapping(value = "/console")
public class ConsoleController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * Log in.
     *
     * @return JSON response with OK message
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResponse login(String loginName, String password, HttpServletRequest request, HttpSession session) throws BusException {
        Map<String, Object> result = new HashMap<>();

        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
            return JsonResponse.failed().withMessage("login.isNull").withCode("login.isNull");
        }


        HzUserInfo userInfoDB = userInfoService.login(loginName, password, request);

        if (userInfoDB == null) {
            return JsonResponse.failed().withMessage("login.error_loginNameAndPassword").withCode("login.error_loginNameAndPassword");
        }

        result.put("toKen", userInfoDB.getToken());
        ResponseUserInfoVo vo = UserInfoConverter.getInstance().convert(userInfoDB);
        result.put("userInfo", vo);
        result.put("userAuth", userInfoDB.getAuthorities());
        return JsonResponse.ok().withData(result).withMessage("login.success");
    }


    /**
     * Log out.
     *
     * @return JSON response with OK message
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public JsonResponse logout(HttpSession session) {
//        HttpSession session = req.getSession(true);
        final String sessionToken = (String) session.getAttribute("sessionAuth");
        this.userInfoService.logout(sessionToken);

        return JsonResponse.ok();
    }
}
