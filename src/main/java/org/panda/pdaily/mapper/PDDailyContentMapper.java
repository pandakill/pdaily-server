package org.panda.pdaily.mapper;

import org.panda.pdaily.bean.PDDailyContentBean;

import java.util.List;

/**
 * mybatis的映射接口
 *
 * Created by fangzanpan on 2017/2/6.
 */
public interface PDDailyContentMapper {

    List<PDDailyContentBean> getDailyContents();

    PDDailyContentBean getDailyContentById(long id);

    List<PDDailyContentBean> getDailyContentsByUserId(long userId);
}
