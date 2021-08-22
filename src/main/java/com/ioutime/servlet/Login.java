package com.ioutime.servlet;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.dao.user.Select;
import com.ioutime.entity.User;
import com.ioutime.util.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/15 18:07
 */

public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取json
        JSONObject jsonObject = new ReqBody().getBody(req);
        String username =(String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");

        //数据库处理,查询
        Select select = new Select();
        User user = select.queryUser(username);
        //响应
        if(user == null){
            RespBody.response(resp,"400","用户不存在","");
            System.out.println("用户不存在");
        }else{
            String dbPassword = user.getPassword();
            /*验证密码*/
            if(BcryptUtil.verify(password, dbPassword)){
                JwtUtil jwtUtil = new JwtUtil();
                /*生成token*/
                HashMap<String, String> map = new HashMap<>();
                map.put("uid",user.getUid().toString());
                map.put("username",user.getUsername());
                String token = jwtUtil.jwtgetToken(map);
                RespBody.response(resp,"200","登录成功",token);
                System.out.println("登录成功");
            }else{
                RespBody.response(resp,"400","密码错误","");
                System.out.println("登录失败");
            }
        }

    }
}
