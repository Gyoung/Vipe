package com.vipe.service;

import com.vipe.common.entity.*;
import com.vipe.service.entity.MasterEntity;

import java.util.List;


/**
 * 内部服务基接口
 * Created by zengjiyang on 2016/1/25.
 */
public interface IBaseInService<T extends MasterEntity> {

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    ServiceResult add(AuthData authData, T entity);

    /**
     * 批量新增
     *
     * @param entities
     * @return
     */
    ServiceResult addList(AuthData authData, List<T> entities);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    ServiceResult deleteById(AuthData authData, String id);


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    ServiceResult deleteByIds(AuthData authData, String ids);

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    ServiceResult update(AuthData authData, T entity);

    /**
     * 批量更新
     *
     * @param entities
     * @return
     */
    ServiceResult updateList(AuthData authData, List<T> entities);

    /**
     * 获取单个对象
     *
     * @param id
     * @return
     */
    ServiceResultT<T> getById(AuthData authData, String id);


    /**
     * 返回所有对象 不分页
     *
     * @return
     */
    ServiceResultT<List<T>> getList(AuthData authData);


    /**
     * 分页查询
     *
     * @param queryPage
     * @return
     */
    ServiceResultT<PageList<T>> getList(AuthData authData, QueryPage queryPage, Class<T> cls);

}
