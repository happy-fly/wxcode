package com.kgh.wxcode.vertx.jdbc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

import java.util.List;

/**
 * 数据库操作演示
 *
 * @author kgh
 */
public class JdbcTest extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new JdbcTest());
    }

    @Override
    public void start() throws Exception {

        JsonObject dbConfig = new JsonObject()
                .put("url", "jdbc:mysql://localhost:3306/db?useSSL=false")
                .put("driver_class", "com.mysql.cj.jdbc.Driver")
                .put("user", "root")
                .put("password", "root");

        // 创建数据库连接客户端，类似于connection
        JDBCClient client = JDBCClient.createShared(vertx, dbConfig);

        // 新增
        save(client);

        // 修改
        update(client);

        // 查询
        query(client);

        // 删除
        delete(client);
    }

    private void delete(JDBCClient client) {
        String sql = "delete from user";
        client.update(sql, r -> {
            if (r.succeeded()) {
                System.out.println("删除成功");
            }
        });
    }

    private void update(JDBCClient client) {
        String sql = "update user set name = ? where id = ?";
        // 带参数
        JsonArray params = new JsonArray()
                .add("lisi")
                .add(1);
        client.updateWithParams(sql, params, r -> {
            if (r.succeeded()) {
                System.out.println("删除成功");
            }
        });
    }

    private void save(JDBCClient client) {
        String sql = "insert into user(name) values ('zhangsan')";
        client.update(sql, r -> {
            if (r.succeeded()) {
                System.out.println("保存成功");
            }
        });
    }

    private void query(JDBCClient client) {
        String sql = "select * from sys_user where id = ?";
        JsonArray params = new JsonArray().add(1);
        client.queryWithParams(sql, params, r -> {
            if (r.succeeded()) {
                List<JsonObject> rows = r.result().getRows();
                System.out.println(rows.get(0));
            } else {
                System.out.println(r.cause());
            }
        });
    }
}
