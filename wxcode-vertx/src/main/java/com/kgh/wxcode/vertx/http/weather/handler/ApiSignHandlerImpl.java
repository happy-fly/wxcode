package com.kgh.wxcode.vertx.http.weather.handler;

import io.vertx.ext.web.RoutingContext;

/**
 * 对后端请求的签名参数进行校验
 *
 * @author kgh
 */
public class ApiSignHandlerImpl implements ApiSignHandler {

    public ApiSignHandlerImpl() {
        System.out.println("Api Sign Handle Init...");
    }

    @Override
    public void handle(RoutingContext context) {

        // 验证签名是否正确
        String sign = context.request().getParam("sign");
        if ("123".equals(sign)) {
            // 验证通过，放行
            context.next();
        } else {
            // 拦截
            context.end("签名验证失败");
        }
    }
}
