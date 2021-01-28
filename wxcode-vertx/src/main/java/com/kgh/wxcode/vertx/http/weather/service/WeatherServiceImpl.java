package com.kgh.wxcode.vertx.http.weather.service;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

public class WeatherServiceImpl implements WeatherService {

    private WebClient webClient;


    public WeatherServiceImpl(Vertx vertx) {
        webClient = WebClient.create(vertx);
    }

    @Override
    public void getWeather(JsonObject data, Handler<AsyncResult<JsonObject>> resultHandler) {
        System.out.println(data.getString("city"));

        webClient.getAbs("http://api.k780.com/?app=weather.today&weaid="+data.getString("city")+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json")
                .send()
                .onSuccess(r->{
                    resultHandler.handle(Future.succeededFuture(r.bodyAsJsonObject()));
                })
                .onFailure(e->{
                    e.printStackTrace();
                    resultHandler.handle(Future.failedFuture(e));
                });
    }
}
