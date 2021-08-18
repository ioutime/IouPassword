package com.ioutime.servlet;

import com.ioutime.dao.user.Select;
import com.ioutime.entity.UserMsg;
import com.ioutime.util.RespBody;

import javax.servlet.ServletException;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = req.getParameter("key");
        Select select = new Select();
        List<UserMsg> list = null;
        try {
            list = select.queryMsg("", key);
            RespBody.response(resp,"200","查询成功",list);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
