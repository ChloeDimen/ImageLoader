package com.dimen.imageloader.request;

import android.util.Log;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件名：com.dimen.imageloader.request
 * 描    述：请求队列
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public class RequestQueue {
    //队列
    //多线程的数据共享
    //阻塞队列
    //生成效率和消费效率相差甚远，处理好兼顾效率和线程安全问题
    //concurrent出现了
    //优先级的阻塞队列
    //1.当队列中没有产品时，消费者被阻塞
    //2.使用优先级，优先级高的产品会被优先消费
    //前提：每个产品的都有一个编号（实例化出来的先后顺序）
    //优先级的确定，受产品编号的影响，但是由加载策略所决定
    private BlockingQueue<BitmapRequest> mBitmapRequests = new PriorityBlockingQueue<>();

    //转发器的数量
    private int threadCount;
    //一组转发器
    private RequestDispatcher[] mDispatchers;

    //i++ ++i线程不安全
    //线程安全
    private AtomicInteger ai = new AtomicInteger(0);


    public RequestQueue(int threadCount) {
        this.threadCount = threadCount;
    }

    public void addRequest(BitmapRequest request) {

        if (!mBitmapRequests.contains(request)) {
            //给请求编号
            request.setSerialNo(ai.incrementAndGet());
            mBitmapRequests.add(request);
            Log.d("Dimen", "添加请求" + request.getSerialNo());
        } else {
            Log.d("Dimen", "请求已经存在" + request.getSerialNo());
        }
    }

    /**
     * 开始请求
     */
    public void start() {
        //先停止
        stop();
        startDispatchers();

    }

    /**
     * 开启转发器
     */
    private void startDispatchers() {
        mDispatchers = new RequestDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            RequestDispatcher p = new RequestDispatcher(mBitmapRequests);
            mDispatchers[i] = p;
            mDispatchers[i].start();

        }
    }

    /**
     * 停止请求
     */
    public void stop() {
        /*mDispatchers = new RequestDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            RequestDispatcher p = new RequestDispatcher(mBitmapRequests);
            mDispatchers[i] = p;
            mDispatchers[i].stop();

        }*/

    }
}
