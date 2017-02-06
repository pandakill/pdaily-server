package org.panda.pdaily.dao;

import org.panda.pdaily.bean.PDTokenBean;

/**
 * token的dao类
 *
 * Created by fangzanpan on 2017/2/6.
 */
public interface PDITokenDao extends PDIBaseDao<PDTokenBean> {

    int deleteByUserIdAndCaller(long userId, String caller);

    PDTokenBean findByUserIdAndCaller(long userId, String caller);
}
