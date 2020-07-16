package com.dimen.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.dimen.imageloader.cache.BitmapCache;
import com.dimen.imageloader.config.DisplayConfig;
import com.dimen.imageloader.request.BitmapRequest;

/**
 * 文件名：com.dimen.imageloader.loader
 * 描    述：
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public abstract class AbstractLoader implements Loader{
    //拿到用户自定义的缓存策略
    private BitmapCache mBitmapCache=SimpleImageLoader.getInstance().getImageLoaderConfig().getBitmapCache();
    //拿到显示配置
    private DisplayConfig mDisplayConfig = SimpleImageLoader.getInstance().getImageLoaderConfig().getDisplayConfig();



    @Override
    public void loadImage(BitmapRequest request) {
        //从缓存取到bitmap,调用层不知道调用的是硬盘缓存还是内存缓存
        Bitmap bitmap = mBitmapCache.get(request);
        if (bitmap == null) {
            //显示默认加载图片
            showLoadImage(request);
            //开始真正的加载图片
            bitmap=onLoad(request);
            //缓存图片
            cacheBitmap(request, bitmap);
        }
        //显示
        deliveryToUIThread(request,bitmap);
    }

    /**
     * 交给主线程显示
     * @param request
     * @param bitmap
     */
    private void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if(imageView!=null)
        {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    updateImageView(request, bitmap);
                }

            });
        }

    }
    private void updateImageView(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        //加载正常  防止图片错位
        if(bitmap != null && imageView.getTag().equals(request.getImageUrl())){
            imageView.setImageBitmap(bitmap);
        }
        //有可能加载失败
        if(bitmap == null && mDisplayConfig!=null&&mDisplayConfig.failedImage!=-1){
            imageView.setImageResource(mDisplayConfig.failedImage);
        }
        //监听
        //回调 给圆角图片  特殊图片进行扩展
        if(request.mImageListener != null){
            request.mImageListener.onComplete(imageView, bitmap, request.getImageUrl());
        }
    }

    /**
     * 缓存图片
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {

        if (request != null && bitmap != null) {
            synchronized (AbstractLoader.class) {
                mBitmapCache.put(request,bitmap);
            }
        }
    }

    /**
     * 抽象加载策略，加载网络和本地图片有差异
     * @param request
     * @return
     */

    protected abstract Bitmap onLoad(BitmapRequest request);

    /**
     * 加载前显示的图片
     * @param request
     */
    private void showLoadImage(BitmapRequest request) {

        //指定了，显示配置
        if(hasLoadingPlaceHolder()){
            final ImageView imageView = request.getImageView();
            if(imageView!=null)
            {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(mDisplayConfig.loadingImage);
                    }
                });
            }

        }
    }
    protected boolean hasLoadingPlaceHolder(){
        return (mDisplayConfig != null && mDisplayConfig.loadingImage > 0);
    }
}
