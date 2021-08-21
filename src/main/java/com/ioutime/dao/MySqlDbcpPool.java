package com.ioutime.dao;

import com.ioutime.util.StartThread;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/16 22:31
 */

public class MySqlDbcpPool {
    private static DataSource dataSource = null;

    static {
        Properties properties = new Properties();
        InputStream stream = MySqlDbcpPool.class.getClassLoader().getResourceAsStream("dbcp.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建数据源
        try {
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public  Connection getMysqlConnection() throws ClassNotFoundException, SQLException {
        return dataSource.getConnection();
    }

    //查询
    public ResultSet select(Connection connection,String sql,Object[] params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i <= params.length; i++) {
            preparedStatement.setObject(i,params[i-1]);
        }
        return preparedStatement.executeQuery();
    }

    //增,删,改
    public int change(Connection connection, String sql,Object[] params) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) {
                preparedStatement.setObject(i,params[i-1]);
            }
            int i = preparedStatement.executeUpdate();
            return i;
        }finally {
            closeConnection(connection,preparedStatement,null);
        }

    }



    //关闭连接
    public void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
