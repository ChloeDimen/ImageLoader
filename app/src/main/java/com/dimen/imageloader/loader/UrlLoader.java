package com.dimen.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.dimen.imageloader.request.BitmapRequest;
import com.dimen.imageloader.utils.BitmapDecoder;
import com.dimen.imageloader.utils.ImageViewHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件名：com.dimen.imageloader.loader
 * 描    述：网络加载图片
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public class UrlLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(final BitmapRequest request) {
       /* HttpURLConnection connection = null;
        InputStream inputStream = null;
        final BitmapDecoder bitmapDecoder;
        try {
            connection = (HttpURLConnection) (new URL(request.getImageUrl())).openConnection();
            //转化bufferinputstream流
            inputStream = new BufferedInputStream(connection.getInputStream());
            // Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            //这个有问题
            inputStream.mark(inputStream.available());
            //解码图片
            final InputStream in = inputStream;
            //图片自适应
            bitmapDecoder = new BitmapDecoder() {
                @Override
                public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                    //options 是入参出参对象
                    Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);
                    if (options.inJustDecodeBounds) {
                        try {
                            //第一次读流,宽高信息，读完之后必须为第二次读整个图片进行准备，将流重置
                            in.reset();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //第二次读
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    return bitmap;
                }
            };
            return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView())
                    ,ImageViewHelper.getImageViewHeight(request.getImageView()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //先下载  后读取
        downloadImgByUrl(request.getImageUrl(), getCache(request.getImageUriMD5()));
        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(getCache(request.getImageUriMD5()).getAbsolutePath(), options);
            }
        };
        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView())
                , ImageViewHelper.getImageViewHeight(request.getImageView()));
    }

    //下载图片
    public static boolean downloadImgByUrl(String urlStr, File file) {
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            is = conn.getInputStream();
            fos = new FileOutputStream(file);
            byte[] buf = new byte[512];
            int len = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }

            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }

        return false;

    }

    private File getCache(String unipue) {
        File file = new File(Environment.getExternalStorageDirectory(), "ImageLoader");
        if (!file.exists()) {
            file.mkdir();
        }
        return new File(file, unipue);
    }
}
