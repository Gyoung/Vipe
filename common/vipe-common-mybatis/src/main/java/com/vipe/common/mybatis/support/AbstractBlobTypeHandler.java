package com.vipe.common.mybatis.support;

import oracle.sql.BLOB;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.OutputStream;
import java.sql.*;

/**
 * Created by qiuyungen on 2016/3/11.
 */
public abstract class AbstractBlobTypeHandler extends BaseTypeHandler<byte[]> {
    // see http://stackoverflow.com/questions/17055558/select-a-blob-column-from-oracle-db-using-mybatis

    protected abstract Connection warpConnection(Connection connection);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, byte[] bytes, JdbcType jdbcType) throws SQLException {
        // see setBlobAsBytes method from https://jira.spring.io/secure/attachment/11851/OracleLobHandler.java
        try {
            if (bytes != null) {
                //prepareLob
                BLOB blob = BLOB.createTemporary(warpConnection(ps.getConnection()), true, BLOB.DURATION_SESSION);

                //callback.populateLob
                OutputStream os = blob.setBinaryStream(0);
                try {
                    os.write(bytes);
                } catch (Exception e) {
                    throw new SQLException(e);
                } finally {
                    try {
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();//ignore
                    }
                }
                ps.setBlob(i, blob);
            } else {
                ps.setBlob(i, (Blob) null);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    /**
     * see getBlobAsBytes method from https://jira.spring.io/secure/attachment/11851/OracleLobHandler.java
     */
    private byte[] getBlobAsBytes(BLOB blob) throws SQLException {

        //initializeResourcesBeforeRead
        if (!blob.isTemporary()) {
            blob.open(BLOB.MODE_READONLY);
        }

        //read
        byte[] bytes = blob.getBytes(1L, (int) blob.length());

        //releaseResourcesAfterRead
        if (blob.isTemporary()) {
            blob.freeTemporary();
        } else if (blob.isOpen()) {
            blob.close();
        }

        return bytes;
    }

    @Override
    public byte[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        BLOB blob = (BLOB) rs.getBlob(columnName);
        if (rs.wasNull())
            return new byte[0];
        return getBlobAsBytes(blob);
    }

    @Override
    public byte[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        BLOB blob = (BLOB) rs.getBlob(columnIndex);
        if (rs.wasNull())
            return new byte[0];
        return getBlobAsBytes(blob);
    }

    @Override
    public byte[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        BLOB blob = (BLOB) cs.getBlob(columnIndex);
        if (cs.wasNull())
            return new byte[0];
        return getBlobAsBytes(blob);
    }
}
