package com.ioutime.servlet;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.dao.user.OtherOpt;
import com.ioutime.util.JwtUtil;
import com.ioutime.util.ReqBody;
import com.ioutime.util.RespBody;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author ioutime
 * @version 1.0
 * @datatime 2021/8/16 17:03
 */

public class AddMessage extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        /*解析资源*/
        ReqBody reqBody = new ReqBody();
        JSONObject body = reqBody.getBody(req);
        String token = body.getString("token");
        String notes = body.getString("notes");
        String msg = body.getString("msg");
        int uid  = JwtUtil.getInfo(token);
        if(uid == 0) {
            RespBody.response(resp,"400","添加失败","");
        }else{
            OtherOpt otherOpt = new OtherOpt();
            Object[] params  ={uid,notes,msg};
            try {
                boolean add = otherOpt.add(params);
                if(add){
                    RespBody.response(resp,"200","添加成功","");
                }else {
                    RespBody.response(resp,"400","添加失败","");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                RespBody.response(resp,"400","添加失败","");
            }
        }


    }
}
