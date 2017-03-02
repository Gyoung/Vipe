package com.vipe.service.mapper;

import com.vipe.service.entity.BaseEntity;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by zengjiyang on 2016/1/25.
 */
public interface IMyMapper<T extends BaseEntity> extends Mapper<T> {

    int addList(List<T> entities) throws Exception;

    boolean updateList(List<T> entities) throws Exception;

    int deleteByIds(String[] ids) throws Exception;

}
