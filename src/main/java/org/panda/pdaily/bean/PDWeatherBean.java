package org.panda.pdaily.bean;

import java.io.Serializable;

/**
 * 天气的数据库实体类
 *
 * Created by fangzanpan on 2017/2/8.
 */
public class PDWeatherBean implements Serializable {

    private long id;
    private String name;
    private String iconUrl;
    private int isPublic;
    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PDWeatherBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", isPublic=" + isPublic +
                ", userId=" + userId +
                '}';
    }
}
