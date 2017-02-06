package org.panda.pdaily.service;

import org.panda.pdaily.util.PDHttpStatus;

import java.util.HashMap;

/**
 * API接口的安全检查接口
 * 1. 在这里会进行记录请求是否合法
 * 2. token验证
 * 3. 设备的记录
 * 4. 唯一UID的记录
 *
 * Created by fangzanpan on 2017/2/5.
 */
public interface PDISecurityService {

    /**
     * 检查用户登录token是否合法;每个token有效时间为7天时间
     *
     * @param token 用户登录的token，唯一；在缓存文件token.txt文件中获取
     *
     * @return  如果合法，返回 {@link PDHttpStatus} 中的SUCCESS
     */
    PDHttpStatus checkTokenAvailable(long userId, String caller, String token);

    /**
     * 检验请求参数是否合法
     *
     * @param data    请求参数列表,即请求参数里面的data数据
     * @return  如果合法，返回 {@link PDHttpStatus} 中的SUCCESS
     */
    PDHttpStatus checkDataAvailable(HashMap<String, Object> data, String sign);

    /**
     * 检查该请求id是否唯一
     *
     * @param id    请求id
     * @return 如果合法，返回 {@link PDHttpStatus} 中的SUCCESS
     */
    PDHttpStatus checkRequestID(long id);

    /**
     * 检查请求参数是否符合要求,请求参数的格式如下
     *
     * @param params    请求参数
     * @return 如果合法，返回 {@link PDHttpStatus} 中的SUCCESS
     */
    PDHttpStatus checkRequestParams(HashMap<String, Object> params);
}
