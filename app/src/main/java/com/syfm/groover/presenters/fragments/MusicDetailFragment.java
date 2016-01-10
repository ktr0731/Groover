package com.syfm.groover.presenters.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.business.usecases.PlayDataUseCase;
import com.syfm.groover.data.network.AppController;
import com.syfm.groover.data.storage.Const;
import com.syfm.groover.data.storage.databases.MusicData;
import com.syfm.groover.data.storage.databases.ResultData;
import com.syfm.groover.data.storage.databases.UserRank;

import java.net.URISyntaxException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import io.realm.Realm;

/**
 * Created by lycoris on 2016/01/10.
 */
public class MusicDetailFragment extends Fragment {

    private MusicData musicData;
    private List<ResultData> resultData;
    private List<UserRank> userRank;
    private ResultData simple;
    private ResultData normal;
    private ResultData hard;
    private ResultData extra;
    private Realm realm = Realm.getInstance(AppController.getInstance());

    @Bind(R.id.tv_music_detail_thumbnail)
    com.syfm.groover.presenters.activities.RoundImageView iv_thumb;
    @Bind(R.id.tv_music_detail_title)
    TextView tv_title;
    @Bind(R.id.tv_music_detail_artist)
    TextView tv_artist;

    @Bind(R.id.tv_music_detail_num_of_plays)
    TextView tv_num_of_plays;
    @Bind(R.id.tv_music_detail_last_played)
    TextView tv_last_played;
    @Bind(R.id.tv_music_detail_skin)
    TextView tv_skin;

    @Bind(R.id.tv_music_detail_simple_score)
    TextView tv_simple_score;
    @Bind(R.id.tv_music_detail_simple_mark)
    TextView tv_simple_mark;
    @Bind(R.id.tv_music_detail_simple_rating)
    TextView tv_simple_rating;
    @Bind(R.id.tv_music_detail_simple_max_chain)
    TextView tv_simple_max_chain;
    @Bind(R.id.tv_music_detail_simple_num_of_plays)
    TextView tv_simple_num_of_plays;
    @Bind(R.id.tv_music_detail_simple_num_of_full_chain)
    TextView tv_simple_num_of_full_chain;
    @Bind(R.id.tv_music_detail_simple_num_of_no_miss)
    TextView tv_simple_num_of_no_miss;

    @Bind(R.id.tv_music_detail_normal_score)
    TextView tv_normal_score;
    @Bind(R.id.tv_music_detail_normal_mark)
    TextView tv_normal_mark;
    @Bind(R.id.tv_music_detail_normal_rating)
    TextView tv_normal_rating;
    @Bind(R.id.tv_music_detail_normal_max_chain)
    TextView tv_normal_max_chain;
    @Bind(R.id.tv_music_detail_normal_num_of_plays)
    TextView tv_normal_num_of_plays;
    @Bind(R.id.tv_music_detail_normal_num_of_full_chain)
    TextView tv_normal_num_of_full_chain;
    @Bind(R.id.tv_music_detail_normal_num_of_no_miss)
    TextView tv_normal_num_of_no_miss;

    @Bind(R.id.tv_music_detail_hard_score)
    TextView tv_hard_score;
    @Bind(R.id.tv_music_detail_hard_mark)
    TextView tv_hard_mark;
    @Bind(R.id.tv_music_detail_hard_rating)
    TextView tv_hard_rating;
    @Bind(R.id.tv_music_detail_hard_max_chain)
    TextView tv_hard_max_chain;
    @Bind(R.id.tv_music_detail_hard_num_of_plays)
    TextView tv_hard_num_of_plays;
    @Bind(R.id.tv_music_detail_hard_num_of_full_chain)
    TextView tv_hard_num_of_full_chain;
    @Bind(R.id.tv_music_detail_hard_num_of_no_miss)
    TextView tv_hard_num_of_no_miss;

    @Bind(R.id.card_view_extra)
    CardView cv_extra;
    @Bind(R.id.tv_music_detail_extra_score)
    TextView tv_extra_score;
    @Bind(R.id.tv_music_detail_extra_mark)
    TextView tv_extra_mark;
    @Bind(R.id.tv_music_detail_extra_rating)
    TextView tv_extra_rating;
    @Bind(R.id.tv_music_detail_extra_max_chain)
    TextView tv_extra_max_chain;
    @Bind(R.id.tv_music_detail_extra_num_of_plays)
    TextView tv_extra_num_of_plays;
    @Bind(R.id.tv_music_detail_extra_num_of_full_chain)
    TextView tv_extra_num_of_full_chain;
    @Bind(R.id.tv_music_detail_extra_num_of_no_miss)
    TextView tv_extra_num_of_no_miss;

    @Bind(R.id.bottom_close)
    android.support.design.widget.FloatingActionButton btn_close;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
//            Intent i = Intent.parseUri(this, Intent.URI_INTENT_SCHEME);
//        } catch (URISyntaxException e) {
//            Log.d("URISyntaxException", e.toString());
//        }
//
//        i.getString
//
//        musicData = realm.where(MusicData.class).equalTo(Const.MUSIC_LIST_MUSIC_ID, id).findFirst();
//
//        simple = musicData.getResult_data().get(0);
//        normal = musicData.getResult_data().get(1);
//        hard   = musicData.getResult_data().get(2);
//        extra  = musicData.getResult_data().get(3);
//
//        userRank = musicData.getUser_rank();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_detail, group, false);
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
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
