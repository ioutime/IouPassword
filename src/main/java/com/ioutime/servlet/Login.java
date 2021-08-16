package com.ioutime.servlet;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.dao.date.OptTables;
import com.ioutime.dao.user.RedisOpt;
import com.ioutime.dao.user.SelectUser;
import com.ioutime.entity.User;
import com.ioutime.methods.ReqBody;
import com.ioutime.methods.RespBody;
import com.ioutime.util.BcryptUtil;
import com.ioutime.util.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/15 18:07
 */

public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取json
        ReqBody reqBody = new ReqBody();
        JSONObject jsonObject = reqBody.getBody(req);
        String username =(String) jsonObject.get("name");
        String password = (String) jsonObject.get("password");

        //数据库处理,查询
        SelectUser selectUser = new SelectUser();
        User user = selectUser.queryUser(username);
        //响应
        RespBody respBody = new RespBody();
        if(user == null){
            respBody.response(resp,"400","用户不存在","");
            System.out.println("用户不存在");
        }else{
            String dbPassword = user.getPassword();
            /*验证密码*/
            if(BcryptUtil.verify(password, dbPassword)){
                RedisOpt redisOpt = new RedisOpt();
                JwtUtil jwtUtil = new JwtUtil();
                /*生成token*/
                String token = jwtUtil.jwtgetToken(user.getUsername(), user.getUid());
                /*存储token*/
                redisOpt.storage(user.getUsername(),token);
                /*判断是否有该用户数据*/
                OptTables optTables = new OptTables();
                try {
                    optTables.creatTable(user.getUsername());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                respBody.response(resp,"200","登录成功",user.getUsername());
                System.out.println("登录成功");
            }else{
                respBody.response(resp,"400","密码错误","");
                System.out.println("登录失败");
            }
        }

    }
}
