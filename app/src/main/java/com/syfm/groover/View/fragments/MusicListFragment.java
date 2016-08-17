package com.syfm.groover.view.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.syfm.groover.R;
import com.syfm.groover.databinding.FragmentMusicListBinding;
import com.syfm.groover.model.constants.Const;
import com.syfm.groover.model.constants.SPConst;
import com.syfm.groover.model.databases.Music;
import com.syfm.groover.model.databases.SharedPreferenceHelper;
import com.syfm.groover.view.activities.MusicDetailActivity;
import com.syfm.groover.view.adapter.MusicListAdapter;
import com.syfm.groover.view.interfaces.MusicListInterface;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lycoris on 2015/10/03.
 */

public class MusicListFragment extends Fragment implements MusicListInterface {

    private FragmentMusicListBinding binding;
    private MusicListAdapter adapter;
    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_music_list, group, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        realm = Realm.getDefaultInstance();
        RealmResults<Music> musics = realm.where(Music.class).findAll();

        setMusicToAdapter(musics);

        binding.setListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //選択されたアイテムを取得
                Music item = (Music) binding.listView.getItemAtPosition(position);

                // ListViewの位置を記憶
                SharedPreferenceHelper.setMusicListViewPosition(
                        binding.listView.getFirstVisiblePosition(),
                        binding.listView.getChildAt(0).getTop()
                );

                Intent i = new Intent(getActivity(), MusicDetailActivity.class);
                i.putExtra(Const.INTENT_MUSIC_ID, item.getId());
                i.putExtra(Const.INTENT_EX_FLAG, item.isExFlag());
                startActivity(i);
            }
        });
    }

    @Override
    public void onStop() {
        realm.close();

        // 位置を記憶
        SharedPreferenceHelper.setMusicListViewPosition(binding.listView.getFirstVisiblePosition(), binding.listView.getChildAt(0).getTop());
        super.onStop();
    }

    public void onListItemClick(AdapterView<?> parent, View view, int position, long id) {
        //選択されたアイテムを取得
        Music item = (Music) binding.listView.getItemAtPosition(position);

        // ListViewの位置を記憶
        SharedPreferenceHelper.setMusicListViewPosition(
                binding.listView.getFirstVisiblePosition(),
                binding.listView.getChildAt(0).getTop()
        );

        Intent i = new Intent(getActivity(), MusicDetailActivity.class);
        i.putExtra(Const.INTENT_MUSIC_ID, item.getId());
        i.putExtra(Const.INTENT_EX_FLAG, item.isExFlag());
        startActivity(i);
    }

    // sort dialogからの通知
    // TODO: 独自クラスで返さないと誤受信する
    public void onEventMainThread(ArrayList<String> params) {
        adapter.sortList(params);
    }

    @Override
    public void searchMusic(String keyword) {
        adapter.getFilter().filter(keyword);
    }

    @Override
    public void setMusicToAdapter(RealmResults<Music> music) {
        if (music == null || music.isEmpty()) {
            return;
        }
        adapter = new MusicListAdapter(getActivity(), 0, music);
        binding.listView.setAdapter(adapter);

        setListViewPosition();

        adapter.sortList();
    }

    @Override
    public void setListViewPosition() {
        Map<String, Integer> positions = SharedPreferenceHelper.getMusicListViewPosition();
        if (!positions.isEmpty()) {
            binding.listView.setSelectionFromTop(
                    positions.get(SPConst.MUSIC_LIST_LIST_VIEW_POSITION),
                    positions.get(SPConst.MUSIC_LIST_LIST_VIEW_Y)
            );
        }
    }
}
