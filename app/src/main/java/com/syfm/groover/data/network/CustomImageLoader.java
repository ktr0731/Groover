package com.syfm.groover.data.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by lycoris on 2015/10/11.
 */
public class CustomImageLoader extends ImageLoader {

    public CustomImageLoader(RequestQueue queue, ImageCache imageCache) {
        super(queue, imageCache);
    }

    public static ImageListener getImageListener(final ImageView view, final ProgressBar progressBar, final int errorImageResId) {

        return new ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Unko", "error : " + error.networkResponse.headers);
                if (errorImageResId != 0) {
                    view.setImageResource(errorImageResId);
                } else {
                    view.setImageResource(android.R.drawable.ic_menu_report_image);
                }
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onResponse(ImageContainer response, boolean isImmediate) {
                Log.d("Unko", "Success:" + response.getRequestUrl());
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        };

    }
}