package com.vipe.common.mybatis.support;

import org.apache.commons.dbcp.DelegatingConnection;

import java.sql.Connection;

/**
 * Created by qiuyungen on 2016/3/28.
 */
public class ClobTypeHandler extends AbstractClobTypeHandler {
    @Override
    protected Connection warpConnection(Connection connection) {
        DelegatingConnection delegatingConnection = (DelegatingConnection) connection;
        if (delegatingConnection == null)
            throw new NullPointerException();
        return delegatingConnection.getDelegate();
    }
}
