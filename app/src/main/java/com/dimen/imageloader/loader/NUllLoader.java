package com.dimen.imageloader.loader;

import android.graphics.Bitmap;

import com.dimen.imageloader.request.BitmapRequest;

/**
 * 文件名：com.dimen.imageloader.loader
 * 描    述：
 * 作    者：Dimen
 * 时    间：2020/7/16
 */
public class NUllLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        return null;
    }
}
