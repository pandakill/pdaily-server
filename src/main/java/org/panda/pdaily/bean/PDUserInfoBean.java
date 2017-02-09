package org.panda.pdaily.bean;

import java.io.Serializable;

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
    private long birthday;
    private int gender;
    private int attribute;
    private String mobile;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "PDUserInfoBean{" +
                "id=" + id +
                ", iconUrl='" + iconUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", attribute=" + attribute +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
