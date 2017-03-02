package com.vipe.service.mybatis;

import com.vipe.common.entity.AuthData;
import com.vipe.service.context.ServiceContext;
import com.vipe.service.entity.BaseEntity;
import com.vipe.service.entity.MasterEntity;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.Date;
import java.util.Properties;

/**
 * Created by zengjiyang on 2016/7/21.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class SetDefaultValueInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if(ServiceContext.getCurrent().getAuthData()==null)
            return invocation.proceed();
        AuthData authData = ServiceContext.getCurrent().getAuthData();
        if (authData == null) {
            return invocation.proceed();
        }
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object arg = args[1];
        if (arg instanceof BaseEntity) {
            if (mappedStatement.getSqlCommandType() == SqlCommandType.INSERT) {
                ((BaseEntity) arg).setCreateBy(authData.getUserId());
                ((BaseEntity) arg).setCreateAt(new Date());
                if (arg instanceof MasterEntity) {
                    ((MasterEntity) arg).setState(1);
                }
            } else if (mappedStatement.getSqlCommandType() == SqlCommandType.UPDATE) {
                if (arg instanceof MasterEntity) {
                    ((MasterEntity) arg).setModifyBy(authData.getUserId());
                    ((MasterEntity) arg).setModifyAt(new Date());
                }
            }
        }

        Object result = invocation.proceed();
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
