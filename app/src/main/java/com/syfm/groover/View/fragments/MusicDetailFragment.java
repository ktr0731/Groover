package com.syfm.groover.view.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syfm.groover.R;
import com.syfm.groover.databinding.FragmentMusicDetailBinding;
import com.syfm.groover.model.constants.Const;
import com.syfm.groover.model.databases.Music;

import io.realm.Realm;

/**
 * Created by lycoris on 2016/01/10.
 */
public class MusicDetailFragment extends Fragment {

    private Music music;

    private FragmentMusicDetailBinding binding;
    Realm realm = Realm.getDefaultInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_music_detail, group, false);

        Intent i = getActivity().getIntent();
        int id = i.getIntExtra(Const.INTENT_MUSIC_ID, 0);

        if (id == 0) {
            getActivity().finish();
        }

        bindMusicData(id);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    private void bindMusicData(int id) {
        music = realm.where(Music.class).equalTo(Const.MUSIC_LIST_MUSIC_ID, id).findFirst();
        binding.setMusic(music);
        binding.setSimple(music.getSimpleResult());
        binding.setNormal(music.getNormalResult());
        binding.setHard(music.getHardResult());
        binding.setExtra(music.getExtraResult());


        Bitmap bmp = BitmapFactory.decodeByteArray(music.getThumbnail(), 0, music.getThumbnail().length);
        binding.tvMusicDetailThumbnail.setImageBitmap(bmp);

        // TODO: FULL CHAIN等でアイコン表示
        if (music.getSimpleResult() != null) {
            if (music.getSimpleResult().getPerfect() > 0) {
                binding.tvMusicDetailSimpleMark.setBackgroundResource(R.drawable.perfect_border);
                binding.tvMusicDetailSimpleMark.setText("PERFECT");
            } else if (music.getSimpleResult().getFullChain() > 0) {
                binding.tvMusicDetailSimpleMark.setBackgroundResource(R.drawable.full_chain_border);
                binding.tvMusicDetailSimpleMark.setText("FULL CHAIN");
            } else if (music.getSimpleResult().getNoMiss() > 0) {
                binding.tvMusicDetailSimpleMark.setBackgroundResource(R.drawable.no_miss_border);
                binding.tvMusicDetailSimpleMark.setText("NO MISS");
            } else if (!music.getSimpleResult().isClear()) {
                binding.tvMusicDetailSimpleMark.setBackgroundResource(R.drawable.failed_border);
                binding.tvMusicDetailSimpleMark.setText("FAILED");
            }
        }

        if (music.getNormalResult() != null) {
            if (music.getNormalResult().getPerfect() > 0) {
                binding.tvMusicDetailNormalMark.setBackgroundResource(R.drawable.perfect_border);
                binding.tvMusicDetailNormalMark.setText("PERFECT");
            } else if (music.getNormalResult().getFullChain() > 0) {
                binding.tvMusicDetailNormalMark.setBackgroundResource(R.drawable.full_chain_border);
                binding.tvMusicDetailNormalMark.setText("FULL CHAIN");
            } else if (music.getNormalResult().getNoMiss() > 0) {
                binding.tvMusicDetailNormalMark.setBackgroundResource(R.drawable.no_miss_border);
                binding.tvMusicDetailNormalMark.setText("NO MISS");
            } else if (!music.getNormalResult().isClear()) {
                binding.tvMusicDetailNormalMark.setBackgroundResource(R.drawable.failed_border);
                binding.tvMusicDetailNormalMark.setText("FAILED");
            }
        }

        if (music.getHardResult() != null) {
            if (music.getHardResult().getPerfect() > 0) {
                binding.tvMusicDetailHardMark.setBackgroundResource(R.drawable.perfect_border);
                binding.tvMusicDetailHardMark.setText("PERFECT");
            } else if (music.getHardResult().getFullChain() > 0) {
                binding.tvMusicDetailHardMark.setBackgroundResource(R.drawable.full_chain_border);
                binding.tvMusicDetailHardMark.setText("FULL CHAIN");
            } else if (music.getHardResult().getNoMiss() > 0) {
                binding.tvMusicDetailHardMark.setBackgroundResource(R.drawable.no_miss_border);
                binding.tvMusicDetailHardMark.setText("NO MISS");
            } else if (!music.getHardResult().isClear()) {
                binding.tvMusicDetailHardMark.setBackgroundResource(R.drawable.failed_border);
                binding.tvMusicDetailHardMark.setText("FAILED");
            }
        }

        if (music.getExtraResult() != null) {
            if (music.getExtraResult().getPerfect() > 0 && music.isExFlag()) {
                binding.tvMusicDetailExtraMark.setBackgroundResource(R.drawable.perfect_border);
                binding.tvMusicDetailExtraMark.setText("PERFECT");
            } else if (music.getExtraResult().getFullChain() > 0 && music.isExFlag()) {
                binding.tvMusicDetailExtraMark.setBackgroundResource(R.drawable.full_chain_border);
                binding.tvMusicDetailExtraMark.setText("FULL CHAIN");
            } else if (music.getExtraResult().getNoMiss() > 0 && music.isExFlag()) {
                binding.tvMusicDetailExtraMark.setBackgroundResource(R.drawable.no_miss_border);
                binding.tvMusicDetailExtraMark.setText("NO MISS");
            } else if (!music.getExtraResult().isClear()) {
                binding.tvMusicDetailExtraMark.setBackgroundResource(R.drawable.failed_border);
                binding.tvMusicDetailExtraMark.setText("FAILED");
            }
        }
    }
}
