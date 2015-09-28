package com.syfm.groover.presenters.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.syfm.groover.R;
import com.syfm.groover.business.usecases.LoginUseCase;
import com.syfm.groover.data.storage.SharedPreferenceHelper;

import de.greenrobot.event.EventBus;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by lycoris on 2015/09/24.
 */
public class LoginActivity extends Activity {

    private SmoothProgressBar smoothProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.activity_login));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        SharedPreferenceHelper.create(getApplicationContext());

        smoothProgressBar = (SmoothProgressBar)findViewById(R.id.progress_bar);

        final Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText serialNo = (EditText)findViewById(R.id.editTextSerialNo);
                final EditText password = (EditText)findViewById(R.id.editTextPassword);

                smoothProgressBar.setVisibility(View.VISIBLE);
                smoothProgressBar.progressiveStart();

                LoginUseCase loginUseCase = new LoginUseCase();
                loginUseCase.checkLogin(serialNo.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(LoginUseCase.LoginEvent event) {
        smoothProgressBar.progressiveStop();
        smoothProgressBar.setVisibility(View.GONE);

        if(event.success) {
            Toast.makeText(getApplicationContext(), "ログインに成功しました", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "ログインに失敗しました", Toast.LENGTH_SHORT).show();
        }
    }

    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }

        return false;
    }
}
