package com.vipe.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/18 0018.
 */
public class PageList<T> implements Serializable {
    /**
     * 分页列表条数
     */
    private int total;
    /**
     * 行内容对象
     */
    private List<T> rows;

    /**
     * 获取分页总条数
     *
     * @return
     */
    public int getTotal() {
        return total;
    }

    /**
     * 设置分页总条数
     *
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 获取行内容对象
     *
     * @return
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置行内容对象
     *
     * @param rows
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
