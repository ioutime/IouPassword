package com.ioutime.util;

import com.ioutime.dao.MySqlDbcpPool;


/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/19 14:50
 */

public class StartThread extends Thread{
    @Override
    public void run() {
        new MySqlDbcpPool();
    }
}
