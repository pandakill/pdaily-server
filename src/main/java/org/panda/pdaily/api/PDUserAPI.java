package org.panda.pdaily.api;

import org.apache.log4j.Logger;
import org.panda.pdaily.bean.PDUserInfoBean;
import org.panda.pdaily.model.PDRequestModel;
import org.panda.pdaily.model.PDResultData;
import org.panda.pdaily.service.PDISecurityService;
import org.panda.pdaily.service.PDITokenService;
import org.panda.pdaily.service.PDIUserService;
import org.panda.pdaily.util.MD5;
import org.panda.pdaily.util.PDHttpStatus;
import org.panda.pdaily.util.PDRequestParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 用户的api
 *
 * Created by fangzanpan on 2017/2/6.
 */
@RestController
@RequestMapping(value = "/user")
public class PDUserAPI {

    private Logger logger = Logger.getLogger(PDUserAPI.class);

    @Autowired
    private PDISecurityService mSecurityService;
    @Autowired
    private PDITokenService mTokenService;
    @Autowired
    private PDIUserService mUserService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public PDResultData login(@RequestParam PDRequestModel requestModel) {

        if (mSecurityService.checkRequestParams(requestModel) != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(mSecurityService.checkRequestParams(requestModel), null);
        }

        try {
            HashMap<String, Object> data = PDRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
            if (data == null) {
                logger.info("登录失败：请求参数为空");
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
            }
            long userId = (Long) data.get("user_id");
            String password = (String) data.get("password");

            PDUserInfoBean userBean = mUserService.findUser(userId);
            if (userBean == null) {
                logger.info("登录失败：用户不存在");
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_USER_UNKNOWK, null);
            } else {
                if ("".equals(password) || password == null) {
                    logger.info("登录失败：密码为空");
                    return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_USER_PSW_UNVALID, null);
                }

                String encodePsw = MD5.md5(password);
                if (encodePsw == null || !encodePsw.equals(userBean.getPassword())) {
                    logger.info("登录失败：与数据库密码不匹配");
                    return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_USER_PSW_UNVALID, null);
                }

                HashMap<String, Object> resultData = new HashMap<String, Object>();
                resultData.put("user_id", userId);
                String token = mTokenService.createTokenByUserIdAndCaller(userId, encodePsw, PDRequestParamsUtil.getClientCaller(requestModel));
                resultData.put("token", token);
                return PDResultData.getSuccessData(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }
    }

    public PDResultData register(@RequestParam HashMap<String, Object> params) {

        return PDResultData.getSuccessData(null);
    }
}
