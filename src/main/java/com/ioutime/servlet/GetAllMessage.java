package com.ioutime.servlet;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.dao.user.Select;
import com.ioutime.entity.UserMsg;
import com.ioutime.util.JwtUtil;
import com.ioutime.util.ReqBody;
import com.ioutime.util.RespBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
* 
* @author ioutime
* @date 2021/8/16 17:05 
* @version 1.0
*/ 

public class GetAllMessage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject body = new ReqBody().getBody(req);
        String token = (String) body.get("token");
        int uid = JwtUtil.getInfo(token);
        if(uid == -1){
            RespBody.response(resp,"400","身份认证失败","");
        }else {
            Select select = new Select();
            JSONObject jsonObject;
            try {
                jsonObject = select.queryMsg(uid,"" );
                RespBody.response(resp,"200","查询成功",jsonObject);
                System.out.println("查询成功");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                RespBody.response(resp,"400","查询失败","");
                System.out.println("查询失败");
            }
        }
    }
}
