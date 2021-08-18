package com.ioutime.util;

import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.net.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/15 18:15
 */

public class ReqBody {
    public JSONObject getBody(HttpServletRequest req) throws IOException {
        //获取请求中的body
        InputStreamReader input = new InputStreamReader(req.getInputStream(),"utf-8");
        String result = "";
        int respInt = input.read();
        while(respInt!=-1) {
            result +=(char)respInt;
            respInt = input.read();
        }
        input.close();
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

}
