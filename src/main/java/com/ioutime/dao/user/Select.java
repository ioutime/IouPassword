package com.ioutime.dao.user;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.dao.MySqlDbcpPool;
import com.ioutime.entity.User;
import com.ioutime.entity.UserMsg;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/15 20:07
 */

public class Select {
    //根据用户名查寻用户
    public User queryUser(String username){
        MySqlDbcpPool dbcpPool = new MySqlDbcpPool();
        Connection connection = null;
        try{
            try {
                connection = dbcpPool.getMysqlConnection();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            User user = new User();
            Object[] params = {username};
            String sql = "select * from login_accounts where username = ?";
            ResultSet res = null;
            try {
                assert connection != null;
                res = dbcpPool.select(connection,sql,params);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                assert res != null;
                if(!res.next()){
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

            return user;
        }finally {
            dbcpPool.closeConnection(connection,null,null);
        }

    }

    public JSONObject queryMsg(int uid, String notes) throws SQLException, ClassNotFoundException {
        MySqlDbcpPool dbcpPool = new MySqlDbcpPool();
        Connection connection = dbcpPool.getMysqlConnection();
        ResultSet resultSet = null;
        try{
            String sql = "SELECT * FROM message WHERE uid = ? AND notes LIKE ?";
            Object[] params = {uid,"%"+notes+"%"};
            resultSet = dbcpPool.select(connection,sql,params);
            JSONObject jsonObject = new JSONObject();
            int i = 1;
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String notes1 = Base64.getEncoder().encodeToString(resultSet.getString("notes").getBytes(StandardCharsets.UTF_8));
                String msg = resultSet.getString("msg");
                UserMsg userMsg = new UserMsg();
                userMsg.setId(id);
                userMsg.setNotes(notes1);
                userMsg.setMsg(msg);
                String s = String.valueOf(i);
                jsonObject.put(s,userMsg);
                i++;
            }
            jsonObject.put("nums",String.valueOf(i-1));
            return jsonObject;
        }finally {
            dbcpPool.closeConnection(connection,null,resultSet);
        }

    }



}
