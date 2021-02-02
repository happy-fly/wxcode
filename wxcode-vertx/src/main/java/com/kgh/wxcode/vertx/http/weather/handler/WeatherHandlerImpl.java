package com.kgh.wxcode.vertx.http.weather.handler;

import com.kgh.wxcode.vertx.http.weather.service.WeatherService;
import com.kgh.wxcode.vertx.http.weather.service.WeatherServiceImpl;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

/**
 * 对后端请求的签名参数进行校验
 *
 * @author kgh
 */
public class WeatherHandlerImpl implements ApiSignHandler {

  private WeatherService weatherService;

  /**
   * 此类仅在系统初始化的时候执行一次
   */
  public WeatherHandlerImpl(Vertx vertx) {
    weatherService = new WeatherServiceImpl(vertx);
    System.out.println("Weather Handle Init...");
  }

  @Override
  public void handle(RoutingContext context) {
    System.out.println("Weather handle service");

    JsonObject data = context.getBodyAsJson();
    weatherService.getWeather(data, r -> {
      if (r.succeeded()) {
        context.response()
          .putHeader("Content-Type", "application/json;charset=utf-8")
          .end(r.result().toString());
      } else {
        context.end("get weather err");
      }
    });

  }
}
