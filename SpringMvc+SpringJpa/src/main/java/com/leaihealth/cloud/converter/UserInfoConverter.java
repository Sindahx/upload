/**
 *
 * Version Information:
 *
 * Date: 2018/06/06
 *
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.converter;

import com.leaihealth.cloud.entity.idem.*;
import com.leaihealth.cloud.vo.response.ResponseUserInfoVo;

/**
 * @author Joseph S. Kuo
 * @since 3.5.0, 2018/06/06
 */
public class UserInfoConverter implements Converter<HzUserInfo, ResponseUserInfoVo> {
    private static final UserInfoConverter instance = new UserInfoConverter();

    private UserInfoConverter() {
    }

    public static UserInfoConverter getInstance() {
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseUserInfoVo convert(final HzUserInfo entity) {
        if (entity == null) {
            return null;
        }

        final ResponseUserInfoVo vo = new ResponseUserInfoVo();
        vo.setNote(entity.getAbstracts());
        vo.setSex(entity.getSex());
        vo.setStatus(entity.getStatus());
        return vo;
    }

}
