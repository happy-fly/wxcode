package com.kgh.wxcode.vertx.http.weather.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * 天气服务接口
 *
 * @author kgh
 */
public interface WeatherService {

    /**
     * 定义获取天气情况的核心服务接口
     *
     * @param data 请求的数据，包括位置信息等
     * @param resultHandler 响应的数据
     */
    void getWeather(JsonObject data, Handler<AsyncResult<JsonObject>> resultHandler);

}
