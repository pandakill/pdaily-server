package org.panda.pdaily.dao;

import org.panda.pdaily.bean.PDTokenBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * token的dao类实现
 *
 * Created by fangzanpan on 2017/2/6.
 */
@Repository
public class PDTokenDaoimpl implements PDITokenDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(PDTokenBean bean) {
        return jdbcTemplate.update("INSERT INTO T_TOKEN(user_id, token, caller, create_date) VALUES (?, ?, ?, ?)",
                bean.getUserId(), bean.getToken(), bean.getCaller(), bean.getCreateDate());
    }

    public int update(PDTokenBean bean) {

        return jdbcTemplate.update("UPDATE T_TOKEN SET token = ? , create_date = ? WHERE (user_id = ? AND caller = ?)",
                new Object[]{bean.getToken(), bean.getCreateDate(), bean.getUserId(), bean.getCaller()});
    }

    public int delete(Long id) {
        return 0;
    }

    public PDTokenBean findById(Long id) {
        return null;
    }

    public List<PDTokenBean> findBeans() {
        return null;
    }

    public int deleteByUserIdAndCaller(long userId, String caller) {
        return jdbcTemplate.update("DELETE FROM T_TOKEN FROM (user_id = ? AND caller = ?)", new Object[]{userId, caller});
    }

    public PDTokenBean findByUserIdAndCaller(long userId, String caller) {
        List<PDTokenBean> list = jdbcTemplate.query("select * from T_TOKEN where (user_id = ? AND caller = ? )",
                new Object[]{userId, caller}, new BeanPropertyRowMapper(PDTokenBean.class));
        if(null != list && list.size()>0) {
            PDTokenBean token = list.get(0);
            return token;
        } else {
            return null;
        }
    }
}
