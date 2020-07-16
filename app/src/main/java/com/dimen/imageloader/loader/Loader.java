package com.dimen.imageloader.loader;

import com.dimen.imageloader.request.BitmapRequest;

/**
 * 文件名：com.dimen.imageloader.loader
 * 描    述：
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public interface Loader {
    /**
     * 加载图片
     */
    void loadImage(BitmapRequest request);
}
