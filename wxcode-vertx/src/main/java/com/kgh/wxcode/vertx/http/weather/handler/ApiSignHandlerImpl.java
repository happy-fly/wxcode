package com.kgh.wxcode.vertx.http.weather.handler;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

public class ApiSignHandlerImpl implements ApiSignHandler {

    @Override
    public void handle(RoutingContext context) {


        // 放行，匹配下一个路由
        context.next();
    }
}
