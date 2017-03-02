package com.vipe.service.util;

import com.github.pagehelper.PageHelper;
import com.vipe.common.entity.QueryPage;
import com.vipe.common.entity.SortOrder;

/**
 * Created by zengjiyang on 2016/3/25.
 */
public final class SqlHelper {

    public static void startPage(QueryPage queryPage) {
        if (queryPage.getOrderBy() != null) {

            String order = queryPage.getOrderBy() + " " + (queryPage.getSortOrder() == null ? SortOrder.ASC : queryPage.getSortOrder());
            PageHelper.startPage(queryPage.getPageNum(), queryPage.getPageSize(), order);
        } else {
            //默认按照时间倒序
            PageHelper.startPage(queryPage.getPageNum(), queryPage.getPageSize(), " CREATE_AT DESC ");
        }
    }

    public static void startPageNoSort(QueryPage queryPage) {
        PageHelper.startPage(queryPage.getPageNum(), queryPage.getPageSize());
    }
}
