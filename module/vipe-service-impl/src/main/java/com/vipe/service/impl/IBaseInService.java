package com.vipe.service.impl;


import com.vipe.common.entity.AuthData;
import com.vipe.common.entity.PageList;
import com.vipe.common.entity.QueryPage;
import com.vipe.service.entity.MasterEntity;

import java.util.List;


/**
 * 内部服务基接口
 * Created by qiuyungen on 2016/3/26.
 */
public interface IBaseInService<T extends MasterEntity> {

    /**
     * 获取用户信息
     *
     * @return
     */
    AuthData getAuthData();

    /**
     * 设置用户信息
     *
     * @param authData
     */
    void setAuthData(AuthData authData);

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    int add(T entity) throws Exception;

    /**
     * 批量新增
     *
     * @param entities
     * @return
     */
    int addList(List<T> entities) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteById(String id) throws Exception;


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(String ids) throws Exception;

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    int update(T entity) throws Exception;

    /**
     * 批量更新
     *
     * @param entities
     * @return
     */
    boolean updateList(List<T> entities) throws Exception;

    /**
     * 获取单个对象
     *
     * @param id
     * @return
     */
    T getById(String id) throws Exception;


    /**
     * 返回所有对象 不分页
     *
     * @return
     */
    List<T> getList() throws Exception;


    /**
     * 分页查询
     *
     * @param queryPage
     * @return
     */
    PageList<T> getList(QueryPage queryPage, Class<T> cls) throws Exception;

}
