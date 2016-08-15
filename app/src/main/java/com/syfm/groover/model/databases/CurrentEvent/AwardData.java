package com.syfm.groover.model.databases.CurrentEvent;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by lycoris on 2016/08/15.
 */

@RealmClass
public class AwardData extends RealmObject {
    private RealmList<AvatarAward> avatarAwards;
    private RealmList<ItemAward> itemAwards;
    private RealmList<MusicAward> musicAwards;
    private RealmList<TitleAward> titleAwards;

    public RealmList<AvatarAward> getAvatarAwards() {
        return avatarAwards;
    }

    public void setAvatarAwards(RealmList<AvatarAward> avatarAwards) {
        this.avatarAwards = avatarAwards;
    }

    public RealmList<ItemAward> getItemAwards() {
        return itemAwards;
    }

    public void setItemAwards(RealmList<ItemAward> itemAwards) {
        this.itemAwards = itemAwards;
    }

    public RealmList<MusicAward> getMusicAwards() {
        return musicAwards;
    }

    public void setMusicAwards(RealmList<MusicAward> musicAwards) {
        this.musicAwards = musicAwards;
    }

    public RealmList<TitleAward> getTitleAwards() {
        return titleAwards;
    }

    public void setTitleAwards(RealmList<TitleAward> titleAwards) {
        this.titleAwards = titleAwards;
    }
}
