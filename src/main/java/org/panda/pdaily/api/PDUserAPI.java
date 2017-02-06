package org.panda.pdaily.api;

import org.apache.log4j.Logger;
import org.panda.pdaily.bean.PDUserInfoBean;
import org.panda.pdaily.model.PDResultData;
import org.panda.pdaily.service.PDISecurityService;
import org.panda.pdaily.service.PDIUserService;
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
    private PDIUserService mUserService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public PDResultData login(@RequestParam HashMap<String, Object> params) {

        if (mSecurityService.checkRequestParams(params) != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(mSecurityService.checkRequestParams(params), null);
        }

        try {
            HashMap<String, Object> data = PDRequestParamsUtil.getRequestParams(params);
            long userId = (Long) data.get("user_id");
            String password = (String) data.get("password");

            PDUserInfoBean userBean = mUserService.findUser(userId);
            if (userBean == null) {
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_USER_UNKNOWK, null);
            } else {
                if ("".equals(password))
                if (password.equals(userBean.getPassword())) {
                    return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_USER_PSW_UNVALID, null);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }

        return PDResultData.getSuccessData(null);
    }
}
