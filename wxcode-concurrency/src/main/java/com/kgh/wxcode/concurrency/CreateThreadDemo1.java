package com.kgh.wxcode.concurrency;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 继承Thread类的方式创建线程
 *
 * @author kgh
 * @version 1.0
 * @date 2019/3/13 19:19
 */
public class CreateThreadDemo1 extends Thread {

    public CreateThreadDemo1() {
        // 设置当前线程的名字
        this.setName("MyThread");
    }

    @Override
    public void run() {
        // 每隔1s中输出一次当前线程的名字
        while (true) {
            // 输出线程的名字，与主线程名称相区分
            printThreadInfo();
            try {
                // 线程休眠一秒
                Thread.sleep(1000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 注意这里，要调用start方法才能启动线程，不能调用run方法
        new CreateThreadDemo1().start();

        // 演示主线程继续向下执行
//        while (true) {
//            printThreadInfo();
//            Thread.sleep(1000);
//        }
    }

    /**
     * 输出当前线程的信息
     */
    private static void printThreadInfo() {
        Map<Thread,StackTraceElement[]> threads = Thread.getAllStackTraces();
        System.out.println("当前线程的数量：" + threads.size() + " " + threads.keySet().stream().map(t->t.getName()).collect(Collectors.joining(",")));
        System.out.println("当前运行的线程名为： " + Thread.currentThread().getName());
    }
}