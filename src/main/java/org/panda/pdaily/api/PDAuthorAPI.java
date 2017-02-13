package org.panda.pdaily.api;

import org.apache.log4j.Logger;
import org.panda.pdaily.bean.PDUserInfoBean;
import org.panda.pdaily.model.PDRequestModel;
import org.panda.pdaily.model.PDResultData;
import org.panda.pdaily.service.PDISecurityService;
import org.panda.pdaily.service.PDIUserService;
import org.panda.pdaily.util.PDHttpStatus;
import org.panda.pdaily.util.PDRequestParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 用户的api {
 *     1. get_author_detail：获取作者信息接口
 *     2. follow_author：关注作者接口
 * }
 *
 * Created by fangzanpan on 2017/2/13.
 */
@RestController
@RequestMapping(value = "/author")
public class PDAuthorAPI {

    private Logger logger = Logger.getLogger(PDAuthorAPI.class);

    @Autowired
    private PDISecurityService mSecurityService;
    @Autowired
    private PDIUserService mUserService;

    @RequestMapping(value = "/get_author_detail", method = RequestMethod.POST)
    public PDResultData getAuthorDetail(@RequestBody PDRequestModel requestModel) {

        if (mSecurityService.checkRequestParams(requestModel) != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(mSecurityService.checkRequestParams(requestModel), null);
        }

        try {
            HashMap<String, Object> data = PDRequestParamsUtil.getRequestParams(requestModel, HashMap.class);

            if (data == null) {
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_REQUEST, null);
            }

            long authorId = Long.parseLong((String) data.get("author_id"));

            PDUserInfoBean userBean = mUserService.findUser(authorId);

            HashMap<String, Object> resultData = new HashMap<String, Object>();
            resultData.put("author_id", userBean.getId());
            resultData.put("author_name", userBean.getUserName());
            resultData.put("icon_url", userBean.getIconUrl());
            resultData.put("daily_count", 0);

            return PDResultData.getSuccessData(resultData);

        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }
    }

    @RequestMapping(value = "/follow_author", method = RequestMethod.POST)
    public PDResultData followAuthor(@RequestBody PDRequestModel requestModel) {

        if (mSecurityService.checkRequestParams(requestModel) != PDHttpStatus.SUCCESS) {
            return PDResultData.getHttpStatusData(mSecurityService.checkRequestParams(requestModel), null);
        }

        try {
            HashMap<String, Object> data = PDRequestParamsUtil.getRequestParams(requestModel, HashMap.class);

            if (data == null) {
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_REQUEST, null);
            }

            long userId = PDRequestParamsUtil.getUserId(requestModel);
            PDHttpStatus checkToken = mSecurityService.checkTokenAvailable(requestModel);
            if (checkToken != PDHttpStatus.SUCCESS) {
                return PDResultData.getHttpStatusData(checkToken, null);
            }

            long authorId = Long.parseLong((String) data.get("author_id"));
            int isLike = Integer.parseInt((String) data.get("is_like"));

            // TODO: 2017/2/13 需要完成关注作者的代码和数据库设计
            if (authorId == userId) {
                return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_USER_UNPERMISSION, null);
            }

            return PDResultData.getSuccessData(null);

        } catch (Exception e) {
            e.printStackTrace();
            return PDResultData.getHttpStatusData(PDHttpStatus.FAIL_APPLICATION_ERROR, null);
        }
    }
}
