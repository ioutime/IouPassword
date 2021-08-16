package com.ioutime.dao.user;

import com.ioutime.dao.MySqlDbcpPool;
import com.ioutime.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/15 20:07
 */

public class SelectUser {
    //根据用户名查寻用户
    public User queryUser(String username){
        MySqlDbcpPool dbcpPool = new MySqlDbcpPool();
        Connection connection = null;
        try {
            connection = dbcpPool.getMysqlConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User user = new User();
        String sql = "select * from login_accounts where username = '"+username+"'";
        ResultSet res = null;
        try {
            res = dbcpPool.select(connection,sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(res.next() == false){
                return null;
            }else {
                String password = res.getString("password");
                user.setPassword(password);
                user.setUid(res.getInt("uid"));
                user.setUsername(res.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbcpPool.closeConnection(connection,null,null);
        return user;
    }
}
