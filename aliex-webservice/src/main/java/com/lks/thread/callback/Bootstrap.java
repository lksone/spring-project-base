package com.lks.thread.callback;

/**
 * @author lks
 * @description 主线程任务
 * @e-mail 1056224715@qq.com
 * @date 2023/12/28 11:03
 */
public class Bootstrap {

    /**
     * 直接执行一个主流程
     *
     * @param wrapper
     * @return
     */
    private Wrapper doWorker(Wrapper wrapper) {
        new Thread(() -> {
            //线程单元
            Worker worker = wrapper.getWorker();
            //返回结果
            String result = worker.action(wrapper.getParam());
            //将结果设置添加到回调器中
            wrapper.getListener().result(result);
        }).start();

        return wrapper;
    }

    /**
     * 创建一个新线程
     *
     * @return
     */
    private Worker newWorker() {
        return new Worker() {
            @Override
            public String action(String obj) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return obj + " callback!";
            }
        };
    }


    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        Worker worker = bootstrap.newWorker();

        Wrapper wrapper = new Wrapper();
        wrapper.setWorker(worker);
        wrapper.setParam("hello");

        //2.回调方法，输出worker中的内容(异步线程执行)
        bootstrap.doWorker(wrapper).addListener(new Listener() {
            @Override
            public void result(Object result) {
                System.out.println(Thread.currentThread().getName());
                System.out.println(result);
            }
        });

        //1.主线程不阻塞，打印当前线程
        System.out.println(Thread.currentThread().getName());
    }
}
