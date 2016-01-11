package com.syfm.groover.presenters.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.data.network.AppController;
import com.syfm.groover.data.storage.Const;
import com.syfm.groover.data.storage.databases.MusicData;
import com.syfm.groover.data.storage.databases.ResultData;
import com.syfm.groover.data.storage.databases.UserRank;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_detail, group, false);
        ButterKnife.bind(this, view);

        Intent i = getActivity().getIntent();
        int id = i.getIntExtra(Const.INTENT_MUSIC_ID, 0);

        if (id == 0) {
            getActivity().finish();
        }
        setMusicData(id);
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void setMusicData(int id) {

        musicData = realm.where(MusicData.class).equalTo(Const.MUSIC_LIST_MUSIC_ID, id).findFirst();

        simple = musicData.getResult_data().get(0);
        normal = musicData.getResult_data().get(1);
        hard = musicData.getResult_data().get(2);
        extra = musicData.getResult_data().get(3);

        userRank = musicData.getUser_rank();Bitmap bmp = BitmapFactory.decodeByteArray(musicData.getMusic_thumbnail(), 0, musicData.getMusic_thumbnail().length);

        iv_thumb.setImageBitmap(bmp);

        tv_title.setText(musicData.getMusic_title());
        tv_artist.setText(musicData.getArtist());

        String numOfPlays = String.valueOf(simple.getPlay_count() + normal.getPlay_count() + hard.getPlay_count() + extra.getPlay_count());
        tv_num_of_plays.setText(numOfPlays);
        tv_last_played.setText(musicData.getLast_play_time());
        tv_skin.setText(musicData.getSkin_name());


        tv_simple_score.setText(String.valueOf(simple.getScore()));
        tv_simple_num_of_plays.setText(String.valueOf(simple.getPlay_count()));
        tv_simple_max_chain.setText(String.valueOf(simple.getMax_chain()));
        tv_simple_num_of_full_chain.setText(String.valueOf(simple.getFull_chain()));
        tv_simple_num_of_no_miss.setText(String.valueOf(simple.getNo_miss()));
        tv_simple_rating.setText(simple.getRating());

        if (simple.getScore() == 1000000) {
            tv_simple_mark.setBackgroundResource(R.drawable.excellect_border);
            tv_simple_mark.setText("EXCELLENT");
        } else if (simple.getFull_chain() > 0) {
            tv_simple_mark.setBackgroundResource(R.drawable.full_chain_border);
            tv_simple_mark.setText("FULL CHAIN");
        } else if (simple.getNo_miss() > 0) {
            tv_simple_mark.setBackgroundResource(R.drawable.no_miss_border);
            tv_simple_mark.setText("NO MISS");
        } else if (simple.getIs_clear_mark().equals("true")) {
            tv_simple_mark.setBackgroundResource(R.drawable.clear_border);
            tv_simple_mark.setText("CLEAR");
        } else if (simple.getIs_failed_mark().equals("true")) {
            tv_simple_mark.setBackgroundResource(R.drawable.failed_border);
            tv_simple_mark.setText("FAILED");
        }

        tv_normal_score.setText(String.valueOf(normal.getScore()));
        tv_normal_num_of_plays.setText(String.valueOf(normal.getPlay_count()));
        tv_normal_max_chain.setText(String.valueOf(normal.getMax_chain()));
        tv_normal_num_of_full_chain.setText(String.valueOf(normal.getFull_chain()));
        tv_normal_num_of_no_miss.setText(String.valueOf(normal.getNo_miss()));
        tv_normal_rating.setText(normal.getRating());

        if (normal.getScore() == 1000000) {
            tv_normal_mark.setBackgroundResource(R.drawable.excellect_border);
            tv_normal_mark.setText("EXCELLENT");
        } else if (normal.getFull_chain() > 0) {
            tv_normal_mark.setBackgroundResource(R.drawable.full_chain_border);
            tv_normal_mark.setText("FULL CHAIN");
        } else if (normal.getNo_miss() > 0) {
            tv_normal_mark.setBackgroundResource(R.drawable.no_miss_border);
            tv_normal_mark.setText("NO MISS");
        } else if (normal.getIs_clear_mark().equals("true")) {
            tv_normal_mark.setBackgroundResource(R.drawable.clear_border);
            tv_normal_mark.setText("CLEAR");
        } else if (normal.getIs_failed_mark().equals("true")) {
            tv_normal_mark.setBackgroundResource(R.drawable.failed_border);
            tv_normal_mark.setText("FAILED");
        }

        tv_hard_score.setText(String.valueOf(hard.getScore()));
        tv_hard_num_of_plays.setText(String.valueOf(hard.getPlay_count()));
        tv_hard_max_chain.setText(String.valueOf(hard.getMax_chain()));
        tv_hard_num_of_full_chain.setText(String.valueOf(hard.getFull_chain()));
        tv_hard_num_of_no_miss.setText(String.valueOf(hard.getNo_miss()));
        tv_hard_rating.setText(hard.getRating());

        if (hard.getScore() == 1000000) {
            tv_hard_mark.setBackgroundResource(R.drawable.excellect_border);
            tv_hard_mark.setText("EXCELLENT");
        } else if (hard.getFull_chain() > 0) {
            tv_hard_mark.setBackgroundResource(R.drawable.full_chain_border);
            tv_hard_mark.setText("FULL CHAIN");
        } else if (hard.getNo_miss() > 0) {
            tv_hard_mark.setBackgroundResource(R.drawable.no_miss_border);
            tv_hard_mark.setText("NO MISS");
        } else if (hard.getIs_clear_mark().equals("true")) {
            tv_hard_mark.setBackgroundResource(R.drawable.clear_border);
            tv_hard_mark.setText("CLEAR");
        } else if (hard.getIs_failed_mark().equals("true")) {
            tv_hard_mark.setBackgroundResource(R.drawable.failed_border);
            tv_hard_mark.setText("FAILED");
        }

        if (musicData.getEx_flag() > 0) {

            cv_extra.setVisibility(View.VISIBLE);
            tv_extra_score.setText(String.valueOf(extra.getScore()));
            tv_extra_num_of_plays.setText(String.valueOf(extra.getPlay_count()));
            tv_extra_max_chain.setText(String.valueOf(extra.getMax_chain()));
            tv_extra_num_of_full_chain.setText(String.valueOf(extra.getFull_chain()));
            tv_extra_num_of_no_miss.setText(String.valueOf(extra.getNo_miss()));
            tv_extra_rating.setText(extra.getRating());

            if (extra.getScore() == 1000000) {
                tv_extra_mark.setBackgroundResource(R.drawable.excellect_border);
                tv_extra_mark.setText("EXCELLENT");
            } else if (extra.getFull_chain() > 0) {
                tv_extra_mark.setBackgroundResource(R.drawable.full_chain_border);
                tv_extra_mark.setText("FULL CHAIN");
            } else if (extra.getNo_miss() > 0) {
                tv_extra_mark.setBackgroundResource(R.drawable.no_miss_border);
                tv_extra_mark.setText("NO MISS");
            } else if (extra.getIs_clear_mark().equals("true")) {
                tv_extra_mark.setBackgroundResource(R.drawable.clear_border);
                tv_extra_mark.setText("CLEAR");
            } else if (extra.getIs_failed_mark().equals("true")) {
                tv_extra_mark.setBackgroundResource(R.drawable.failed_border);
                tv_extra_mark.setText("FAILED");
            }

        }
    }
}
