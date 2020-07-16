package com.dimen.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.dimen.imageloader.request.BitmapRequest;
import com.dimen.imageloader.utils.BitmapDecoder;
import com.dimen.imageloader.utils.ImageViewHelper;

import java.io.File;

/**
 * 文件名：com.dimen.imageloader.loader
 * 描    述：
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public class LocalLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
         //得到本地图片的路径
        final String path= Uri.parse(request.getImageUrl()).getPath();
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        BitmapDecoder bitmapDecoder=new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path,options);
            }
        };
        return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView())
                ,ImageViewHelper.getImageViewHeight(request.getImageView()));
    }
}
