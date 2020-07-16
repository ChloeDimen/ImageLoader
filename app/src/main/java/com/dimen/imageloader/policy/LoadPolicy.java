package com.dimen.imageloader.policy;

import com.dimen.imageloader.request.BitmapRequest;

/**
 * 文件名：com.dimen.imageloader.policy
 * 描    述：加载策略
 * 作    者：Dimen
 * 时    间：2020/7/7
 */
public interface LoadPolicy {

    /**
     * 两个BitmapRequest进行比较
     * @param request1
     * @param request2
     * @return 小于0，request1 < request2，大于0，request1 > request2，等于
     */
    int compareTo(BitmapRequest request1, BitmapRequest request2);

}
