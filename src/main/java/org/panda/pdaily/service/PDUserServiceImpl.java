package org.panda.pdaily.service;

import org.panda.pdaily.bean.PDUserInfoBean;
import org.panda.pdaily.dao.PDIUserDao;
import org.panda.pdaily.mapper.PDUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户操作的业务实现层
 *
 * Created by fangzanpan on 2017/1/4.
 */
@Service(value = "userService")
public class PDUserServiceImpl implements PDIUserService {

    @Autowired
    private PDIUserDao userDao;

    @Autowired
    private PDUserMapper mapper;

    public int add(PDUserInfoBean user) {
        return userDao.add(user);
    }

    public int update(PDUserInfoBean user) {
        return userDao.update(user);
    }

    public int delete(Long id) {
        return userDao.delete(id);
    }

    public PDUserInfoBean findUser(Long id) {
        return userDao.findById(id);
    }

    public List<PDUserInfoBean> findUserList() {
//        return userDao.findBeans();
        return mapper.getUsers();
    }
}
