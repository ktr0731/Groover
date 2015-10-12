package com.syfm.groover.presenters.activities;

import android.app.Activity;
import android.os.Bundle;

import com.syfm.groover.R;

import butterknife.ButterKnife;

/**
 * Created by lycoris on 2015/10/12.
 */
public class MusicDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
