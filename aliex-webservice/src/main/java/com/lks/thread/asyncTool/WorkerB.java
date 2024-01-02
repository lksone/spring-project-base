package com.lks.thread.asyncTool;

import com.alibaba.fastjson.JSON;
import com.jd.platform.async.callback.ICallback;
import com.jd.platform.async.callback.IWorker;
import com.jd.platform.async.executor.timer.SystemClock;
import com.jd.platform.async.worker.WorkResult;
import com.jd.platform.async.wrapper.WorkerWrapper;

import java.util.Map;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/12/28 14:51
 */
public class WorkerB implements IWorker<Integer, Integer>, ICallback<Integer, Integer> {
    @Override
    public void begin() {
        System.out.println("B - Thread:" + Thread.currentThread().getName() + "- start --" + SystemClock.now());
    }

    /**
     * 耗时操作执行完毕后，就给value注入值
     *
     * @param success
     * @param param
     * @param workResult
     */
    @Override
    public void result(boolean success, Integer param, WorkResult<Integer> workResult) {
        System.out.println("B - param:" + JSON.toJSONString(param));
        System.out.println("B - result:" + JSON.toJSONString(workResult));
        System.out.println("B - Thread:" + Thread.currentThread().getName() + "- end --" + SystemClock.now());
    }

    /**
     * 在这里做耗时操作，如rpc请求、IO等
     *
     * @param object      object
     * @param allWrappers
     */
    @Override
    public Integer action(Integer object, Map<String, WorkerWrapper> allWrappers) {
        Integer res = object + 2;
        return res;
    }

    /**
     * 超时、异常时，返回的默认值
     *
     * @return 默认值
     */
    @Override
    public Integer defaultValue() {
        System.out.println("B - defaultValue");
        return 102;
    }
}
