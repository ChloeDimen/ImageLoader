package com.dimen.imageloader.config;

import com.dimen.imageloader.cache.BitmapCache;
import com.dimen.imageloader.cache.MemoryCache;
import com.dimen.imageloader.policy.LoadPolicy;
import com.dimen.imageloader.policy.ReversePolicy;

/**
 * 文件名：com.dimen.imageloader.config
 * 描    述：默认的图片的配置
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public class ImageLoaderConfig {
    //缓存策略
    private BitmapCache mBitmapCache=new MemoryCache();
    //加载策略
    private LoadPolicy mLoadPolicy=new ReversePolicy();
    //默认线程个数，jvm虚拟机的可用处理器个数
    private int threadCount = Runtime.getRuntime().availableProcessors();


    private DisplayConfig mDisplayConfig = new DisplayConfig();

    private ImageLoaderConfig() {


    }

    public static class Builder {

        private ImageLoaderConfig mConfig;

        public Builder() {
            mConfig = new ImageLoaderConfig();

        }

        //设置缓存策略
        public Builder setCachePolicy(BitmapCache bitmapCache) {
            mConfig.mBitmapCache = bitmapCache;
            return this;
        }

        /**
         * 设置加载策略
         *
         * @param loadPolicy
         * @return
         */
        public Builder setloadPolicy(LoadPolicy loadPolicy) {
            mConfig.mLoadPolicy = loadPolicy;
            return this;
        }

        /**
         * 设置线程个数
         *
         * @param count
         * @return
         */
        public Builder setThreadCount(int count) {
            mConfig.threadCount = count;
            return this;
        }

        /**
         * 图片加载过程中显示的图片
         *
         * @param resId
         * @return
         */
        public Builder setLoadingPlaceHolder(int resId) {
            mConfig.mDisplayConfig.loadingImage = resId;
            return this;
        }


        /**
         * 图片加载失败显示的图片
         *
         * @param resId
         * @return
         */
        public Builder setFailedPlaceHolder(int resId) {
            mConfig.mDisplayConfig.failedImage = resId;
            return this;
        }

        public ImageLoaderConfig build() {
            return mConfig;
        }
    }


    public BitmapCache getBitmapCache() {
        return mBitmapCache;
    }

    public LoadPolicy getLoadPolicy() {
        return mLoadPolicy;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }
}
