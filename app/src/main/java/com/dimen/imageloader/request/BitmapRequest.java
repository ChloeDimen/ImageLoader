package com.dimen.imageloader.request;

import android.widget.ImageView;

import com.dimen.imageloader.config.DisplayConfig;
import com.dimen.imageloader.loader.SimpleImageLoader;
import com.dimen.imageloader.policy.LoadPolicy;
import com.dimen.imageloader.utils.MD5Utils;

import java.lang.ref.SoftReference;


/**
 * 文件名：com.dimen.imageloader.request
 * 描    述：
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public class BitmapRequest implements Comparable<BitmapRequest> {
    //持有imageview的软引用
    private SoftReference<ImageView> mImageViewSoftReference;
    //图片路径
    private  String imageUrl;
    //MD5图片路径
    private String imageUriMD5;
    //下载完成监听
    public  SimpleImageLoader.ImageListener mImageListener;

    //加载策略
    private LoadPolicy mLoadPolicy = SimpleImageLoader.getInstance().getImageLoaderConfig().getLoadPolicy();
    private DisplayConfig displayConfig ;

    //优先级序列号
    private int serialNo;

    public BitmapRequest(ImageView imageView, String uri, DisplayConfig config, SimpleImageLoader.ImageListener imageListener) {
        this.mImageViewSoftReference = new SoftReference<ImageView>(imageView);
        //设置可见的ImageView的tag为，要下载的图片路径
        imageView.setTag(uri);
        this.imageUrl = uri;
        this.imageUriMD5 = MD5Utils.toMD5(imageUrl);
        if(config != null){
            this.displayConfig = config;
        }
        this.mImageListener = imageListener;
    }
    //优先级的确定
    @Override
    public int compareTo(BitmapRequest o) {
        return mLoadPolicy.compareTo(this, o);
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mLoadPolicy == null) ? 0 : mLoadPolicy.hashCode());
        result = prime * result + serialNo;
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapRequest that = (BitmapRequest) o;

        if (serialNo != that.serialNo) return false;
        return mLoadPolicy != null ? mLoadPolicy.equals(that.mLoadPolicy) : that.mLoadPolicy == null;
    }

    public ImageView getImageView() {
        return mImageViewSoftReference.get();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageUriMD5() {
        return imageUriMD5;
    }

    public LoadPolicy getLoadPolicy() {
        return mLoadPolicy;
    }

    public DisplayConfig getDisplayConfig() {
        return displayConfig;
    }
}
