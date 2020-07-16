package com.dimen.imageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件名：com.dimen.imageloader.loader
 * 描    述：加载图片管理器
 * 作    者：Dimen
 * 时    间：2020/7/16
 */
public class LoaderManger {
    //缓存所有支持的Loader类型
    private Map<String, Loader> mLoaderMap = new HashMap<>();

    private static LoaderManger sManger = new LoaderManger();

    private LoaderManger() {

        register("http", new UrlLoader());
        register("https", new UrlLoader());
        register("file", new LocalLoader());
    }


    public static LoaderManger getInstance() {

        return sManger;
    }

    private void register(String schema, Loader loader) {
        mLoaderMap.put(schema, loader);

    }

    public Loader getLoader(String schema) {
        if (mLoaderMap.containsKey(schema)) {
          return   mLoaderMap.get(schema);
        }
        return new NUllLoader();
    }
}
