package com.kgh.wxcode.concurrency;

/**
 * 创建多个线程同时执行
 *
 * @author kgh
 * @version 1.0
 * @date 2019/3/18 9:46
 */
public class CreateMultiThreadDemo2 extends Thread {

    public CreateMultiThreadDemo2(String name) {
        // 设置当前线程的名字
        this.setName(name);
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
        new CreateMultiThreadDemo2("MyThread-01").start();

        // 创建多个线程实例，同时执行
        new CreateMultiThreadDemo2("MyThread-02").start();

        // 演示主线程继续向下执行
        while (true) {
            printThreadInfo();
            Thread.sleep(1000);
        }
    }

    /**
     * 输出当前线程的信息
     */
    private static void printThreadInfo() {
        System.out.println("当前运行的线程名为：" + Thread.currentThread().getName());
    }
}