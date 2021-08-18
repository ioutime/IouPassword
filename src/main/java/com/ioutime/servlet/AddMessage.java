package com.ioutime.servlet;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.dao.MySqlDbcpPool;
import com.ioutime.util.ReqBody;
import com.ioutime.util.RespBody;
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
 * @datatime 2021/8/16 17:03
 */

public class AddMessage extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*解析资源*/
        ReqBody reqBody = new ReqBody();
        JSONObject body = reqBody.getBody(req);
        String table_name = body.getString("key");
        String notes = body.getString("notes");
        String msg = body.getString("msg");

        /*数据库操作*/
        MySqlDbcpPool dbcpPool = new MySqlDbcpPool();
        Connection connection = null;
        try {
            connection = dbcpPool.getMysqlConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "insert into "+table_name+"(notes,msg) values ( '"+notes+"','"+msg+"')";
        try {
            int change = dbcpPool.change(connection, sql);
            if(change != 0){
                RespBody.response(resp,"200","添加成功","");
                System.out.println("添加成功");
            }else {
                RespBody.response(resp,"400","添加失败","");
                System.out.println("添加失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
