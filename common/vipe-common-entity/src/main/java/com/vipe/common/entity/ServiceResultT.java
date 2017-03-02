package com.vipe.common.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/18 0018.
 */
public class ServiceResultT<T> extends ServiceResult implements Serializable {
    /**
     * 返回回来的结果实体（集合）
     */
//    @JSONField(name = "Result")
    private T result;

    /**
     * 获取返回回来的结果实体（集合）
     *
     * @return
     */
    public T getResult() {
        return result;
    }

    /**
     * 设置返回回来的结果实体（集合）
     *
     * @param result
     */
    public void setResult(T result) {
        this.result = result;
    }
}
