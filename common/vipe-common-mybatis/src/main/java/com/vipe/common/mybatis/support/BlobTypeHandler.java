package com.vipe.common.mybatis.support;

import org.apache.commons.dbcp.DelegatingConnection;
import org.apache.commons.dbcp.PoolableConnection;

import java.sql.Connection;

/**
 * Created by qiuyungen on 2016/3/28.
 */
public class BlobTypeHandler extends AbstractBlobTypeHandler {
    @Override
    protected Connection warpConnection(Connection connection) {
        return ((PoolableConnection)((DelegatingConnection)connection).getDelegate()).getDelegate();
    }
}
