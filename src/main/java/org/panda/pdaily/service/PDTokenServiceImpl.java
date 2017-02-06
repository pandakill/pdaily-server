package org.panda.pdaily.service;

import org.apache.catalina.util.MD5Encoder;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.panda.pdaily.bean.PDTokenBean;
import org.panda.pdaily.dao.PDITokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.util.Arrays;
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
     * 按照升序，password+userId进行base64加密
     * 然后再将base64+时间戳进行md5加密存储
     *
     * @param userId    用户唯一id
     * @param password  用户密码
     * @param caller    用户的caller
     * @return  生成的token
     */
    public String createTokenByUserIdAndCaller(long userId, String password, String caller) {
        PDTokenBean tokenBean = findTokenByUserIdAndCaller(userId, caller);
        logger.info("createTokenByUserIdAndCaller() called with: " + "userId = [" + userId + "], password = [" + password + "], caller = [" + caller + "]");
        String md5Psw = MD5Encoder.encode((password + userId).getBytes());
        byte[] pswByte = (password + userId).getBytes();
        logger.info("md5Psw : " + md5Psw + "; pswByte = " + pswByte);
        long currentTimeMillis = System.currentTimeMillis();
        String base64EncodeStr = caller + md5Psw + currentTimeMillis;
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
