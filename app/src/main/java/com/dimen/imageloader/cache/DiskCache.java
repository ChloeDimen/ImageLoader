package com.dimen.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.dimen.imageloader.disk.DiskLruCache;
import com.dimen.imageloader.disk.IOUtil;
import com.dimen.imageloader.request.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件名：com.dimen.imageloader.cache
 * 描    述：硬盘缓存
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public class DiskCache implements BitmapCache {

    private static DiskCache mDiskCache;
    //缓存路径
    private String mCacheDir = "Image";
    //MB
    private static final int MB = 1024 * 1024;
    //jackwharton杰作
    private DiskLruCache mDiskLruCache;

    public DiskCache(Context context) {

        initDiskCache(context);
    }
    public static DiskCache getInstance(Context context) {
        if(mDiskCache==null)
        {
            synchronized (DiskCache.class)
            {
                if(mDiskCache==null)
                {
                    mDiskCache=new DiskCache(context);
                }
            }
        }
        return mDiskCache;
    }
    /**
     * 初始化
     * @param context
     */
    private void initDiskCache(Context context) {
        //得到缓存的目录 /cache/Image
        File directory=getDiskCache(mCacheDir,context);
        if(!directory.exists())
        {
            directory.mkdir();
        }
        try {
            //最后一个参数 指定缓存容量
            mDiskLruCache=DiskLruCache.open(directory,1,1,50*MB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private File getDiskCache(String mCacheDir, Context context) {
        String cachePath;
        //默认缓存路径
        return new File(Environment.getExternalStorageDirectory(),mCacheDir);
    }

    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        DiskLruCache.Editor edtor=null;
        OutputStream os=null;
        try {
            //路径必须是合法字符
            edtor=mDiskLruCache.edit(request.getImageUriMD5());
            os=edtor.newOutputStream(0);
            if(persistBitmap2Disk(bitmap,os))
            {
                edtor.commit();
            }else {
                edtor.abort();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean persistBitmap2Disk(Bitmap bitmap, OutputStream os) {
        BufferedOutputStream bos=new BufferedOutputStream(os);

        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            IOUtil.closeQuietly(bos);
        }
        return true;

    }
    @Override
    public Bitmap get(BitmapRequest request) {
        try {
            DiskLruCache.Snapshot snapshot=mDiskLruCache.get(request.getImageUriMD5());
            if(snapshot!=null)
            {
                InputStream inputStream=snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(BitmapRequest request) {
        try {
            mDiskLruCache.remove(request.getImageUriMD5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
