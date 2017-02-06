package org.panda.pdaily.dao;

import java.util.List;

/**
 * 基类
 *
 * Created by fangzanpan on 2017/2/6.
 */
public interface PDIBaseDao<T> {

    int add(T bean);
    int update(T bean);
    int delete(Long id);
    T findById(Long id);
    List<T> findBeans();
}
