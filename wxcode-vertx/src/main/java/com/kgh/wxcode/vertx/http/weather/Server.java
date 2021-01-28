package com.kgh.wxcode.vertx.http.weather;

import com.kgh.wxcode.vertx.http.weather.handler.ApiSignHandler;
import com.kgh.wxcode.vertx.http.weather.handler.WeatherHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TimeoutHandler;

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
        // 超时时间设置
        router.route().handler(TimeoutHandler.create(5000));

        // html页面统一分配到template目录
        router.routeWithRegex(".*html").handler(StaticHandler.create("template"));
        // 静态资源统一分配到classpath下的static目录下
        router.route("/static/*").handler(StaticHandler.create("static"));

        // 后台接口统一到接口处理目录
        router.route("/api/*").handler(ApiSignHandler.create());

        // 获取天气的api服务
        router.route("/api/w").handler(WeatherHandler.create(vertx));

        server.requestHandler(router);
        server.listen(80);
    }
}
