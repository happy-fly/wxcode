package com.kgh.wxcode.vertx.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisClientType;
import io.vertx.redis.client.RedisOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Vert.操作Redis
 *
 * @author kgh
 */
public class RedisTest extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new RedisTest());
  }

  @Override
  public void start() throws Exception {

    // 创建Redis客户端
    RedisOptions options = new RedisOptions()
      // redis://[:password@]host[:port][/db-number]
      .setConnectionString("redis://192.168.5.136:6379/0")
      .setType(RedisClientType.STANDALONE);
    Redis redis = Redis.createClient(vertx, options);

    // 使用便于操作的RedisAPI
    RedisAPI api = RedisAPI.api(redis);

    // 存储
    set(api);

    //读取
    get(api);
  }

  private void get(RedisAPI api) {
    api.get("hello", r -> {
      if (r.succeeded()) {
        String v = r.result().toString();
        System.out.println(v);
      }
    });
  }

  private void set(RedisAPI api) {
    List<String> p = new ArrayList<>();
    p.add("hello");
    p.add("world");

    // 向缓存中存储一个key为hello 值为world的数据
    api.set(p);
  }
}
