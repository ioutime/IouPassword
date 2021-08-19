package com.ioutime.servlet;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.dao.user.OtherOpt;
import com.ioutime.util.JwtUtil;
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
 * @date 2021/8/16 17:03
 */

public class DelMessage extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        /*解析资源*/
        ReqBody reqBody = new ReqBody();
        JSONObject body = reqBody.getBody(req);
        int id = body.getInteger("id");
        String token = body.getString("token");
        int uid = JwtUtil.getInfo(token);
        if(uid == -1){
            RespBody.response(resp,"400","身份认证失败","");
        }else {
            OtherOpt otherOpt = new OtherOpt();
            try {
                boolean delete = otherOpt.delete(id,uid);
                if(delete){
                    RespBody.response(resp,"200","删除成功","");
                    System.out.println("删除成功");
                }else {
                    RespBody.response(resp,"400","删除失败","");
                    System.out.println("删除失败");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                RespBody.response(resp,"400","删除失败","");
                System.out.println("删除失败");
            }
        }

    }
}
