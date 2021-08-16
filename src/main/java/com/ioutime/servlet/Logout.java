package com.ioutime.servlet;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.dao.user.RedisOpt;
import com.ioutime.methods.ReqBody;
import com.ioutime.methods.RespBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/16 17:12
 */

public class Logout extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取json
        ReqBody reqBody = new ReqBody();
        JSONObject jsonObject = reqBody.getBody(req);
        String username = jsonObject.getString("username");

        //删除token
        RedisOpt redisOpt = new RedisOpt();
        Long value = redisOpt.delValue(username);
        if(value == 0){
            RespBody.response(resp,"200","已注销","");
            System.out.println("已注销");
        }else {
            RespBody.response(resp,"200","注销成功","");
            System.out.println("注销成功");
        }
    }
}
