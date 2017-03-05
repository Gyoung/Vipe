package com.vipe.service.impl;



import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.github.pagehelper.PageInfo;

import com.vipe.common.entity.AuthData;
import com.vipe.common.entity.PageList;
import com.vipe.common.entity.QueryPage;
import com.vipe.common.util.StringUtil;
import com.vipe.service.entity.MasterEntity;
import com.vipe.service.exception.BizException;
import com.vipe.service.mapper.IMyMapper;
import com.vipe.service.util.SqlHelper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by qiuyungen on 2016/3/26.
 */
public abstract class BaseInServiceImpl<T extends MasterEntity, TMapper extends IMyMapper<T>> implements IBaseInService<T>, Mapper<T>, GenericService {

    private AuthData authData;

    private Class mapperInterface;

    @Autowired(required = true)
    @SuppressWarnings("all")
    private List<IMyMapper<T>> mapper;

    public BaseInServiceImpl() {
        this.mapperInterface = null;
        for (Class<?> interfaceClazz : this.getClass().getInterfaces()) {
            if (IMyMapper.class.isAssignableFrom(interfaceClazz)) {
                this.mapperInterface = interfaceClazz;
                break;
            }
        }
    }

    public TMapper getMapper() {
        if (this.mapperInterface == null)
            return null;
        for (IMyMapper<T> myMapper : mapper) {
            if (this.mapperInterface.isAssignableFrom(myMapper.getClass())) {
                return (TMapper) myMapper;
            }
        }
        return null;
    }

    @Override
    public AuthData getAuthData() {
        return this.authData;
    }

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }

    @Override
    public int add(T entity) throws Exception {
        if (entity.getCreateAt() == null)
            entity.setCreateAt(new Date());
        entity.setModifyAt(new Date());
        entity.setState(1);//状态设置为1
        return getMapper().insertSelective(entity);
    }

    @Override
    public int addList(List<T> entities) throws Exception {
        entities.forEach(e -> {
            if (StringUtil.isNullOrWhiteSpace(e.getId())) {
                //设置主键
                e.setId(StringUtil.getUUID());
            }
            if (e.getCreateAt() == null)
                e.setCreateAt(new Date());
            e.setModifyAt(new Date());
            e.setState(1);
        });
        return getMapper().addList(entities);
    }

    @Override
    public int deleteById(String id) throws Exception {
        if (StringUtil.isNullOrWhiteSpace(id))
            throw new BizException("删除参数不能为空");
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByIds(String ids) throws Exception {
        if (StringUtil.isNullOrWhiteSpace(ids))
            throw new BizException("批量删除参数不能为空");
        return getMapper().deleteByIds(ids.split(","));
    }

    public int deleteByIds(String[] ids) throws Exception {
        return getMapper().deleteByIds(ids);
    }

    @Override
    public int update(T entity) throws Exception {
        entity.setModifyAt(new Date());
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    @Override
    public boolean updateList(List<T> entities) throws Exception {
        getMapper().updateList(entities);
        return true;
    }

    @Override
    public T getById(String id) throws Exception {

        if (StringUtil.isNullOrWhiteSpace(id))
            throw new BizException("Id不能为空!");
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public List<T> getList() throws Exception {
        return getMapper().selectAll();
    }

    @Override
    public PageList<T> getList(QueryPage queryPage, Class<T> cls) throws Exception {
        PageList<T> pageList = new PageList<T>();
        Example example = new Example(cls);
        SqlHelper.startPage(queryPage);
        List<T> results = getMapper().selectByExample(example);
        PageInfo<T> pageInfo = new PageInfo<T>(results);
        pageList.setTotal((int) pageInfo.getTotal());
        pageList.setRows(results);
        return pageList;
    }

    //region //空函数，转接

    @Override
    public int deleteByExample(Object o) {
        return getMapper().deleteByExample(o);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return getMapper().deleteByPrimaryKey(o);
    }

    @Override
    public int delete(T t) {
        return getMapper().delete(t);
    }

    @Override
    public int insert(T t) {
        return getMapper().insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return getMapper().insertSelective(t);
    }

    @Override
    public List<T> selectAll() {
        return getMapper().selectAll();
    }

    @Override
    public List<T> selectByExample(Object o) {
        return getMapper().selectByExample(o);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object o, RowBounds rowBounds) {
        return getMapper().selectByExampleAndRowBounds(o, rowBounds);
    }

    @Override
    public T selectByPrimaryKey(Object o) {
        return getMapper().selectByPrimaryKey(o);
    }

    @Override
    public int selectCountByExample(Object o) {
        return getMapper().selectCountByExample(o);
    }

    @Override
    public int selectCount(T t) {
        return getMapper().selectCount(t);
    }

    @Override
    public List<T> select(T t) {
        return getMapper().select(t);
    }

    @Override
    public T selectOne(T t) {
        return getMapper().selectOne(t);
    }

    @Override
    public List<T> selectByRowBounds(T t, RowBounds rowBounds) {
        return getMapper().selectByRowBounds(t, rowBounds);
    }

    @Override
    public int updateByExample(@Param("record") T t, @Param("example") Object o) {
        return getMapper().updateByExample(t, o);
    }

    @Override
    public int updateByExampleSelective(@Param("record") T t, @Param("example") Object o) {
        return getMapper().updateByExampleSelective(t, o);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return getMapper().updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return getMapper().updateByPrimaryKeySelective(t);
    }

    @Override
    public Object $invoke(String s, String[] strings, Object[] objects) throws GenericException {
        return null;
    }

    public void dispose(Map<String, Object> map) {
        map.clear();
        map = null;
    }

    //endregion
}
