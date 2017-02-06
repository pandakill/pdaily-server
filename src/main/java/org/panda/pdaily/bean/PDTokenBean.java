package org.panda.pdaily.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 对应数据库t_token表
 *
 * Created by fangzanpan on 2017/2/6.
 */
public class PDTokenBean implements Serializable {

    private long userId;
    private String token;
    private String caller;
    private long createDate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "PDTokenBean{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                ", caller='" + caller + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
