package com.ioutime.servlet;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.dao.MySqlDbcpPool;
import com.ioutime.dao.user.SelectUser;
import com.ioutime.entity.User;
import com.ioutime.methods.ReqBody;
import com.ioutime.methods.RespBody;
import com.ioutime.util.BcryptUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/17 9:24
 */

public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*获取数据*/
        ReqBody reqBody = new ReqBody();
        JSONObject jsonObject = reqBody.getBody(req);
        String username =(String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");
        /*数据库处理*/
        SelectUser selectUser = new SelectUser();
        User user = selectUser.queryUser(username);
        if(user != null){
            RespBody.response(resp,"400","用户名重复","");
            System.out.println("用户名重复");
        }
        else{
            /*添加用户*/
            MySqlDbcpPool dbcpPool = new MySqlDbcpPool();
            Connection connection = null;
            try {
                connection = dbcpPool.getMysqlConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String sql = "insert into login_accounts (username,password) values"+
                    "( '"+username+"', '"+BcryptUtil.ciphertext(password)+"')";

            try {
                int i = dbcpPool.change(connection, sql);
                dbcpPool.closeConnection(connection, null, null);
                if(i !=0 ){
                    RespBody.response(resp,"200","注册成功",username);
                    System.out.println("注册成功");
                }else{
                    RespBody.response(resp,"400","注册成败","");
                    System.out.println("注册成败");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
