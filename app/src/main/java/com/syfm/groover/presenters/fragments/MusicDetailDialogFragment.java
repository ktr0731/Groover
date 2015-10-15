package com.syfm.groover.presenters.fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.data.storage.databases.MusicData;
import com.syfm.groover.data.storage.databases.ResultData;
import com.syfm.groover.data.storage.databases.UserRank;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lycoris on 2015/10/12.
 */
public class MusicDetailDialogFragment extends DialogFragment {

    private MusicData musicData;
    private List<ResultData> resultData;
    private List<UserRank> userRank;
    private ResultData simple;
    private ResultData normal;
    private ResultData hard;
    private ResultData extra;

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

    @OnClick(R.id.bottom_close)
    public void onClickCloseBotton() {
        dismiss();
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        // DialogFragmentをタイトル無しに
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 背景を透明に
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent_glay);

        int id = getArguments().getInt("music_id");

        if(id == 0)
            return dialog;

        musicData = MusicData.getMusicDataSingle(id);
        
        resultData = MusicData.getAllResultData(musicData);
        simple = resultData.get(0);
        normal = resultData.get(1);
        hard   = resultData.get(2);
        extra  = resultData.get(3);
        
        userRank = MusicData.getAllRank(musicData);

        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog dialog = getDialog();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_dialog_music_detail, null);
        ButterKnife.bind(this, view);

        Bitmap bmp = BitmapFactory.decodeByteArray(musicData.music_thumbnail, 0, musicData.music_thumbnail.length);
        iv_thumb.setImageBitmap(bmp);

        tv_title.setText(musicData.music_title);
        tv_artist.setText(musicData.artist);

        String numOfPlays = String.valueOf(simple.play_count + normal.play_count + hard.play_count + extra.play_count);
        tv_num_of_plays.setText(numOfPlays);
        tv_last_played.setText(musicData.last_play_time);
        tv_skin.setText(musicData.skin_name);


        tv_simple_score.setText(String.valueOf(simple.score));
        tv_simple_num_of_plays.setText(String.valueOf(simple.play_count));
        tv_simple_max_chain.setText(String.valueOf(simple.max_chain));
        tv_simple_num_of_full_chain.setText(String.valueOf(simple.full_chain));
        tv_simple_num_of_no_miss.setText(String.valueOf(simple.no_miss));
        tv_simple_rating.setText(simple.rating);

        if(simple.score == 1000000) {
            tv_simple_mark.setBackgroundResource(R.drawable.excellect_border);
            tv_simple_mark.setText("EXCELLENT");
        } else if(simple.full_chain > 0) {
            tv_simple_mark.setBackgroundResource(R.drawable.full_chain_border);
            tv_simple_mark.setText("FULL CHAIN");
        } else if(simple.no_miss > 0) {
            tv_simple_mark.setBackgroundResource(R.drawable.no_miss_border);
            tv_simple_mark.setText("NO MISS");
        } else if(simple.is_clear_mark.equals("true")) {
            tv_simple_mark.setBackgroundResource(R.drawable.clear_border);
            tv_simple_mark.setText("CLEAR");
        } else if(simple.is_failed_mark.equals("true")) {
            tv_simple_mark.setBackgroundResource(R.drawable.failed_border);
            tv_simple_mark.setText("FAILED");
        }

        tv_normal_score.setText(String.valueOf(normal.score));
        tv_normal_num_of_plays.setText(String.valueOf(normal.play_count));
        tv_normal_max_chain.setText(String.valueOf(normal.max_chain));
        tv_normal_num_of_full_chain.setText(String.valueOf(normal.full_chain));
        tv_normal_num_of_no_miss.setText(String.valueOf(normal.no_miss));
        tv_normal_rating.setText(normal.rating);

        if(normal.score == 1000000) {
            tv_normal_mark.setBackgroundResource(R.drawable.excellect_border);
            tv_normal_mark.setText("EXCELLENT");
        } else if(normal.full_chain > 0) {
            tv_normal_mark.setBackgroundResource(R.drawable.full_chain_border);
            tv_normal_mark.setText("FULL CHAIN");
        } else if(normal.no_miss > 0) {
            tv_normal_mark.setBackgroundResource(R.drawable.no_miss_border);
            tv_normal_mark.setText("NO MISS");
        } else if(normal.is_clear_mark.equals("true")) {
            tv_normal_mark.setBackgroundResource(R.drawable.clear_border);
            tv_normal_mark.setText("CLEAR");
        } else if(normal.is_failed_mark.equals("true")) {
            tv_normal_mark.setBackgroundResource(R.drawable.failed_border);
            tv_normal_mark.setText("FAILED");
        }

        tv_hard_score.setText(String.valueOf(hard.score));
        tv_hard_num_of_plays.setText(String.valueOf(hard.play_count));
        tv_hard_max_chain.setText(String.valueOf(hard.max_chain));
        tv_hard_num_of_full_chain.setText(String.valueOf(hard.full_chain));
        tv_hard_num_of_no_miss.setText(String.valueOf(hard.no_miss));
        tv_hard_rating.setText(hard.rating);

        if(hard.score == 1000000) {
            tv_hard_mark.setBackgroundResource(R.drawable.excellect_border);
            tv_hard_mark.setText("EXCELLENT");
        } else if(hard.full_chain > 0) {
            tv_hard_mark.setBackgroundResource(R.drawable.full_chain_border);
            tv_hard_mark.setText("FULL CHAIN");
        } else if(hard.no_miss > 0) {
            tv_hard_mark.setBackgroundResource(R.drawable.no_miss_border);
            tv_hard_mark.setText("NO MISS");
        } else if(hard.is_clear_mark.equals("true")) {
            tv_hard_mark.setBackgroundResource(R.drawable.clear_border);
            tv_hard_mark.setText("CLEAR");
        } else if(hard.is_failed_mark.equals("true")) {
            tv_hard_mark.setBackgroundResource(R.drawable.failed_border);
            tv_hard_mark.setText("FAILED");
        }

        if(musicData.ex_flag > 0) {

            cv_extra.setVisibility(View.VISIBLE);
            tv_extra_score.setText(String.valueOf(extra.score));
            tv_extra_num_of_plays.setText(String.valueOf(extra.play_count));
            tv_extra_max_chain.setText(String.valueOf(extra.max_chain));
            tv_extra_num_of_full_chain.setText(String.valueOf(extra.full_chain));
            tv_extra_num_of_no_miss.setText(String.valueOf(extra.no_miss));
            tv_extra_rating.setText(extra.rating);

            if(extra.score == 1000000) {
                tv_extra_mark.setBackgroundResource(R.drawable.excellect_border);
                tv_extra_mark.setText("EXCELLENT");
            } else if(extra.full_chain > 0) {
                tv_extra_mark.setBackgroundResource(R.drawable.full_chain_border);
                tv_extra_mark.setText("FULL CHAIN");
            } else if(extra.no_miss > 0) {
                tv_extra_mark.setBackgroundResource(R.drawable.no_miss_border);
                tv_extra_mark.setText("NO MISS");
            } else if(extra.is_clear_mark.equals("true")) {
                tv_extra_mark.setBackgroundResource(R.drawable.clear_border);
                tv_extra_mark.setText("CLEAR");
            } else if(extra.is_failed_mark.equals("true")) {
                tv_extra_mark.setBackgroundResource(R.drawable.failed_border);
                tv_extra_mark.setText("FAILED");
            }

        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }


}
