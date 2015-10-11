package com.syfm.groover.data.storage;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.syfm.groover.data.network.CustomImageLoader;

/**
 * Created by lycoris on 2015/10/11.
 */
public class CustomLruCache implements CustomImageLoader.ImageCache{

    private LruCache<String, Bitmap> mMemoryCache;

    public CustomLruCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;       // 最大メモリに依存
        // int cacheSize = 5 * 1024 * 1024;  // 5MB

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 使用キャッシュサイズ(KB単位)
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mMemoryCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mMemoryCache.put(url,bitmap);
    }

}
