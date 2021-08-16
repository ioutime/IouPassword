package com.ioutime.dao.date;

import com.ioutime.dao.MysqlConnection;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/16 16:16
 */

public class OptTables {

    public int creatTable(String name) throws SQLException, ClassNotFoundException {
        /*数据库连接*/
        MysqlConnection mysqlConnection = new MysqlConnection();
        Connection connection = mysqlConnection.getConnection();
        String sql = "create table if not exists "+name +
                "(id INTEGER primary key not null auto_increment," +
                "notes VARCHAR(255) not null," +
                "msg VARCHAR(255) not null)";
        int i = mysqlConnection.change(connection, sql);
        mysqlConnection.closeConnection(connection,null,null);
        return i;
    }

}
