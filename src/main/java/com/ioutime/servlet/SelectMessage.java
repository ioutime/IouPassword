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
import java.util.List;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/16 17:04
 */

public class SelectMessage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = new ReqBody().getBody(req);
        String token = (String) jsonObject.get("token");
        String notes = (String) jsonObject.get("notes");
        int uid = JwtUtil.getInfo(token);
        if(uid == -1){
            RespBody.response(resp,"400","身份认证失败","");
        }else {
            /*数据库处理*/
            Select select = new Select();
            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1 = select.queryMsg(uid,notes);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            RespBody.response(resp,"200","查询成功",jsonObject1);

        }


    }
}
