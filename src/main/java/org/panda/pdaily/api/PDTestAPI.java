package org.panda.pdaily.api;

import org.apache.log4j.Logger;
import org.panda.pdaily.bean.PDUserInfoBean;
import org.panda.pdaily.mapper.PDUserMapper;
import org.panda.pdaily.model.PDResultData;
import org.panda.pdaily.service.PDISecurityService;
import org.panda.pdaily.service.PDITokenService;
import org.panda.pdaily.service.PDIUserService;
import org.panda.pdaily.util.PDHttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 用户接口，提供登录、注册等接口
 *
 * Created by fangzanpan on 2017/2/4.
 */
@RestController
@RequestMapping(value = "/user")
public class PDTestAPI {

    private static Logger sLog = Logger.getLogger(PDTestAPI.class);

    @Autowired
    private PDIUserService mUserService;
    @Autowired
    private PDISecurityService mSecurityService;
    @Autowired
    private PDITokenService mTokenService;
    @Autowired
    private PDUserMapper mUserMapper;

    @RequestMapping(value = "/test_api", method = RequestMethod.GET)
    public PDResultData testAPI(@RequestParam HashMap<String, Object> params) {
        sLog.info("PARAMS : " + params.toString());
        sLog.info("PATAMS.c : " + params.get("c").toString());
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

        sLog.info("testMapper.size = " + (mUserMapper.getUsers() == null ? 0 : mUserMapper.getUsers().size()));

        return PDResultData.getSuccessData(mUserMapper.getUsers());
    }

    @RequestMapping(value = "/test_get_users", method = RequestMethod.GET)
    public PDResultData testGetUsers() {
        sLog.info("testGetUsers.size = " + (mUserService.findUserList() == null ? 0 : mUserService.findUserList().size()));
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("users", mUserService.findUserList());
        return PDResultData.getSuccessData(result);
    }

    @RequestMapping(value = "/test_login", method = RequestMethod.GET)
    public PDResultData testLogin(@RequestParam HashMap<String, Object> params) {
        long userId = Long.parseLong((String) params.get("user_id"));
        String password = (String) params.get("password");
        String caller = (String) params.get("caller");

        PDUserInfoBean userInfoBean = mUserService.findUser(userId);

        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("user_id", userId);
        result.put("token", mTokenService.createTokenByUserIdAndCaller(userId, password, caller));
        result.put("user_name", userInfoBean.getUserName());
        result.put("birthday", userInfoBean.getBirthDay());

        return PDResultData.getSuccessData(result);
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
        result.put("birthday", userInfoBean.getBirthDay());

        return PDResultData.getSuccessData(result);
    }

}
