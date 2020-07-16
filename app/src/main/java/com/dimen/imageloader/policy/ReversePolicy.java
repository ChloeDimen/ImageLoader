package com.dimen.imageloader.policy;

import com.dimen.imageloader.request.BitmapRequest;

/**
 * 文件名：com.dimen.imageloader.policy
 * 描    述：反向加载
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public class ReversePolicy implements LoadPolicy {
    @Override
    public int compareTo(BitmapRequest request1, BitmapRequest request2) {
        return request2.getSerialNo()-request1.getSerialNo();
    }
}
