package com.kgh.wxcode.concurrency;

/**
 * 匿名内部类的方式创建线程
 *
 * @author kgh
 * @version 1.0
 * @date 2019/3/18 10:04
 */
public class CreateThreadDemo7_Anonymous {

    public static void main(String[] args) {
        // 基于子类和接口的方式
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    printInfo("interface");
                }
            }
        }) {
            @Override
            public void run() {
                while (true) {
                    printInfo("sub class");
                }
            }
        }.start();
    }

    /**
     * 输出当前线程的信息
     */
    private static void printInfo(String text) {
        System.out.println(text);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}