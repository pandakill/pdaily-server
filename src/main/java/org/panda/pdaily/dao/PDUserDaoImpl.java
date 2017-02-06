package org.panda.pdaily.dao;

import org.panda.pdaily.bean.PDUserInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangzanpan on 2017/1/4.
 */
@Repository
public class PDUserDaoImpl implements PDIUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(PDUserInfoBean user) {
        return jdbcTemplate.update("INSERT INTO T_USER_INFO(user_name, icon_url) VALUES (?, ?)",
                user.getUserName(), user.getIconUrl());
    }

    public int update(PDUserInfoBean user) {
        return jdbcTemplate.update("UPDATE T_USER_INFO SET user_name = ? , icon_url = ? WHERE id = ?",
                new Object[]{user.getUserName(), user.getIconUrl(), user.getId()});
    }

    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM T_USER_INFO FROM id = ?", id);
    }

    public PDUserInfoBean findById(Long id) {
        List<PDUserInfoBean> list = jdbcTemplate.query("select * from T_USER_INFO where id = ?",
                new Object[]{id}, new BeanPropertyRowMapper(PDUserInfoBean.class));
        if(null != list && list.size()>0) {
            PDUserInfoBean user = list.get(0);
            return user;
        } else {
            return null;
        }
    }

    public List<PDUserInfoBean> findBeans() {
        List<PDUserInfoBean> list = jdbcTemplate.query("select * from T_USER_INFO", new Object[]{},
                new BeanPropertyRowMapper(PDUserInfoBean.class));
        return list;
    }
}
