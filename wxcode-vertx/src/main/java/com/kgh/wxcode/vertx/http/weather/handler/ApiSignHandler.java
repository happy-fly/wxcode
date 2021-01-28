package com.kgh.wxcode.vertx.http.weather.handler;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface ApiSignHandler extends Handler<RoutingContext> {

    static Handler create() {
        return new ApiSignHandlerImpl();
    }
}
