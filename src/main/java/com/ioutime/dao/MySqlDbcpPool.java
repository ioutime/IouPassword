package com.ioutime.dao;

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
        Connection connection = dataSource.getConnection();
        return connection;
    }

    //查询
    public ResultSet select(Connection connection,String sql) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    //增,删,改
    public int change(Connection connection, String sql) throws SQLException {
        Statement statement = connection.createStatement();
        int i = statement.executeUpdate(sql);
        return i;
    }



    //关闭连接
    public boolean closeConnection(Connection connection,Statement statement,ResultSet resultSet){
        boolean flag = true;
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                resultSet = null;
                flag = false;
            }
        }
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                statement = null;
                flag = false;
            }
        }
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                connection = null;
                flag = false;
            }
        }
        return flag;
    }

}
