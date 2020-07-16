package com.dimen.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.dimen.imageloader.config.DisplayConfig;
import com.dimen.imageloader.config.ImageLoaderConfig;
import com.dimen.imageloader.request.BitmapRequest;
import com.dimen.imageloader.request.RequestQueue;

/**
 * 文件名：com.dimen.imageloader.loader
 * 描    述：
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public class SimpleImageLoader {

    //配置
    private ImageLoaderConfig mImageLoaderConfig;
    //请求队列
    private RequestQueue mRequestQueue;
    private static SimpleImageLoader mInstance;

    private SimpleImageLoader() {

    }

    private SimpleImageLoader(ImageLoaderConfig imageLoaderConfig) {
        mImageLoaderConfig = imageLoaderConfig;
        mRequestQueue = new RequestQueue(mImageLoaderConfig.getThreadCount());
        //开启请求队列
        mRequestQueue.start();
    }

    /**
     * 获取单例方式
     *
     * @param config
     * @return
     */
    public static SimpleImageLoader getInstance(ImageLoaderConfig config) {
        if (mInstance == null) {
            synchronized (SimpleImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new SimpleImageLoader(config);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取SimpleImageLoader的实例
     *
     * @return 只有在SimpleImageLoader getInstance(ImageLoaderConfig config)调用过之后，才能返回一个实例化了的SimpleImageLoader对象
     */
    public static SimpleImageLoader getInstance() {
        if (mInstance == null) {
            throw new UnsupportedOperationException("getInstance(ImageLoaderConfig config) 没有执行过！");
        }
        return mInstance;
    }

    /**
     * 显示图片
     * @param imageView
     * @param uri
     */
    public void displayImage(ImageView imageView, String uri) {
        displayImage(imageView, uri, null, null);

    }

    /**
     * 显示图片
     * @param imageView   要显示图片的控件
     * @param uri 图片路径
     * @param displayConfig 显示配置
     * @param imageListener 图片加载的监听
     */
    public void displayImage(ImageView imageView, String uri, DisplayConfig displayConfig,ImageListener imageListener) {

        //生成一个请求，添加到请求队列中
        BitmapRequest request = new BitmapRequest(imageView, uri, displayConfig, imageListener);
        //添加到队列
        mRequestQueue.addRequest(request);
    }

    //监听图片加载
    public static interface ImageListener{
        /**
         * 加载完成
         * @param imageView
         * @param bitmap
         * @param uri
         */
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

    /**
     * 拿到全局配置
     * @return
     */
    public ImageLoaderConfig getImageLoaderConfig(){
        return mImageLoaderConfig;
    }
}
