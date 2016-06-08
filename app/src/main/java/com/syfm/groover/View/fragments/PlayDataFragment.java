package com.syfm.groover.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syfm.groover.R;
import com.syfm.groover.databinding.FragmentPlayDataBinding;
import com.syfm.groover.model.databases.PlayerData;

import io.realm.Realm;

/**
 * Created by lycoris on 2015/09/22.
 */
public class PlayDataFragment extends Fragment {

    private FragmentPlayDataBinding binding;
    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_data, group, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = FragmentPlayDataBinding.bind(getView());
        binding.setFragment(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        realm = Realm.getDefaultInstance();
        PlayerData data = realm.where(PlayerData.class).findFirst();
        if (data == null) {
            Log.d("ktr", "event data is null");
            return;
        }

        binding.setPlayerData(data);
    }

    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
