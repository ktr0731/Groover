package com.syfm.groover.model.databases.CurrentEvent;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2016/08/15.
 */

@RealmClass
public class AwardData extends RealmObject {
    // String arrayは未サポートのため、その場合は自分で格納する必要あり

    private RealmList<AvatarAward> avatar_award;
    private RealmList<ItemAward> item_award;
    private RealmList<MusicAward> music_award;
    private RealmList<TitleAward> title_award;

    public RealmList<AvatarAward> getAvatar_award() {
        return avatar_award;
    }

    public void setAvatar_award(RealmList<AvatarAward> avatar_award) {
        this.avatar_award = avatar_award;
    }

    public RealmList<ItemAward> getItem_award() {
        return item_award;
    }

    public void setItem_award(RealmList<ItemAward> item_award) {
        this.item_award = item_award;
    }

    public RealmList<MusicAward> getMusic_award() {
        return music_award;
    }

    public void setMusic_award(RealmList<MusicAward> music_award) {
        this.music_award = music_award;
    }

    public RealmList<TitleAward> getTitle_award() {
        return title_award;
    }

    public void setTitle_award(RealmList<TitleAward> title_award) {
        this.title_award = title_award;
    }
}
