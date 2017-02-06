package org.panda.pdaily.service;

import org.apache.log4j.Logger;
import org.panda.pdaily.model.PDRequestModel;
import org.panda.pdaily.util.PDHttpStatus;
import org.panda.pdaily.util.PDRequestParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PDITokenService mTokenService;

    public PDHttpStatus checkTokenAvailable(long userId, String caller, String token) {
        if (mTokenService.isTokenAvailable(userId, caller, token)) {
            return PDHttpStatus.SUCCESS;
        } else {
            return PDHttpStatus.FAIL_USER_TOKEN_UNVALID;
        }
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

    public PDHttpStatus checkRequestParams(PDRequestModel requestModel) {
        if (checkRequestID(PDRequestParamsUtil.getRequestId(requestModel)) != PDHttpStatus.SUCCESS) {
            return checkRequestID(PDRequestParamsUtil.getRequestId(requestModel));
        } else if (checkDataAvailable((HashMap<String, Object>) PDRequestParamsUtil.getRequestParams(requestModel, HashMap.class),
                PDRequestParamsUtil.getSign(requestModel))
                != PDHttpStatus.SUCCESS) {
            return checkDataAvailable((HashMap<String, Object>) PDRequestParamsUtil.getRequestParams(requestModel, HashMap.class),
                    PDRequestParamsUtil.getSign(requestModel));
        }

        return PDHttpStatus.SUCCESS;
    }
}
