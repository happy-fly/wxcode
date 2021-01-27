package com.kgh.wxcode.vertx.http;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

/**
 * 基础的HttpServer
 *
 * 1、创建Vertx实例
 * 2、创建HttpServer实例
 * 3、设置监听的端口号
 * 4、监听客户端请求，并进行处理
 *
 * @author kgh
 */
public class BaseHttpServer_main {

    public static void main(String[] args) {

        // 创建Vertx实例
        Vertx vertx = Vertx.vertx();

        // 创建HttpServer实例
        HttpServer httpServer = vertx.createHttpServer();

        // 处理客户端请求
        httpServer.requestHandler(request -> {
            // 获取到response对象
            HttpServerResponse response = request.response();

            // 设置响应头
            response.putHeader("Content-type", "text/html;charset=utf-8");

            // 响应数据
            response.end("HELLO WORLD");
        });

        // 指定监听端口
        httpServer.listen(80);
    }
}
