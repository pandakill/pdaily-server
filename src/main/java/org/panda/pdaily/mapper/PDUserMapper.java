package org.panda.pdaily.mapper;

import org.panda.pdaily.bean.PDUserInfoBean;

import java.util.List;

/**
 * mybatis的映射接口
 *
 * Created by fangzanpan on 2017/2/6.
 */
public interface PDUserMapper {

    List<PDUserInfoBean> getUsers();

    PDUserInfoBean getUserById(long id);

    void registerUser(PDUserInfoBean userInfoBean);

    void updateUserPsw(PDUserInfoBean userInfoBean);
}
