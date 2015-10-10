package com.syfm.groover.presenters.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.syfm.groover.R;
import com.syfm.groover.business.usecases.LoginUseCase;
import com.syfm.groover.business.usecases.MusicDataUseCase;
import com.syfm.groover.business.usecases.PlayDataUseCase;
import com.syfm.groover.data.storage.databases.MusicData;
import com.syfm.groover.presenters.fragments.CommonDialogFragment;
import com.syfm.groover.presenters.fragments.CustomProgressDialogFragment;

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
        /*
        Gson gson = new Gson();
        MusicData data = gson.fromJson("{\"music_id\":\"260\",\"music_title\":\"\\u84bc\\u7a7a\\u306b\\u821e\\u3048\\u3001\\u58a8\\u67d3\\u306e\\u685c\",\"artist\":\"Silver Forest feat. \\u3055\\u3086\\u308a\",\"skin_name\":\"DEFAULT\",\"ex_flag\":1,\"simple_result_data\":{\"rating\":\"S+\",\"no_miss\":1,\"full_chain\":0,\"play_count\":3,\"is_clear_mark\":false,\"is_failed_mark\":false,\"music_level\":\"SIMPLE\",\"score\":976399,\"max_chain\":1477,\"adlib\":18},\"normal_result_data\":{\"rating\":\"S+\",\"no_miss\":1,\"full_chain\":0,\"play_count\":4,\"is_clear_mark\":false,\"is_failed_mark\":false,\"music_level\":\"NORMAL\",\"score\":986551,\"max_chain\":1609,\"adlib\":19},\"hard_result_data\":{\"rating\":\"S+\",\"no_miss\":1,\"full_chain\":1,\"play_count\":5,\"is_clear_mark\":false,\"is_failed_mark\":false,\"music_level\":\"HARD\",\"score\":967356,\"max_chain\":2095,\"adlib\":9},\"extra_result_data\":{\"rating\":\"A\",\"no_miss\":0,\"full_chain\":0,\"play_count\":6,\"is_clear_mark\":true,\"is_failed_mark\":false,\"music_level\":\"EXTRA\",\"score\":826202,\"max_chain\":986,\"adlib\":9},\"user_rank\":[{\"rank\":4113,\"difficulty\":0},{\"rank\":1819,\"difficulty\":1},{\"rank\":2669,\"difficulty\":2},{\"rank\":5578,\"difficulty\":3}],\"difficulty\":[{\"music_difficulty\":0},{\"music_difficulty\":1},{\"music_difficulty\":2},{\"music_difficulty\":3}]}", MusicData.class);
        if(data != null) {
            Log.d("Unko", "rank:" + data.user_rank.get(0).rank);
        }*/
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
                    MusicDataUseCase musicDataUseCase = new MusicDataUseCase();
                    musicDataUseCase.setMusicData();
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

    public void onEvent(PlayDataUseCase.SetPlayData event) {
        if (event.success) {
            Log.d("Unko", "SetPlayDataSuccess");
            setResult(RESULT_OK);
            finish();
        } else {
            Log.d("Unko", "SetPlayDataError");
        }
    }

    public void onEvent(MusicDataUseCase.SetMusicData event) {
        if (event.success) {
            Log.d("Unko", "SetMusicDataSuccess");
            setResult(RESULT_OK);
            finish();
        } else {
            Log.d("Unko", "SetMusicDataError");
        }
    }
}
