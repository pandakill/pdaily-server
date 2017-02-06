package org.panda.pdaily.service;

import org.panda.pdaily.bean.PDTokenBean;

/**
 * token的dao接口
 *
 * Created by fangzanpan on 2017/2/6.
 */
public interface PDITokenService extends PDIBaseDBService<PDTokenBean> {

    PDTokenBean findTokenByUserIdAndCaller(long userId, String caller);

    int deleteTokenByUserIdAndCaller(long userId, String caller);

    String createTokenByUserIdAndCaller(long userId, String password, String caller);
}
