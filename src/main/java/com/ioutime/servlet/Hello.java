package com.ioutime.servlet;

import com.ioutime.util.RespBody;
import com.ioutime.util.StartThread;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/19 15:11
 */

public class Hello extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        StartThread startThread = new StartThread();
        startThread.start();
        try {
            RespBody.response(resp,"200","连接成功","");
        } catch (IOException e) {
            System.out.println("返回失败");
        }
    }
}
