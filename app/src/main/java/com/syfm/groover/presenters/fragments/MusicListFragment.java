package com.syfm.groover.presenters.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.syfm.groover.R;
import com.syfm.groover.business.usecases.MusicDataUseCase;
import com.syfm.groover.business.usecases.PlayDataUseCase;
import com.syfm.groover.data.network.AppController;
import com.syfm.groover.presenters.adapter.MusicListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/10/03.
 */

public class MusicListFragment extends Fragment {

    private MusicListAdapter adapter;

    @Bind(R.id.list_view)
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MusicDataUseCase musicDataUseCase = new MusicDataUseCase();
        musicDataUseCase.getMusicData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_list, group, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        new MusicDataUseCase().getMusicData();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEventMainThread(MusicDataUseCase.MusicDataEvent event) {
        if(event.resultData == null) {
            return;
        }
        MusicListAdapter adapter = new MusicListAdapter(getActivity(), 0, event.resultData, AppController.getInstance().getRequestQueue());
        listView.setAdapter(adapter);
    }
}
