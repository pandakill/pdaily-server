package org.panda.pdaily.service;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.panda.pdaily.bean.PDTokenBean;
import org.panda.pdaily.dao.PDITokenDao;
import org.panda.pdaily.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * token的service层
 *
 * Created by fangzanpan on 2017/2/6.
 */
@Service("tokenService")
public class PDTokenServiceImpl implements PDITokenService {

    private Logger logger = Logger.getLogger(PDTokenServiceImpl.class);

    @Autowired
    private PDITokenDao mTokenDao;

    public int add(PDTokenBean bean) {
        return mTokenDao.add(bean);
    }

    public int update(PDTokenBean bean) {
        return mTokenDao.update(bean);
    }

    public int deleteById(Long id) {
        return mTokenDao.delete(id);
    }

    public PDTokenBean findById(Long id) {
        return mTokenDao.findById(id);
    }

    public List<PDTokenBean> findBeanList() {
        return mTokenDao.findBeans();
    }

    public PDTokenBean findTokenByUserIdAndCaller(long userId, String caller) {
        return mTokenDao.findByUserIdAndCaller(userId, caller);
    }

    public int deleteTokenByUserIdAndCaller(long userId, String caller) {
        return mTokenDao.deleteByUserIdAndCaller(userId, caller);
    }

    /**
     * 1、按照升序，password+userId进行md5加密
     * 2、然后将步骤1加密所得的字符串+caller进行md5加密
     * 3、将步骤3的加密字符串 + "_date=" + date 进行base64加密存储
     *
     * @param userId    用户唯一id
     * @param password  用户密码
     * @param caller    用户的caller
     * @return  生成的token
     */
    public String createTokenByUserIdAndCaller(long userId, String password, String caller) {
        PDTokenBean tokenBean = findTokenByUserIdAndCaller(userId, caller);
        String md5Psw = MD5.md5((password + userId));
        md5Psw = MD5.md5(md5Psw + caller);
        long currentTimeMillis = System.currentTimeMillis();
        String base64EncodeStr = md5Psw + "_date=" + currentTimeMillis;
        String token = Base64.encodeBase64String(base64EncodeStr.getBytes());

        if (tokenBean != null) {
            tokenBean.setToken(token);
            update(tokenBean);
        } else {
            tokenBean = new PDTokenBean();
            tokenBean.setToken(token);
            tokenBean.setUserId(userId);
            tokenBean.setCaller(caller);
            tokenBean.setCreateDate(new Date(currentTimeMillis));

            add(tokenBean);
        }

        return token;
    }
}
