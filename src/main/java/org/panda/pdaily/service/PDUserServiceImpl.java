package org.panda.pdaily.service;

import org.panda.pdaily.bean.PDDailyContentBean;
import org.panda.pdaily.bean.PDUserInfoBean;
import org.panda.pdaily.dao.PDIUserDao;
import org.panda.pdaily.mapper.PDDailyContentMapper;
import org.panda.pdaily.mapper.PDUserMapper;
import org.panda.pdaily.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private PDDailyContentMapper dailyContentMapper;

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

    public List<PDDailyContentBean> findUserDailyContents(long userId) {
        return dailyContentMapper.getDailyContentsByUserId(userId);
    }

    /**
     * 注册新的账号
     *
     * @param mobile    手机号码
     * @param verCode   短信验证码
     * @param password  密码，在客户端已经进行了md5(base64(password))，在注册完成之后需要md5(客户端+userId)
     */
    public PDUserInfoBean register(String mobile, String verCode, String password) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(verCode) || StringUtils.isEmpty(password)) {
            return null;
        }

        // TODO: 2017/2/13 checkVerCode
        PDUserInfoBean userInfoBean = new PDUserInfoBean();
        userInfoBean.setMobile(mobile);
        userInfoBean.setPassword(password);

        System.out.println("插入之前的结果：" + userInfoBean.getId() + ", password = " + password);
        mapper.registerUser(userInfoBean);
        String md5Psw = MD5.md5(password + "_userId=" + userInfoBean.getId());
        userInfoBean.setPassword(md5Psw);
        mapper.updateUserPsw(userInfoBean);
        System.out.println("插入之后的结果：" + userInfoBean.getId() + ", password = " + userInfoBean.getPassword());

        return userInfoBean;
    }
}
