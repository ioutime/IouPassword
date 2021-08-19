package com.ioutime.servlet;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.dao.user.OtherOpt;
import com.ioutime.dao.user.Select;
import com.ioutime.entity.User;
import com.ioutime.util.BcryptUtil;
import com.ioutime.util.ReqBody;
import com.ioutime.util.RespBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/17 9:24
 */

public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        /*获取数据*/
        ReqBody reqBody = new ReqBody();
        JSONObject jsonObject = reqBody.getBody(req);
        String username =(String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");
        /*数据库处理*/
        Select select = new Select();
        User user = select.queryUser(username);
        if(user != null){
            RespBody.response(resp,"400","用户名重复","");
            System.out.println("用户名重复");
        }
        else{
            OtherOpt otherOpt = new OtherOpt();
            try {
                boolean i = otherOpt.addUser(username, BcryptUtil.ciphertext(password));
                if(i){
                    RespBody.response(resp,"200","注册成功",username);
                    System.out.println("注册成功");
                }else{
                    RespBody.response(resp,"400","注册成败","");
                    System.out.println("注册成败");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                RespBody.response(resp,"400","注册成败","");
                System.out.println("注册成败");
            }
        }

    }
}
