package org.panda.pdaily.util;

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

    public static String getEncrypt(HashMap<String, Object> params) {
        if (null != params.get("encrypt")) {
            try {
                return (String) params.get("encrypt");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
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

    public static String getClientVersion(HashMap<String, Object> params) {

        return null;
    }

    public static String getClientCaller(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> clientInfo = (HashMap<String, Object>) params.get("client");
            return (String) clientInfo.get("version");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    public static String getSign(HashMap<String, Object> params) {
        try {
            return (String) params.get("sign");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserId(HashMap<String, Object> params) {
        try {
            HashMap<String, Object> dataInfo = (HashMap<String, Object>) params.get("data");
            return (String) dataInfo.get("user_id");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
}
