package org.panda.pdaily.service;

import org.apache.log4j.Logger;
import org.panda.pdaily.bean.PDTokenBean;
import org.panda.pdaily.dao.PDITokenDao;
import org.panda.pdaily.util.Base64;
import org.panda.pdaily.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * token的service层
 *
 * Created by fangzanpan on 2017/2/6.
 */
@Service("tokenService")
public class PDTokenServiceImpl implements PDITokenService {

    private Logger logger = Logger.getLogger(PDTokenServiceImpl.class);

    private static final long TOKEN_AVAILABLE_TIME = 7 * 24 * 60 * 60 * 1000;

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
        String token = Base64.encode(base64EncodeStr.getBytes());

        if (tokenBean != null) {
            tokenBean.setToken(token);
            tokenBean.setCreateDate(currentTimeMillis);
            update(tokenBean);
        } else {
            tokenBean = new PDTokenBean();
            tokenBean.setToken(token);
            tokenBean.setUserId(userId);
            tokenBean.setCaller(caller);
            tokenBean.setCreateDate(currentTimeMillis);

            add(tokenBean);
        }

        return token;
    }

    /**
     * token有效时间默认为7天
     *
     * @param userId    用户id
     * @param caller    用户的caller
     * @param token     用户传进来的token
     * @return  如果token有效返回true
     */
    public boolean isTokenAvailable(long userId, String caller, String token) {

        PDTokenBean tokenBean = findTokenByUserIdAndCaller(userId, caller);
        if (tokenBean == null) {
            return false;
        }

        String decodeToken = new String(Base64.decode(token));
        String[] tokenDecode = decodeToken.split("_date=");
        if (tokenDecode.length != 2) {
            logger.info("token被篡改：解密的token为：" + Arrays.toString(tokenDecode));
            return false;
        }
        long tokenDate;
        try {
            tokenDate = Long.parseLong(tokenDecode[1]);
        } catch (Exception e) {
            logger.info("token被篡改：时间戳格式有误.");
            return false;
        }

        if (tokenBean.getCreateDate() != tokenDate) {
            logger.info("token.getCreateDate().getTime = " + tokenBean.getCreateDate() + "; tokenDate = " + tokenDate);
            logger.info("token被篡改：时间戳与数据库匹配不正确.");
            return false;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - tokenDate >= TOKEN_AVAILABLE_TIME) {
            logger.info("token超时失效.");
            return false;
        }

        return true;
    }
}
