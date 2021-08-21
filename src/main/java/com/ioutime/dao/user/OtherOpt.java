package com.ioutime.dao.user;

import com.ioutime.dao.MySqlDbcpPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/19 12:07
 */

public class OtherOpt {

    /*添加用户*/
    public boolean addUser(String username,String password) throws SQLException, ClassNotFoundException {
        MySqlDbcpPool dbcpPool = new MySqlDbcpPool();
        Connection connection = dbcpPool.getMysqlConnection();
        try {
            String sql = "insert into login_accounts (username,password) values ( ? , ? )";
            Object[] params = { username, password};
            int i = dbcpPool.change(connection, sql, params);
            return i != 0;
        }finally {
            dbcpPool.closeConnection(connection,null,null);
        }

    }

    /*增*/
    public boolean add(Object[] params) throws SQLException, ClassNotFoundException {
        MySqlDbcpPool dbcpPool = new MySqlDbcpPool();
        Connection connection = dbcpPool.getMysqlConnection();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "insert into message (uid,notes,msg) values (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,(int) params[0]);
            preparedStatement.setObject(2,params[1]);
            preparedStatement.setObject(3,params[2]);
            int i = preparedStatement.executeUpdate();
            return i != 0;
        }finally {
            dbcpPool.closeConnection(connection,preparedStatement,null);
        }


    }

    /*删*/
    public boolean delete(int id,int uid) throws SQLException, ClassNotFoundException {
        MySqlDbcpPool dbcpPool = new MySqlDbcpPool();
        Connection connection = dbcpPool.getMysqlConnection();
        String sql = "delete from message where  id = ? and uid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        preparedStatement.setInt(2,uid);
        int i = preparedStatement.executeUpdate();
        dbcpPool.closeConnection(connection,preparedStatement,null);
        return i != 0;
    }


}
