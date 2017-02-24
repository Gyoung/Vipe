package com.vipe.common.mybatis.support;

import oracle.sql.CLOB;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;

/**
 * Created by qiuyungen on 2016/3/12.
 */
public abstract class AbstractClobTypeHandler extends BaseTypeHandler<String> {

    protected abstract Connection warpConnection(Connection connection);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String s, JdbcType jdbcType) throws SQLException {
        try {
            if (s != null) {
                //prepareLob
                CLOB clob = CLOB.createTemporary(warpConnection(ps.getConnection()), true, CLOB.DURATION_SESSION);

                //callback.populateLob
                try {
                    clob.setString(i, s);
                } catch (Exception e) {
                    throw new SQLException(e);
                }
                ps.setClob(i, clob);
            } else {
                ps.setClob(i, (Clob) null);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }


/*  private String getClobAsString(Clob clob) throws SQLException {
        CLOB oracleCLOB = (CLOB) clob;
        return oracleCLOB.getSubString(1, oracleCLOB.getPrefetchedDataSize());
    }*/
    private String getClobAsString(Clob clob) throws SQLException {
        CLOB oracleCLOB = getOracleClob(clob);
        return ClobToString(oracleCLOB);
    }


    private String ClobToString(Clob clob) {
        String reString = "";
        Reader is = null;
        try {
            is = clob.getCharacterStream();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 得到流
        BufferedReader br = new BufferedReader(is);
        String s = null;
        try {
            s = br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        while (s != null) {
            //执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            try {
                s = br.readLine();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        reString = sb.toString();
        return reString;
    }


    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Clob rawClob = rs.getClob(columnName);
        CLOB clob = getOracleClob(rawClob);
        if (rs.wasNull())
            return null;
        return getClobAsString(clob);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Clob rawClob = rs.getClob(columnIndex);
        oracle.sql.CLOB clob = getOracleClob(rawClob);
        if (rs.wasNull())
            return null;
        return getClobAsString(clob);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Clob rawClob = cs.getClob(columnIndex);
        Clob clob = getOracleClob(rawClob);
        if (cs.wasNull())
            return null;
        return getClobAsString(clob);
    }

    private oracle.sql.CLOB getOracleClob(Clob rawClob){
        oracle.sql.CLOB clob = null;
       /* if(rawClob instanceof com.alibaba.druid.proxy.jdbc.ClobProxyImpl) {
            com.alibaba.druid.proxy.jdbc.ClobProxyImpl impl = (com.alibaba.druid.proxy.jdbc.ClobProxyImpl) rawClob;
            rawClob = impl.getRawClob(); // 获取原生的这个 Clob
        }*/
        clob = (CLOB) rawClob;
        return clob;
    }
}
