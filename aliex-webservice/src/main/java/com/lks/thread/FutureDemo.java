package com.lks.thread;

import com.google.common.util.concurrent.*;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/12/20 22:31
 */
public class FutureDemo {

    /**
     * guava执行
     */
    public static void futureDmeo() {
        //创建一个监听service
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        //执行一个线程，call是回调
        final ListenableFuture<Integer> listenableFuture = executorService.submit(new Callable<Integer>() {

            /**
             * 线程执行的结果
             * @return
             * @throws Exception
             */
            @Override
            public Integer call() throws Exception {
                System.out.println("call execute..");
                TimeUnit.SECONDS.sleep(1);
                return 7;
            }
        });

        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("get listenable future's result " + listenableFuture.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, executorService);

        while (listenableFuture.isDone()) {
            executorService.shutdownNow();
        }

    }

    /**
     * 多线程
     */
    public static void guravaEx() {
        //创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(5);
        //执行监听服务
        ListeningExecutorService guavaExecutor = MoreExecutors.listeningDecorator(executor);
        //执行2
        ListenableFuture<String> future2 = guavaExecutor.submit(() -> {
            //step 2
            System.out.println("执行step 2");
            return "step2 result";
        });
        //执行1
        ListenableFuture<String> future1 = guavaExecutor.submit(() -> {
            //step 1
            System.out.println("执行step 1");
            return "step1 result";
        });

        ListenableFuture<List<String>> future1And2 = Futures.allAsList(future1, future2);

        //futures执行回调数据信息
        Futures.addCallback(future1And2, new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                System.out.println(result);
                ListenableFuture<String> future3 = guavaExecutor.submit(() -> {
                    System.out.println("一定是回调第三次执行执行step 3");
                    return "step3 result";
                });
                Futures.addCallback(future3, new FutureCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                    }
                }, guavaExecutor);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("执行失败了");
            }
        }, guavaExecutor);
    }

    public static void demo3() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行step 1");
            return "step1 result";
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行step 2");
            return "step2 result";
        });
        cf1.thenCombine(cf2, (result1, result2) -> {
            System.out.println(result1 + " , " + result2);
            System.out.println("执行step 3");
            return "step3 result";
        }).thenAccept(result3 -> System.out.println(result3));
    }

    public static void main(String[] args) {
        demo3();
    }
}
