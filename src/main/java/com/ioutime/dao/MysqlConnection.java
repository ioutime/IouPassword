package com.ioutime.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/15 18:32
 */

public class MysqlConnection {

    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static {
        Properties properties = new Properties();
        InputStream stream = MysqlConnection.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    //连接数据库
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url+"useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",username,password);
        return connection;
    }

    //查询
    public ResultSet select(Connection connection, String sql) throws SQLException {
        if(connection==null) return null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    //增,删,改
    public int change(Connection connection, String sql) throws SQLException {
        if(connection==null) return 0;
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
