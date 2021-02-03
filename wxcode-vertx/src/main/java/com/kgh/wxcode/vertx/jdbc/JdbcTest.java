package com.kgh.wxcode.vertx.jdbc;

import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import rx.Single;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作演示
 *
 * @author kgh
 */
public class JdbcTest extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new JdbcTest());
  }

  @Override
  public void start() throws Exception {

    JsonObject dbConfig = new JsonObject()
      .put("url", "jdbc:mysql://localhost:3306/db?useSSL=false")
      .put("driver_class", "com.mysql.cj.jdbc.Driver")
      .put("user", "root")
      .put("password", "root");

    // 创建数据库连接客户端，类似于connection
    JDBCClient client = JDBCClient.createShared(vertx, dbConfig);

    // 新增
    save(client);

    // 修改
    update(client);

    // 查询
    query(client);

    // 删除
    delete(client);

    // 循环插入
    forInsert(client);

    // 多次操作
    moreOperate(client);

    // 异步循环终极解决方案
    forOperate(client);

    // tx
    tx(client);
  }

/**
 * 事务
 * <p>
 * 获取连接
 * 设置不自动提交事务（setAutoCommit = false)
 * 执行SQL操作
 * 提交或者回滚事务
 * 关闭资源
 *
 * @param client
 */
private void tx(JDBCClient client) {
  client.getConnection(c->{
    if(c.succeeded()) {
      SQLConnection connection = c.result();
      // 关闭事务自动提交
      connection.setAutoCommit(false, a->{
        if(a.succeeded()) {
          // 执行SQL
          connection.update("sql1", r1->{
            if(r1.succeeded()) {
              connection.update("sql2",r2->{
                if(r2.succeeded()) {
                  // 提交事务
                  connection.commit(o->{});
                } else {
                  // 操作2执行失败，事务回滚
                  connection.rollback(r->{});
                }
              });
            }else {
              // 操作1执行失败
              connection.rollback(b->{
                if(b.succeeded()) {
                  // 回滚成功
                }
              });
            }
          });
        } else {
          // 开始事务失败
        }
      });
    } else {
      // 获取连接失败
    }
  });
}

  private void forOperate(JDBCClient client) {
    // 执行的异步操作
    Map<String, JsonArray> operates = buildOperates();

    boolean begin = true;
    Single<JsonObject> r = null;
    JsonObject data = new JsonObject();
    for (Map.Entry<String, JsonArray> op : operates.entrySet()) {
      if (begin) {
        data.put("sql", op.getKey()).put("params", op.getValue());
        r = rxExecutor(data, client);
        begin = false;
      } else {
        r.flatMap(s -> {
          data.mergeIn(s);
          return rxExecutor(data, client);
        });
      }
    }
  }

  /**
   * 构建测试参数
   *
   * @return
   */
  private Map<String, JsonArray> buildOperates() {
    Map<String, JsonArray> ops = new HashMap<>();
    ops.put("select * from user", new JsonArray());
    ops.put("update user set name = ? where id = ?", new JsonArray().add("lisi").add(1));
    return ops;
  }

  /**
   * 异步操作
   *
   * @param data       上一步操作的结果
   * @param jdbcClient
   * @return
   */
  public static Single<JsonObject> rxExecutor(JsonObject data, JDBCClient jdbcClient) {
    return Single.create(
      new io.vertx.rx.java.SingleOnSubscribeAdapter<>(
        fut -> execute(data, jdbcClient, fut))
    );
  }

  /**
   * 具体操作
   *
   * @param data       数据
   * @param jdbcClient
   * @param fut
   */
  private static void execute(JsonObject data, JDBCClient jdbcClient,
                              Handler<AsyncResult<JsonObject>> fut) {
    String sql = data.getString("sql");
    JsonArray params = data.getJsonArray("params");
    jdbcClient.queryWithParams(sql, params, r -> {
      fut.handle(Future.succeededFuture(r.result().getRows().get(0)));
    });
  }

  private void moreOperate(JDBCClient client) {
    String sql = "select * from user";
    client.query(sql, r -> {
    })
      .query(sql, r -> {
      })
      .query(sql, r -> {
      });
  }

  private void forInsert(JDBCClient client) {
    String sql = "update user set name = ? where id = ?";

    List<JsonArray> params = new ArrayList<>();
    params.add(new JsonArray().add("lisi").add(1));
    params.add(new JsonArray().add("zs").add(2));

    // 获取到连接
    client.getConnection(s -> {
      // 拿到的连接对象
      SQLConnection connection = s.result();
      // 执行批量操作
      connection.batchWithParams(sql, params, r -> {
        List<Integer> updates = r.result();
        // do something
      });
    });
  }

  private void delete(JDBCClient client) {
    String sql = "delete from user";
    client.update(sql, r -> {
      if (r.succeeded()) {
        System.out.println("删除成功");
      }
    });
  }

  private void update(JDBCClient client) {
    String sql = "update user set name = ? where id = ?";
    // 带参数
    JsonArray params = new JsonArray()
      .add("lisi")
      .add(1);
    client.updateWithParams(sql, params, r -> {
      if (r.succeeded()) {
        System.out.println("删除成功");
      }
    });
  }

  private void save(JDBCClient client) {
    String sql = "insert into user(name) values ('zhangsan')";
    client.update(sql, r -> {
      if (r.succeeded()) {
        System.out.println("保存成功");
      }
    });
  }

  private void query(JDBCClient client) {
    String sql = "select * from sys_user where id = ?";
    JsonArray params = new JsonArray().add(1);
    client.queryWithParams(sql, params, r -> {
      if (r.succeeded()) {

        List<JsonObject> rows = r.result().getRows();
        System.out.println(rows.get(0));
      } else {
        System.out.println(r.cause());
      }
    });
  }
}
