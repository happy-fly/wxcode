package com.kgh.wxcode.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 带返回值的方式
 *
 * @author kgh
 * @version 1.0
 * @date 2019/3/18 10:04
 */
public class CreateThreadDemo11_Callable {

    public static void main(String[] args) throws Exception {

        // 创建线程任务
        Callable<Integer> call = () -> {
            System.out.println("线程任务开始执行了....");
            Thread.sleep(2000);
            return 1;
        };

        // 将任务封装为FutureTask
        FutureTask<Integer> task = new FutureTask<>(call);

        // 开启线程，执行线程任务
        new Thread(task).start();

        // ====================
        // 这里是在线程启动之后，线程结果返回之前
        System.out.println("这里可以为所欲为....");
        // ====================

        // 为所欲为完毕之后，拿到线程的执行结果
        Integer result = task.get();
        System.out.println("主线程中拿到异步任务执行的结果为：" + result);
    }
}