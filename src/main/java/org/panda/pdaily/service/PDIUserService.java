package org.panda.pdaily.service;


import org.panda.pdaily.bean.PDDailyContentBean;
import org.panda.pdaily.bean.PDUserInfoBean;

import java.util.List;

/**
 * 用户的service业务实现层
 * 包括用户登良验证、token验证、注册业务
 *
 * Created by fangzanpan on 2017/1/4.
 */
public interface PDIUserService {
    int add(PDUserInfoBean user);
    int update(PDUserInfoBean user);
    int delete(Long id);
    PDUserInfoBean findUser(Long id);
    List<PDUserInfoBean> findUserList();

    List<PDDailyContentBean> findUserDailyContents(long userId);
}
