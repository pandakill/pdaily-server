package org.panda.pdaily.service;

import org.apache.log4j.Logger;
import org.panda.pdaily.util.PDHttpStatus;
import org.panda.pdaily.util.PDRequestParamsUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 安全service的检查接口
 *
 * Created by fangzanpan on 2017/2/5.
 */
@Service(value = "securityService")
public class PDSecurityServiceImpl implements PDISecurityService {

    private Logger mLog = Logger.getLogger(PDSecurityServiceImpl.class);

    public PDHttpStatus checkTokenAvailable(String token) {
        if (token.equals("token")) {
            return PDHttpStatus.SUCCESS;
        }

        return PDHttpStatus.FAIL_USER_TOKEN_UNVALID;
    }

    public PDHttpStatus checkDataAvailable(HashMap<String, Object> data, String sign) {
        return PDHttpStatus.SUCCESS;
    }

    public PDHttpStatus checkRequestID(long id) {
        return PDHttpStatus.SUCCESS;
    }

    public PDHttpStatus checkRequestParams(HashMap<String, Object> params) {

        if (checkRequestID(PDRequestParamsUtil.getRequestId(params)) != PDHttpStatus.SUCCESS) {
            return checkRequestID(PDRequestParamsUtil.getRequestId(params));
        } else if (checkDataAvailable(PDRequestParamsUtil.getRequestParams(params), PDRequestParamsUtil.getSign(params))
                != PDHttpStatus.SUCCESS) {
            return checkDataAvailable(PDRequestParamsUtil.getRequestParams(params), PDRequestParamsUtil.getSign(params));
        }

        return PDHttpStatus.SUCCESS;
    }
}
