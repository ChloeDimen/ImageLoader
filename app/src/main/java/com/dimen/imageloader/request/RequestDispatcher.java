package com.dimen.imageloader.request;



import android.support.v4.app.LoaderManager;
import android.util.Log;

import com.dimen.imageloader.loader.Loader;
import com.dimen.imageloader.loader.LoaderManger;

import java.util.concurrent.BlockingQueue;

/**
 * 文件名：com.dimen.imageloader.request
 * 描    述：转发器，请求转发线程，不断从请求队列中获取请求
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public class RequestDispatcher extends Thread{


    //请求队列
    private BlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    @Override
    public void run() {
        //非阻塞状态，获取请求处理
        while(!isInterrupted()){
            //从队列中获取优先级最高的请求进行处理
            try {
                BitmapRequest request = mRequestQueue.take();
                Log.d("jason", "---处理请求"+request.getSerialNo());
                /**
                 * 处理请求对象
                 */
                //解析图片地址，获取对象的加载器
                String schema = parseSchema(request.getImageUrl());
                //获取加载器
                Loader loader = LoaderManger.getInstance().getLoader(schema);
                loader.loadImage(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 解析图片地址，获取schema
     * @param imageUri
     * @return
     */
    private String parseSchema(String imageUri) {
        if(imageUri.contains("://")){
            return imageUri.split("://")[0];
        }else{
            Log.e("dimen", "图片地址schema异常！");
        }
        return null;
    }


}
