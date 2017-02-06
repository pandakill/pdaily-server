package org.panda.pdaily.service;


import java.util.List;

/**
 * 基本包含了数据库的增删改查操作
 *
 * Created by fangzanpan on 2017/2/6.
 */
public interface PDIBaseDBService<T> {
    int add(T bean);
    int update(T bean);
    int deleteById(Long id);
    T findById(Long id);
    List<T> findBeanList();
}
