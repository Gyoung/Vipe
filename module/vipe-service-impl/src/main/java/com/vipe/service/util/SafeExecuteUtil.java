package com.vipe.service.util;

import com.vipe.common.entity.ServiceResult;
import com.vipe.common.entity.ServiceResultT;
import com.vipe.common.function.Function;
import com.vipe.service.annotation.ErrorMessage;
import com.vipe.service.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * Created by zhangwenbin on 2016/2/29.
 */
public final class SafeExecuteUtil {

    private static final Logger logger = LoggerFactory.getLogger(SafeExecuteUtil.class);

    public static ServiceResult execute(Function<Boolean> invoker, ErrorMessage[] errors) {
        ServiceResult executeResult = new ServiceResult();
        try {
            executeResult.setIsSuccess(invoker.apply());
        } catch (BizException ex) {
            //日志消息
            setErrorMessage(errors, executeResult, ex);
            logger.error("方法执行报错:",ex);
        } catch (Exception e) {
            //错误消息
            executeResult.setIsSuccess(false);
           // executeResult.addLogData(e.toString());// TODO: 2017/1/21 先加空字符串,安全防范
            executeResult.addLogData("");
            executeResult.addLogData(getStackMsg(e));
            logger.error("方法执行报错:",e);
        }
        return executeResult;
    }


    /**
     * 处理webservice服务  todo:错误返回处理 统一生成XML？
     *
     * @param invoker
     * @param errors
     * @return
     */
    public static String executeWebService(Function<Object> invoker, ErrorMessage[] errors) {
        String result;
        try {
            result = (String) invoker.apply();
        } catch (BizException ex) {
            //日志消息
            result = ex.getMessage();
            logger.error("方法执行报错:",ex);
        } catch (Exception e) {
            //错误消息
            result = e.getMessage();
            logger.error("方法执行报错:",e);
        }
        return result;
    }

    public static <R> ServiceResultT<R> execute0(Function<Object> invoker, ErrorMessage[] errors) {
        ServiceResultT<R> executeResult = new ServiceResultT<R>();
        try {
            R result = (R) invoker.apply();
            executeResult.setResult(result);
            executeResult.setIsSuccess(result != null);
        } catch (BizException ex) {
            //日志消息
            setErrorMessage(errors, executeResult, ex);
            logger.error("方法执行报错:",ex);
        } catch (Exception e) {
            executeResult.setIsSuccess(false);
            executeResult.addLogData("");

            logger.error("方法执行报错:",e);
        }
        return executeResult;
    }

    private static void setErrorMessage(ErrorMessage[] errors, ServiceResult result, BizException ex) {
        if (errors != null) {
            for (ErrorMessage er : errors) {
                if (er.code().equals(ex.getCode())) {
                    result.addMessage(er.code(), er.message());
                    result.setIsSuccess(false);
                    break;
                }
            }
        } else {
            result.addMessage("", ex.getMessage());
            result.setIsSuccess(false);
        }
    }

    public static String getBusinessExceptionMsg(BizException e, Class<?> invokeClass) {
        String msg = "";
        ErrorMessage[] errorMessages = invokeClass.getAnnotationsByType(ErrorMessage.class);
        if (null == errorMessages || errorMessages.length == 0) {
            errorMessages = invokeClass.getSuperclass().getAnnotationsByType(ErrorMessage.class);
        }
        if (errorMessages != null) {
            for (ErrorMessage er : errorMessages) {
                if (er.code().equals(e.getCode())) {
                    msg =er.message();// MessageFormat.format(er.message(), e.getArguments());
                    break;
                }
            }
        }
        return msg;
    }

    public static String getStackMsg(Throwable e) {

        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            String msg = element.toString();
            if (msg.startsWith("com.zoe")) {
                sb.append(element.toString() + "\n");
            }
        }
        return sb.toString();
    }

    public static <R> ServiceResultT<R> executeExternal(Function<Object> invoker, Class<?> classis) {
        ErrorMessage[] errors = classis.getAnnotationsByType(ErrorMessage.class);
        return execute0(invoker, errors);
    }
}

