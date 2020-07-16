package com.dimen.imageloader.cache;

import android.graphics.Bitmap;

import com.dimen.imageloader.request.BitmapRequest;

/**
 * 文件名：com.dimen.imageloader.cache
 * 描    述：
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public interface BitmapCache {
    /**
     * 缓存bitmap
     * @param request
     * @param bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);

    /**
     * 通过请求取bitmap
     * @param request
     */
    Bitmap get(BitmapRequest request);

    /**
     * 移除缓存
     * @param request
     */

    void  remove(BitmapRequest request);
}
