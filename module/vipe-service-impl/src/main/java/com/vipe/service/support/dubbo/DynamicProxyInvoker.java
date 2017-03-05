package com.vipe.service.support.dubbo;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker;
import com.vipe.common.entity.AuthData;
import com.vipe.common.entity.ServiceResult;
import com.vipe.common.function.Function;
import com.vipe.service.annotation.ErrorMessage;
import com.vipe.service.annotation.IgnoreAuthrzation;
import com.vipe.service.annotation.WithResult;
import com.vipe.service.entity.ErrorCode;
import com.vipe.service.impl.IBaseInService;
import com.vipe.service.security.SystemCredential;
import com.vipe.service.util.SafeExecuteUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuyungen on 2016/3/26.
 */
public class DynamicProxyInvoker<T> extends AbstractProxyInvoker<T> {

    private com.alibaba.dubbo.config.annotation.Service dubboService;

    public DynamicProxyInvoker(T proxy, Class<T> type, URL url) {
        super(proxy, type, url);
    }

    private final static Class<?>[] makeClass(final Class<?>[] parameterTypes) {
        Class<?>[] types;
        if (parameterTypes.length != 0) {
            List<Class<?>> parameterTypeList = new ArrayList<Class<?>>();
            boolean first = true;
            int offset = 0;
            for (Class<?> parameterType : parameterTypes) {
                if (first && parameterType == AuthData.class) {
                    offset = 1;
                    first = false;
                    continue;
                }
                parameterTypeList.add(parameterType);
            }
            types = new Class<?>[parameterTypes.length - offset];
            parameterTypeList.toArray(types);
            parameterTypeList.clear();
            parameterTypeList = null;
        } else {
            types = parameterTypes;
        }
        return types;
    }

    private final static Object[] makeArgument(final Object[] arguments, final boolean isFirstSystemDataClass) {
        Object[] objects;
        if (arguments.length != 0) {
            List<Object> argumentList = new ArrayList<Object>();
            boolean first = true;
            for (Object argument : arguments) {
                if (first && isFirstSystemDataClass) {
                    first = false;
                    continue;
                }
                argumentList.add(argument);
            }
            objects = new Object[arguments.length - (isFirstSystemDataClass ? 1 : 0)];
            argumentList.toArray(objects);
            argumentList.clear();
            argumentList = null;
        } else {
            objects = arguments;
        }
        return objects;
    }

    // 解出真实异常对象
    private static Object execute(Function<Object> invoker) throws Exception {
        try {
            //执行隐私保护
            Object result = invoker.apply();
            return result;
        } catch (Exception ex) {
            throw (Exception) ex.getCause();
        }
    }

    private Class getForeignInterface() {


        return this.dubboService.interfaceClass();
    }

    private void buildDubboService(Class proxy) {
        if (this.dubboService == null) {
            Class clazz;
            if (proxy.getName().contains("$$")) {
                clazz = proxy.getSuperclass();
            } else {
                clazz = proxy;
            }
            this.dubboService = (Service) clazz.getAnnotation(Service.class);
        }
    }

    @Override
    protected Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable {
        buildDubboService(proxy.getClass());

        Method foreignMethod = getForeignInterface().getMethod(methodName, parameterTypes);

        Object instance = proxy;
        //处理webservice
        if (this.getUrl().getProtocol().equals("webservice")) {
            Method method = instance.getClass().getMethod(methodName, parameterTypes);
            ErrorMessage[] errors = method.getAnnotationsByType(ErrorMessage.class);
            return SafeExecuteUtil.executeWebService(() -> method.invoke(instance, arguments), errors);
        }

        // arguments
        final boolean isFirstSystemDataClass = parameterTypes.length != 0 && parameterTypes[0] == AuthData.class;
        Object firstData = isFirstSystemDataClass ? arguments[0] : null;
        final Object[] objects = makeArgument(arguments, isFirstSystemDataClass);

        IgnoreAuthrzation ignore = foreignMethod.getAnnotation(IgnoreAuthrzation.class);
        if (ignore == null) {
            if (isFirstSystemDataClass) {
                AuthData token = (AuthData) firstData;
                if (token == null) {
                    ServiceResult result = new ServiceResult();
                    result.addMessage(ErrorCode.SESSION_EXPIRED, "Session过期,请重新登录!");
                    return result;
                }
                ((IBaseInService) instance).setAuthData(token);
                // TODO: 忽略认证以后必须删除 2016/05/26
                if (!token.isIgnoreCredential()) {
                    boolean isAuth = SystemCredential.checkCredential(token.getUserId(), token.getUserName(), token.getCredential());
                    if (!isAuth) {
                        ServiceResult result = new ServiceResult();
                        result.addMessage(ErrorCode.SESSION_EXPIRED, "Session过期,请重新登录!");
                        return result;
                    }
                }
            } else {
                ServiceResult result = new ServiceResult();
                result.addMessage(ErrorCode.DEFAULT, "方法的第一个参数必须为SystemData类型");
                return result;
            }
        }


        final Class<?>[] types = makeClass(parameterTypes);
        Method method = instance.getClass().getMethod(methodName, types);
        ErrorMessage[] errors = method.getAnnotationsByType(ErrorMessage.class);
//        System.out.println("execute method:" + methodName);
        if (errors == null || errors.length == 0) {
            errors = getErrorAnnotations(instance, methodName, types);
        }

        final boolean withResult = method.getAnnotation(WithResult.class) == null ?
                getWithResult(instance, methodName, types) != null : true;

        // start service context
       /* if (instance instanceof IService) {
            ServiceContext.getCurrent().setService((IService) instance);
        }*/

        if (method.getReturnType() == Boolean.class && !withResult) {
            return SafeExecuteUtil.execute(() ->
                    (Boolean) execute(() -> method.invoke(instance, objects)), errors
            );
        }
        if (method.getReturnType() == int.class && !withResult) {
            return SafeExecuteUtil.execute(() ->
                    (((int) execute(() -> (method.invoke(instance, objects)))) >= 0), errors
            );
        }
        return SafeExecuteUtil.execute0(() ->
                execute(() -> method.invoke(instance, objects)), errors
        );
    }

    private ErrorMessage[] getErrorAnnotations(Object instance, String methodName, Class<?>[] types) throws Exception {
        ErrorMessage[] errors = null;
        //方法名中有$$说明是代理类
        if (instance.getClass().getName().contains("$$")) {
            Class<?> superClass = instance.getClass().getSuperclass();
            errors = superClass.getMethod(methodName, types)
                    .getAnnotationsByType(ErrorMessage.class);
            if (errors == null || errors.length == 0) {
                errors = superClass.getAnnotationsByType(ErrorMessage.class);
            }
        }
        if (errors == null || errors.length == 0) {
            errors = instance.getClass().getAnnotationsByType(ErrorMessage.class);
        }
        return errors;
    }

    private WithResult getWithResult(Object instance, String methodName, Class<?>[] types) throws Exception {
        WithResult withResult = null;
        //方法名中有$$说明是代理类
        if (instance.getClass().getName().contains("$$")) {
            Class<?> superClass = instance.getClass().getSuperclass();
            withResult = superClass.getMethod(methodName, types)
                    .getAnnotation(WithResult.class);

        }
        return withResult;
    }
}
