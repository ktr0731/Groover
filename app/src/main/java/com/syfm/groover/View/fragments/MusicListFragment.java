package com.syfm.groover.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.syfm.groover.R;
import com.syfm.groover.controller.usecases.MusicDataUseCase;
import com.syfm.groover.model.storage.Const;
import com.syfm.groover.model.storage.databases.MusicData;
import com.syfm.groover.view.activities.MusicDetailActivity;
import com.syfm.groover.view.adapter.MusicListAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;

/**
 * Created by lycoris on 2015/10/03.
 */

public class MusicListFragment extends Fragment {

    private ViewPager pager;
    private MusicListAdapter adapter;

    @Bind(R.id.list_view)
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MusicDataUseCase musicDataUseCase = new MusicDataUseCase();
        //musicDataUseCase.getMusicData();
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

    @OnItemClick(R.id.list_view)
    public void OnListItemClick(AdapterView<?> parent, View view, int position, long id) {
        //選択されたアイテムを取得
        ListView listView = (ListView) parent;
        MusicData item = (MusicData) listView.getItemAtPosition(position);

        //IDをつける
        Bundle bundle = new Bundle();
        bundle.putInt("music_id", item.getMusic_id());

        Intent i = new Intent(getActivity(), MusicDetailActivity.class);
        i.putExtra(Const.INTENT_MUSIC_ID, item.getMusic_id());
        i.putExtra(Const.INTENT_EX_FLAG, item.getEx_flag());
        startActivity(i);
    }

    public void onEvent(MusicDataUseCase.SetMusicData event) {
        if (event.success) {

        } else {

        }
    }

    public void onEventMainThread(MusicDataUseCase.MusicDataEvent event) {
        if (event.musicData == null || event.musicData.isEmpty()) {
            return;
        }
        adapter = new MusicListAdapter(getActivity(), 0, event.musicData, true);
        listView.setAdapter(adapter);
    }


    // sort dialogからの通知
    public void onEventMainThread(List<String> params) {
        adapter.sortList(params);
    }

    public void searchMusic(String keyword) {
        adapter.getFilter().filter(keyword);
    }

}
