package org.panda.pdaily.api;

import org.apache.log4j.Logger;
import org.panda.pdaily.bean.PDUserInfoBean;
import org.panda.pdaily.model.PDRequestModel;
import org.panda.pdaily.model.PDResultData;
import org.panda.pdaily.model.response.PDRepUserInfoModel;
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
 * 用户接口，提供登录、注册等接口
 *
 * Created by fangzanpan on 2017/2/4.
 */
@RestController
@RequestMapping(value = "/test")
public class PDTestAPI {

    private static Logger logger = Logger.getLogger(PDTestAPI.class);

    @Autowired
    private PDIUserService mUserService;
    @Autowired
    private PDISecurityService mSecurityService;
    @Autowired
    private PDITokenService mTokenService;

    @RequestMapping(value = "/test_api", method = RequestMethod.GET)
    public PDResultData testAPI(@RequestParam HashMap<String, Object> params) {
        logger.info("PARAMS : " + params.toString());
        logger.info("PATAMS.c : " + params.get("c").toString());
        String a = (String) params.get("a");
//        PDHttpStatus checkResult = mSecurityService.checkTokenAvailable("1".equals(a) ? "token" : "token1");

        PDHttpStatus checkResult = mSecurityService.checkRequestParams(params);
        if (checkResult != PDHttpStatus.SUCCESS) {
            return PDResultData.getResultData(checkResult.code, checkResult.msg);
        }

        return PDResultData.getSuccessData(null);
    }

    @RequestMapping(value = "/test_mapper_get_users", method = RequestMethod.GET)
    public PDResultData testMapper() {

        logger.info("testMapper.size = " + (mUserService.findUserList() == null ? 0 : mUserService.findUserList()));
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("users", mUserService.findUserList());

        return PDResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/test_get_users", method = RequestMethod.GET)
    public PDResultData testGetUsers() {
        logger.info("testGetUsers.size = " + (mUserService.findUserList() == null ? 0 : mUserService.findUserList().size()));
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("users", mUserService.findUserList());
        return PDResultData.getSuccessData(result);
    }

    // 测试账号：userId=1，psw=fangzanpan，caller=chrome_test
    @RequestMapping(value = "/test_login", method = RequestMethod.GET)
    public PDResultData testLogin(@RequestParam HashMap<String, Object> params) {
        try {
            long userId = Long.parseLong((String) params.get("user_id"));
            String password = (String) params.get("password");
            String caller = (String) params.get("caller");
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
                logger.info("加密后的密码是：" + encodePsw);
                if (encodePsw == null || !encodePsw.equals(userBean.getPassword())) {
                    logger.info("登录失败：与数据库密码不匹配");
                    return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_USER_PSW_UNVALID, null);
                }

                HashMap<String, Object> resultData = new HashMap<String, Object>();
                resultData.put("user_id", userId);
                String token = mTokenService.createTokenByUserIdAndCaller(userId, encodePsw, caller);
                resultData.put("token", token);
                resultData.put("user_name", mUserService.findUser(userId).getUserName());
                return PDResultData.getSuccessData(resultData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }
    }

    @RequestMapping(value = "/test_get_user_info", method = RequestMethod.GET)
    public PDResultData getUserInfo(@RequestParam HashMap<String, Object> params) {

        long userId = Long.parseLong((String) params.get("user_id"));
        String token = (String) params.get("token");
        String caller = (String) params.get("caller");

        PDHttpStatus checkToken = mSecurityService.checkTokenAvailable(userId, caller, token);
        if (checkToken != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(checkToken, null);
        }

        PDUserInfoBean userInfoBean = mUserService.findUser(userId);

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("user_id", userId);
        result.put("user_name", userInfoBean.getUserName());
        result.put("birthday", userInfoBean.getBirthday());

        return PDResultData.getSuccessData(result);
    }

    // 支持json的请求头
    // 测试账号：userId=1，psw=fangzanpan，caller=chrome_test
    @RequestMapping(value = "/test_post", method = RequestMethod.POST)
    public PDResultData testPost(@RequestBody PDRequestModel requestModel) {

        if (mSecurityService.checkRequestParams(requestModel) != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(mSecurityService.checkRequestParams(requestModel), null);
        }

        try {
            HashMap<String, Object> data = (HashMap<String, Object>) requestModel.getData();
            if (data == null) {
                logger.info("登录失败：请求参数为空");
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_REQUEST_UNVALID_PARAMS, null);
            }
            long userId = Long.parseLong((String) data.get("user_id"));
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
                return PDResultData.getSuccessData(resultData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }
    }

    // 支持json的请求头
    // 测试账号：userId=1，psw=fangzanpan，caller=chrome_test
    @RequestMapping(value = "/test_get_user_info", method = RequestMethod.POST)
    public PDResultData getUserInfo(@RequestBody PDRequestModel requestModel) {

        if (mSecurityService.checkRequestParams(requestModel) != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(mSecurityService.checkRequestParams(requestModel), null);
        }

        try {
            HashMap<String, Object> data = (HashMap<String, Object>) requestModel.getData();
            long userId = Long.parseLong((String) data.get("user_id"));
            String token = (String) data.get("token");
            String caller = PDRequestParamsUtil.getClientCaller(requestModel);
            PDHttpStatus checkToken = mSecurityService.checkTokenAvailable(userId, caller, token);
            if (checkToken != PDHttpStatus.SUCCESS) {
                return PDResultData.getHttpStatusData(checkToken, null);
            }

            PDUserInfoBean userInfoBean = mUserService.findUser(userId);

            return PDResultData.getSuccessData(new PDRepUserInfoModel(userInfoBean));
        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }
    }

    // 支持json的请求头
    // 测试账号：userId=1，psw=fangzanpan，caller=chrome_test
    @RequestMapping(value = "/get_user_daily_contents", method = RequestMethod.POST)
    public PDResultData getUserDailyContents(@RequestBody PDRequestModel requestModel) {

        if (mSecurityService.checkRequestParams(requestModel) != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(mSecurityService.checkRequestParams(requestModel), null);
        }

        try {
            HashMap<String, Object> data = (HashMap<String, Object>) requestModel.getData();
            long userId = Long.parseLong((String) data.get("user_id"));
            String token = (String) data.get("token");
            String caller = PDRequestParamsUtil.getClientCaller(requestModel);
            PDHttpStatus checkToken = mSecurityService.checkTokenAvailable(userId, caller, token);
            if (checkToken != PDHttpStatus.SUCCESS) {
                return PDResultData.getHttpStatusData(checkToken, null);
            }

            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("user_id", userId + "");
            result.put("daily_contents", mUserService.findUserDailyContents(userId));

            return PDResultData.getSuccessData(result);
        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }
    }

}
