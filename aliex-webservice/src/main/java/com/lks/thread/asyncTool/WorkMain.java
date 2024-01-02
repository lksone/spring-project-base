package com.lks.thread.asyncTool;

import com.jd.platform.async.executor.Async;
import com.jd.platform.async.wrapper.WorkerWrapper;

import java.util.concurrent.ExecutionException;

/**
 * @author lks
 * @description 执行C->B->A
 * @e-mail 1056224715@qq.com
 * @date 2023/12/28 14:58
 */
public class WorkMain {

    /**
     * 串行执行
     */
    public static void test1() {
        //引入Worker工作单元
        WorkerA workerA = new WorkerA();
        WorkerB workerB = new WorkerB();
        WorkerC workerC = new WorkerC();

        //包装Worker，编排串行顺序：C <- B <- A
        //C是最后一步，它没有next
        WorkerWrapper wrapperC = new WorkerWrapper.Builder<Integer, Integer>()
                .id("workerC")
                .worker(workerC)
                .callback(workerC)
                .param(3)//3+3
                .build();
        //B的next是C
        WorkerWrapper wrapperB = new WorkerWrapper.Builder<Integer, Integer>()
                .id("workerB")
                .worker(workerB)
                .callback(workerB)
                .param(2)//2+2
                .next(wrapperC)
                .build();
        //A的next是B
        WorkerWrapper wrapperA = new WorkerWrapper.Builder<Integer, Integer>()
                .id("workerA")
                .worker(workerA)
                .callback(workerA)
                .param(1)//1+1
                .next(wrapperB)
                .build();
        try {
            //Action
            Async.beginWork(1000, wrapperA);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * ----A---
     * ----B---
     * ----C---
     * 并行执行
     */
    public static void test2() {
        //引入Worker工作单元
        WorkerA workerA = new WorkerA();
        WorkerB workerB = new WorkerB();
        WorkerC workerC = new WorkerC();

        //包装Worker，编排串行顺序：C <- B <- A
        //C是最后一步，它没有next
        WorkerWrapper wrapperC = new WorkerWrapper.Builder<Integer, Integer>()
                .id("workerC")
                .worker(workerC)
                .callback(workerC)
                .param(3)//3+3
                .build();
        //B的next是C
        WorkerWrapper wrapperB = new WorkerWrapper.Builder<Integer, Integer>()
                .id("workerB")
                .worker(workerB)
                .callback(workerB)
                .param(2)//2+2
                .build();
        //A的next是B
        WorkerWrapper wrapperA = new WorkerWrapper.Builder<Integer, Integer>()
                .id("workerA")
                .worker(workerA)
                .callback(workerA)
                .param(1)//1+1
                .build();
        try {
            //Action
            Async.beginWork(1000, wrapperA, wrapperB, wrapperC);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 项目信息
     */
    public static void test3() {
        //引入Worker工作单元
        WorkerA workerA = new WorkerA();
        WorkerB workerB = new WorkerB();
        WorkerC workerC = new WorkerC();

        //C是最后一步，它没有next
        WorkerWrapper wrapperC = new WorkerWrapper.Builder<Integer, Integer>()
                .id("workerC")
                .worker(workerC)
                .callback(workerC)
                .param(null)//没有参数，根据A的返回值+3
                .build();
        //B是最后一步，它没有next
        WorkerWrapper wrapperB = new WorkerWrapper.Builder<Integer, Integer>()
                .id("workerB")
                .worker(workerB)
                .callback(workerB)
                .param(null)//没有参数，根据A的返回值+2
                .build();
        //A的next是B、C
        WorkerWrapper wrapperA = new WorkerWrapper.Builder<Integer, Integer>()
                .id("workerA")
                .worker(workerA)
                .callback(workerA)
                .param(1)//1+1
                //next是B、C
                .next(wrapperB, wrapperC)
                .build();
        try {
            //Action
            Async.beginWork(1000, wrapperA);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test2();
    }
}
