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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 用户的api {
 *     1. login：登录接口
 *     2. register：注册接口
 *     3. get_user_info:：获取用户信息接口
 * }
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
    public PDResultData login(@RequestBody PDRequestModel requestModel) {

        if (mSecurityService.checkRequestParams(requestModel) != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(mSecurityService.checkRequestParams(requestModel), null);
        }

        try {
            HashMap<String, Object> data = PDRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
            if (data == null) {
                logger.info("登录失败：请求参数为空");
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
            }
            long userId = PDRequestParamsUtil.getUserId(requestModel);
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
                resultData.put("mobile", userBean.getMobile());
                resultData.put("user_name", userBean.getUserName());
                return PDResultData.getSuccessData(resultData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public PDResultData register(@RequestBody PDRequestModel requestModel) {
        if (mSecurityService.checkRequestParams(requestModel) != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(mSecurityService.checkRequestParams(requestModel), null);
        }

        try {
            HashMap<String, Object> data = PDRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
            if (data == null) {
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_REQUEST, null);
            }

            String mobile = (String) data.get("mobile");
            String verCode = (String) data.get("ver_code");
            String password = (String) data.get("password");

            PDUserInfoBean userInfoBean = mUserService.register(mobile, verCode, password);

            HashMap<String, Object> resultData = new HashMap<String, Object>();

            if (userInfoBean != null) {
                resultData.put("user_id", userInfoBean.getId() + "");
                resultData.put("mobile", userInfoBean.getMobile());

                return PDResultData.getSuccessData(resultData);
            } else {
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_REQUEST, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }
    }

    @RequestMapping(value = "/get_user_info", method = RequestMethod.POST)
    public PDResultData getUserInfo(@RequestBody PDRequestModel requestModel) {
        if (mSecurityService.checkRequestParams(requestModel) != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(mSecurityService.checkRequestParams(requestModel), null);
        }

        try {
            HashMap<String, Object> data = PDRequestParamsUtil.getRequestParams(requestModel, HashMap.class);
            if (data == null) {
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_REQUEST, null);
            }

            PDHttpStatus checkToken = mSecurityService.checkTokenAvailable(requestModel);
            if (checkToken != PDHttpStatus.SUCCESS) {
                return PDResultData.getHttpStatusData(checkToken, null);
            }

            long userId = PDRequestParamsUtil.getUserId(requestModel);
            PDUserInfoBean userInfoBean = mUserService.findUser(userId);

            HashMap<String, Object> resultData = new HashMap<String, Object>();
            resultData.put("user_id", userInfoBean.getId());
            resultData.put("token", PDRequestParamsUtil.getToken(requestModel));
            resultData.put("user_name", userInfoBean.getUserName());
            resultData.put("mobile", userInfoBean.getMobile());
            resultData.put("gender", userInfoBean.getGender());
            resultData.put("attribute", userInfoBean.getAttribute());
            resultData.put("birthday", userInfoBean.getBirthday());

            return PDResultData.getSuccessData(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }
    }
}
