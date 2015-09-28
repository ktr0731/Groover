package com.syfm.groover.presenters.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.syfm.groover.data.network.AppController;
import com.syfm.groover.R;

import org.json.JSONObject;

/**
 * Created by lycoris on 2015/09/22.
 */
public class PlayDataFragment extends Fragment {

    public static PlayDataFragment newInstance(int position) {
        PlayDataFragment playDataFragment = new PlayDataFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        playDataFragment.setArguments(bundle);

        return playDataFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int position = 0;
        String[] tab_name = getResources().getStringArray(R.array.tab_name);

        if(bundle != null) {
            position = bundle.getInt("position");
        }

        View view = inflater.inflate(R.layout.fragment_play_data, group, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void requestPlayData() {
        String url = "https://mypage.groovecoaster.jp/sp/json/player_data.php";
        final String tag_json = "json_req";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }), tag_json);
    }



}
