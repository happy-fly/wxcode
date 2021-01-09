package com.kgh.wxcode.concurrency;

/**
 * 匿名内部类的方式创建线程
 *
 * @author kgh
 * @version 1.0
 * @date 2019/3/18 10:04
 */
public class CreateThreadDemo6_Anonymous {

    public static void main(String[] args) {
        // 基于子类的方式
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    printThreadInfo();
                }
            }
        }.start();

        // 基于接口的实现
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    printThreadInfo();
                }
            }
        }).start();
    }

    /**
     * 输出当前线程的信息
     */
    private static void printThreadInfo() {
        System.out.println("当前运行的线程名为：" + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}