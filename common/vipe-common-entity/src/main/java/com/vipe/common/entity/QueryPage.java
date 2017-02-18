package com.vipe.common.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/18 0018.
 */
public class QueryPage implements Serializable {

    //    @JSONField(name = "PageNum")
    private int pageNum;
    //    @JSONField(name = "PageSize")
    private int pageSize;
    //    @JSONField(name = "OrderBy")
    private String orderBy;

    private SortOrder sortOrder;

    public QueryPage() {
        this.pageNum = 1;
        this.pageSize = 20;
    }

    /**
     * @param pageNum  第几页
     * @param pageSize 页面大小
     */
    public QueryPage(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    /**
     * @param pageNum  第几页
     * @param pageSize 页面大小
     * @param orderBy  排序字段
     */
    public QueryPage(int pageNum, int pageSize, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.sortOrder = SortOrder.DESC;
    }

    public QueryPage(int pageNum, int pageSize, String orderBy, SortOrder sortOrder) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.sortOrder = sortOrder;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}