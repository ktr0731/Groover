package com.syfm.groover.view.interfaces;

import android.view.View;
import android.widget.AdapterView;

import com.syfm.groover.model.databases.Music;

import io.realm.RealmResults;

/**
 * Created by lycoris on 2016/06/08.
 */

public interface MusicListInterface {
    void setMusicToAdapter(RealmResults<Music> music);
    void searchMusic(String keyword);
    void setListViewPosition();
}
