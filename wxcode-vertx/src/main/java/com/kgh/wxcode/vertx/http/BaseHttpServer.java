package com.kgh.wxcode.vertx.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

/**
 * 最基础的HTTP服务
 *
 * 1、继承AbstractVerticle
 * 2、重写start方法，执行逻辑在start方法中实现
 * 3、部署Verticle
 *
 * @author kgh
 */
public class BaseHttpServer extends AbstractVerticle {

    public static void main(String[] args) {
        // 部署Verticle
        Vertx.vertx().deployVerticle(new BaseHttpServer());
    }

    @Override
    public void start() throws Exception {
        HttpServer httpServer = this.getVertx().createHttpServer();
        httpServer.requestHandler(request -> {
            // 获取到response对象
            HttpServerResponse response = request.response();

            // 设置响应头
            response.putHeader("Content-type", "text/html;charset=utf-8");

            // 响应数据
            response.end("SUCCESS");
        });

        // 指定监听80端口
        httpServer.listen(80);
    }
}
