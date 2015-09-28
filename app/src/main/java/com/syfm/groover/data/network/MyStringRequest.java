package com.syfm.groover.data.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.syfm.groover.data.network.AppController;

import java.util.Map;

/**
 * Created by lycoris on 2015/09/24.
 */
public class MyStringRequest extends StringRequest {

    public MyStringRequest(int method, String url,
                           Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MyStringRequest(String url,
                           Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Map<String, String> headers = response.headers;
        Log.d("UnkoParse", headers.toString());
        return super.parseNetworkResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);

        Log.d("UnkoRes", error.networkResponse.headers.toString() + "CODE::" + error.networkResponse.statusCode);
    }
}
