package com.kgh.wxcode.concurrency;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时任务
 *
 * @author kgh
 * @version 1.0
 * @date 2019/3/18 10:04
 */
public class CreateThreadDemo10_Timer {

    public static void main(String[] args){

        // 创建定时器
        Timer timer = new Timer();

        // 提交计划任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时任务执行了...");
            }
        },
                new Date(), 1000);
    }
}