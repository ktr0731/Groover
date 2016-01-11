package com.syfm.groover.data.storage.databases;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by lycoris on 2016/01/11.
 */
public class ScoreRank extends RealmObject {
    private int music_id;
    private RealmList<ScoreRankData> simple_rank;
    private RealmList<ScoreRankData> normal_rank;
    private RealmList<ScoreRankData> hard_rank;
    private RealmList<ScoreRankData> extra_rank;

    public int getMusic_id() {
        return music_id;
    }

    public void setMusic_id(int music_id) {
        this.music_id = music_id;
    }

    public RealmList<ScoreRankData> getSimple_rank() {
        return simple_rank;
    }

    public void setSimple_rank(RealmList<ScoreRankData> simple_rank) {
        this.simple_rank = simple_rank;
    }

    public RealmList<ScoreRankData> getNormal_rank() {
        return normal_rank;
    }

    public void setNormal_rank(RealmList<ScoreRankData> normal_rank) {
        this.normal_rank = normal_rank;
    }

    public RealmList<ScoreRankData> getHard_rank() {
        return hard_rank;
    }

    public void setHard_rank(RealmList<ScoreRankData> hard_rank) {
        this.hard_rank = hard_rank;
    }

    public RealmList<ScoreRankData> getExtra_rank() {
        return extra_rank;
    }

    public void setExtra_rank(RealmList<ScoreRankData> extra_rank) {
        this.extra_rank = extra_rank;
    }
}
