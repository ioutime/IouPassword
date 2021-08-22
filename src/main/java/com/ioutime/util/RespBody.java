package com.ioutime.util;

import com.alibaba.fastjson.JSONObject;
import com.ioutime.result.ResultMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/15 21:32
 */

public class RespBody {
    public static void response(HttpServletResponse resp,String code,String msg,Object data) throws IOException {
        //响应
        resp.setContentType("application/json; charset=utf-8");
        String base64 = Base64.getEncoder().encodeToString(msg.getBytes(StandardCharsets.UTF_8));
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setCode(code);
        resultMessage.setMsg(base64);
        resultMessage.setData(data);
        PrintWriter writer = resp.getWriter();
        JSONObject o = new JSONObject();
        o.put("res", resultMessage);
        writer.write(o.toString());
    }
}
