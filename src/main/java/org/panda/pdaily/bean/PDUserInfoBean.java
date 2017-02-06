package org.panda.pdaily.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户数据库实体类型
 *
 * Created by fangzanpan on 2017/1/4.
 */
public class PDUserInfoBean implements Serializable {

    private long id;
    private String iconUrl;
    private String userName;
    private String password;
    private Date birthDay;
    private int gender;
    private int attribute;

    public PDUserInfoBean() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }
}
