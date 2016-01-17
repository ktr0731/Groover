package com.syfm.groover.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.syfm.groover.R;
import com.syfm.groover.controller.usecases.LoginUseCase;
import com.syfm.groover.controller.usecases.MusicDataUseCase;
import com.syfm.groover.controller.usecases.PlayDataUseCase;
import com.syfm.groover.view.fragments.CommonDialogFragment;
import com.syfm.groover.view.fragments.CustomProgressDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by lycoris on 2015/09/24.
 */
public class LoginActivity extends Activity {

    @Bind(R.id.progress_bar)
    SmoothProgressBar smoothProgressBar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.editTextSerialNo)
    EditText serialNo;
    @Bind(R.id.editTextPassword)
    EditText password;

    final CustomProgressDialogFragment dialogFragment =
            CustomProgressDialogFragment.newInstance(R.string.dialog_title_login, R.string.dialog_title_login, 1);

    @OnClick(R.id.loginButton)
    public void onClickLoginButton() {
        smoothProgressBar.setVisibility(View.VISIBLE);
        smoothProgressBar.progressiveStart();

        LoginUseCase loginUseCase = new LoginUseCase();
        loginUseCase.checkLogin(serialNo.getText().toString(), password.getText().toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        toolbar.setTitle(getResources().getString(R.string.activity_login));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
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


            Bundle bundle = new Bundle();
            bundle.putInt(CommonDialogFragment.FIELD_TITLE, R.string.dialog_title_login);
            bundle.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.dialog_title_login);
            bundle.putInt(CommonDialogFragment.FIELD_LAYOUT, R.layout.dialog_progress);
            bundle.putBoolean(CommonDialogFragment.FIELD_PROGRESS_BAR, true);

            final CommonDialogFragment dialogFragment = new CommonDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getFragmentManager(), "getDataDialog");

            //dialogFragment.show(getFragmentManager(), "dialog_fragment");

            //Get all data and set db.

            finish();

            // TODO: 取得パーセント表示
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //PlayDataUseCase playDataUseCase = new PlayDataUseCase();
                    //playDataUseCase.setPlayData();
                }
            }, 1000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //MusicDataUseCase musicDataUseCase = new MusicDataUseCase();
                    //musicDataUseCase.setMusicData();
                }
            }, 5000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //eventDataUseCase.setEventData();
                }
            }, 2500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //rankingDataUseCase.setRankingData();
                }
            }, 5500);

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
