package com.kgh.wxcode.vertx.http.weather.handler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

/**
 * 天气API处理器接口
 *
 * @author kgh
 */
public interface WeatherHandler extends Handler<RoutingContext> {

    static Handler create(Vertx vertx) {
        return new WeatherHandlerImpl(vertx);
    }
}
