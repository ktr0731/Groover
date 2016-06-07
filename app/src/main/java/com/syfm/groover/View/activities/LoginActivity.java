package com.syfm.groover.view.activities;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.syfm.groover.databinding.ActivityLoginBinding;
import com.syfm.groover.view.eventHandler.LoginEventHandlers;
import com.syfm.groover.view.fragments.ProgressDialogFragment;

import org.jdeferred.android.AndroidDeferredManager;

import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/09/24.
 */
public class LoginActivity extends Activity implements LoginEventHandlers {

    private ActivityLoginBinding binding;

    private AndroidDeferredManager deferred = new AndroidDeferredManager();

    final ProgressDialogFragment dialogFragment =
            ProgressDialogFragment.newInstance(R.string.dialog_title_login);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);

        binding.toolbar.setTitle(getResources().getString(R.string.activity_login));
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
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
        if (event.success) {
            dialogFragment.changeMessage(getResources().getString(R.string.dialog_title_play_data));

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_login_successful), Toast.LENGTH_SHORT).show();

            //Get all data and set db.

            // TODO: 取得パーセント表示

            deferred.when(() -> {
                PlayDataUseCase playDataUseCase = new PlayDataUseCase();
                playDataUseCase.setPlayData();
            }).done(callback -> {
                dialogFragment.changeMessage(getResources().getString(R.string.dialog_title_music_data));
                MusicDataUseCase musicDataUseCase = new MusicDataUseCase();
                musicDataUseCase.setMusicData();
            });

        } else {
            dialogFragment.dismiss();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_login_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }

        return false;
    }

    public void onClickLoginButton(View view) {
        dialogFragment.show(getFragmentManager(), "dialog_fragment");

        LoginUseCase loginUseCase = new LoginUseCase();
        loginUseCase.checkLogin(binding.editTextSerialNo.getText().toString(), binding.editTextPassword.getText().toString());
    }

    public void onEvent(PlayDataUseCase.SetPlayData event) {
        if(event.success) {
            Log.d("ktr", "playdata success");
        } else {
            Toast.makeText(this, "プレイデータの取得に失敗しました" + event.message, Toast.LENGTH_SHORT).show();
        }
    }

    public void onEvent(MusicDataUseCase.SetMusicData event) {
        if(event.success) {
            Log.d("ktr", "MusicData Success");
            dialogFragment.dismiss();
            finish();
        } else {
            Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
        }
    }

}
