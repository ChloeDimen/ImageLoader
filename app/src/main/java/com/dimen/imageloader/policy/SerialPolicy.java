package com.dimen.imageloader.policy;

import com.dimen.imageloader.request.BitmapRequest;

/**
 * 文件名：com.dimen.imageloader.policy
 * 描    述：正向加载
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public class SerialPolicy implements LoadPolicy {
    @Override
    public int compareTo(BitmapRequest request1, BitmapRequest request2) {
        return request1.getSerialNo()-request2.getSerialNo();
    }
}
