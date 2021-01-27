package com.kgh.wxcode.vertx.http.weather;

import com.kgh.wxcode.vertx.http.weather.handler.ApiSignHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * 天气服务核心启动类
 *
 * @author kgh
 */
public class Server extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new Server());
    }

    @Override
    public void start() throws Exception {
        HttpServer server= this.getVertx().createHttpServer();

        Router router = Router.router(this.getVertx());


        // 解析body体数据
        router.route().handler(BodyHandler.create());

        // html页面统一分配到template目录
        router.routeWithRegex(".*html").handler(StaticHandler.create("template"));
        // 静态资源统一分配到classpath下的static目录下
        router.route("/static/*").handler(StaticHandler.create("static"));

        // 后台接口统一到接口处理目录
        router.route("/api/*").handler(ApiSignHandler.create());

        server.requestHandler(router);

        server.listen(80);

    }
}
