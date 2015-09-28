package com.syfm.groover.presenters.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syfm.groover.R;
import com.syfm.groover.business.usecases.PlayDataUseCase;

import de.greenrobot.event.EventBus;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (AppController.getInstance().checkLoginCookie()) {
            //Go to LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            int code = getResources().getInteger(R.integer.status_code_login);
            startActivityForResult(intent, code);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int position = 0;
        String[] tab_name = getResources().getStringArray(R.array.tab_name);

        if (bundle != null) {
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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (getResources().getInteger(R.integer.status_code_login) == requestCode) {

            Bundle bundle = new Bundle();
            bundle.putInt(CommonDialogFragment.FIELD_TITLE, R.string.dialog_title_login);
            bundle.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.dialog_title_login);
            bundle.putInt(CommonDialogFragment.FIELD_LAYOUT, R.layout.dialog_progress);
            //bundle.putBoolean(CommonDialogFragment.FIELD_PROGRESS_BAR, true);

            final CommonDialogFragment dialogFragment = new CommonDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getActivity().getSupportFragmentManager(), "getDataDialog");

            //Get all data and set db.
            //After that, get data from db.

            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PlayDataUseCase playDataUseCase = new PlayDataUseCase();
                    playDataUseCase.setPlayData();
                }
            }, 1000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //eventDataUseCase.setEventData();
                }
            }, 2500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //musicDataUseCase.setMusicData();
                }
            }, 4000);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //rankingDataUseCase.setRankingData();
                }
            }, 5500);
        }
    }

    public void onEvent(PlayDataUseCase.SetPlayData event) {
        if (event.success) {
            PlayDataUseCase useCase = new PlayDataUseCase();
            useCase.getPlayData();
        }
    }

    public void onEvent(PlayDataUseCase.PlayDataEvent event) {
        if (event != null) {
            PlayDataUseCase useCase = new PlayDataUseCase();
            useCase.getPlayData();
        }
    }

}
