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
 * @date 2021/8/16 17:03
 */

public class DelMessage extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*解析资源*/
        ReqBody reqBody = new ReqBody();
        JSONObject body = reqBody.getBody(req);
        String table_name = body.getString("key");
        int id = body.getInteger("id");

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
        String sql = "DELETE FROM "+table_name+" WHERE id = "+id;
        try {
            int i = dbcpPool.change(connection, sql);
            dbcpPool.closeConnection(connection,null,null);
            if (i != 0){
                RespBody.response(resp,"200","删除成功","");
                System.out.println("删除成功");
            }else{
                RespBody.response(resp,"400","删除失败","");
                System.out.println("删除失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
