package org.panda.pdaily.util;

import org.panda.pdaily.model.PDRequestModel;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * 请求参数的处理类，提取请求参数里面的各种参数
 *
 * Created by fangzanpan on 2017/2/5.
 */
public class PDRequestParamsUtil {

//    {
//        id : 1234567890
//        client : {
//            caller : android_offical	// 用户APP的来源
//            version : v2.1	// 用户APP的版本号
//            date : 1222 // 用户手机端的时间戳
//            ex : {
//                sc : 720,1280	// 宽*高
//                dv : xiaomi_os	// 用户固件
//                uid : 122fff	// 用户唯一id
//                sf : pp	// 渠道
//                os : android	// 操作系统
//            }
//        }
//        encrypt : “md5”
//        sign : fafafafafafafa
//        data : {
//            user_id : 12
//        }
//    }

    public static long getRequestId (HashMap<String, Object> params) {
        if (null != params.get("id")) {
            if (params.get("id") instanceof String) {
                try {
                    return (Long) params.get("id");
                } catch (Exception e) {
                    e.printStackTrace();
                    return -1;
                }
            }
        }

        return -1;
    }

    public static long getRequestId (PDRequestModel requestModel) {
        if (null != requestModel.getId() && !"".equals(requestModel.getId())) {
            return Long.parseLong(requestModel.getId());
        }

        return -1;
    }

    public static String getEncrypt(HashMap<String, Object> params) {
        try {
            if (null != params.get("encrypt")) {
                return (String) params.get("encrypt");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String getEncrypt(PDRequestModel requestModel) {
        try {
            if (!StringUtils.isEmpty(requestModel.getEncrypt())) {
                    return requestModel.getEncrypt();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static HashMap<String, Object> getRequestParams(HashMap<String, Object> params) {
        if (null != params.get("data")) {
            try {
                return (HashMap<String, Object>) params.get("data");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static <T> T getRequestParams(PDRequestModel requestModel, Class<T> targetClass) {
        try {
            if (null != requestModel.getData()) {
                return (T) requestModel.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String getClientVersion(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> clientInfo = (HashMap<String, Object>) params.get("client");
            return (String) clientInfo.get("version");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getClientVersion(PDRequestModel requestModel) {
        try {
            if (!StringUtils.isEmpty(requestModel.getClient().getVersion())) {
                return requestModel.getClient().getVersion();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String getClientCaller(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> clientInfo = (HashMap<String, Object>) params.get("client");
            return (String) clientInfo.get("caller");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getClientCaller(PDRequestModel requestModel) {
        try {
            if (!StringUtils.isEmpty(requestModel.getClient().getCaller())) {
                return requestModel.getClient().getCaller();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static String getClientDate(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> clientInfo = (HashMap<String, Object>) params.get("client");
            return (String) clientInfo.get("date");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long getClientDate(PDRequestModel requestModel) {
        try {
            if (!StringUtils.isEmpty(requestModel.getClient().getDate())) {
                return Long.parseLong(requestModel.getClient().getDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static String getSign(HashMap<String, Object> params) {
        try {
            return (String) params.get("sign");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSign(PDRequestModel requestModel) {
        try {
            if (!StringUtils.isEmpty(requestModel.getSign())) {
                return requestModel.getSign();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static long getUserId(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> dataInfo = (HashMap<String, Object>) params.get("data");
            return Long.parseLong((String) dataInfo.get("user_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getUserId(PDRequestModel requestModel) {
        try {
            if (requestModel.getData() instanceof HashMap) {
                HashMap<String, Object> data = (HashMap<String, Object>) requestModel.getData();
                return Long.parseLong((String) data.get("user_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    public static String getToken(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> dataInfo = (HashMap<String, Object>) params.get("data");
            return (String) dataInfo.get("token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getToken(PDRequestModel requestModel) {
        try {
            if (requestModel.getData() instanceof HashMap) {
                HashMap<String, Object> data = (HashMap<String, Object>) requestModel.getData();
                return (String) data.get("token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
