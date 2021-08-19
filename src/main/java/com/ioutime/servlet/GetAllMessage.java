package com.ioutime.servlet;

import com.ioutime.dao.user.Select;
import com.ioutime.entity.UserMsg;
import com.ioutime.util.JwtUtil;
import com.ioutime.util.RespBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
* 
* @author ioutime
* @date 2021/8/16 17:05 
* @version 1.0
*/ 

public class GetAllMessage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        int uid = JwtUtil.getInfo(token);
        Select select = new Select();
        List<UserMsg> list;
        try {
            list = select.queryMsg(uid,"" );
            RespBody.response(resp,"200","查询成功",list);
            System.out.println("查询成功");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            RespBody.response(resp,"400","查询失败","");
            System.out.println("查询失败");
        }

    }
}
