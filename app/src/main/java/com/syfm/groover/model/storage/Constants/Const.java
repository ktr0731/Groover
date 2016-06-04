package com.syfm.groover.model.storage.Constants;

/**
 * Created by lycoris on 2015/10/06.
 */
public class Const {


    /*                      MusicData( MusicData + MusicDetail )                 */

    public final static String TABLE_NAME_MUSIC_DATA = "MusicData";
    public final static String TABLE_NAME_MUSIC_RESULT = "MusicResult";
    public final static String TABLE_NAME_MUSIC_RANK = "UserRank";

    public final static String MUSIC_RELATION_RESULT_SIMPLE = "simple_result_data";
    public final static String MUSIC_RELATION_RESULT_NORMAL = "normal_result_data";
    public final static String MUSIC_RELATION_RESULT_HARD = "hard_result_data";
    public final static String MUSIC_RELATION_RESULT_EXTRA = "extra_result_data";

    public final static String MUSIC_LIST_MUSIC_ID = "music_id";
    public final static String MUSIC_LIST_MUSIC_TITLE = "music_title";
    public final static String MUSIC_LIST_PLAY_COUNT = "play_count";
    public final static String MUSIC_LIST_LAST_PLAY_TIME = "last_play_time";

    public final static String MUSIC_DETAIL_ARTIST = "artist";
    public final static String MUSIC_DETAIL_EX_FLAG = "ex_flag";
    public final static String MUSIC_DETAIL_SKIN_NAME = "skin_name";

    public final static String MUSIC_RESULT_ADLIB = "adlib";
    public final static String MUSIC_RESULT_FULL_CHAIN = "full_chain";
    public final static String MUSIC_RESULT_IS_CLEAR_MARK = "is_clear_mark";
    public final static String MUSIC_RESULT_IS_FAILED_MARK = "is_failed_mark";
    public final static String MUSIC_RESULT_MAX_CHAIN = "max_chain";
    public final static String MUSIC_RESULT_NO_MISS = "no_miss";
    public final static String MUSIC_RESULT_RATING = "rating";
    public final static String MUSIC_RESULT_SCORE = "score";
    public final static String MUSIC_RESULT_LEVEL = "music_level";

    public final static String MUSIC_USER_RANK = "rank";

    public final static String MUSIC_THUMBNAIL = "music_thumbnail";

    public final static String MUSIC_SORT_MUSIC_NAME = "Music name";
    public final static String MUSIC_SORT_LAST_PLAYED = "Last played";
    public final static String MUSIC_SORT_PLAY_TIMES = "Play times";
    public final static String MUSIC_SORT_CATEGORY = "Category";

    public final static String INTENT_MUSIC_ID = "music_id";
    public final static String INTENT_EX_FLAG = "ex_flag";

    public final static String MUSIC_SCORE_RANKING_RANK = "rank";

    public final static String MUSIC_SCORE_DATA_DIFF = "diff";

    public final static int TIME = 500;

    public final static Boolean DEBUG_LOGIN = true;

    public final static int RANKING_LEVEL = 0;
    public final static int RANKING_GENRE = 1;
    public final static int RANKING_EVENT = 2;

    public final static String RANKING_GENRE_JPOP = "Pops";
    public final static String RANKING_GENRE_ANIME = "Anime";
    public final static String RANKING_GENRE_VOCALOID = "Vocaloid";
    public final static String RANKING_GENRE_TOHO = "Toho";
    public final static String RANKING_GENRE_GAME = "Game";
    public final static String RANKING_GENRE_VARIETY = "Variety";
    public final static String RANKING_GENRE_ORIGINAL = "Original";

    public final static String RANKING_DATA_RANK = "rank";
    public final static String RANKING_DATA_RANK2 = "rank2";
    public final static String RANKING_DATA_PLAYER_NAME = "player_name";
    public final static String RANKING_DATA_SCORE_BI1 = "score_bi1";
    public final static String RANKING_DATA_PCOL3 = "pcol3";
    public final static String RANKING_DATA_FCOL1 = "fcol1";
    public final static String RANKING_DATA_LAST_PLAY_TENPO_ID = "last_play_tenpo_id";
    public final static String RANKING_DATA_TENPO_NAME = "tenpo_name";
    public final static String RANKING_DATA_PREF_ID = "pref_id";
    public final static String RANKING_DATA_PREF = "pref";
    public final static String RANKING_DATA_AREA_ID = "area_id";
    public final static String RANKING_DATA_AREA = "area";
    public final static String RANKING_DATA_TITLE = "title";

    public final static String RANKING_DATA_PCOL1 = "pcol1";
    public final static String RANKING_DATA_SCORE_i1 = "score_i1";
    public final static String RANKING_DATA_FCOL2 = "fcol2";
    public final static String RANKING_DATA_GENRE = "genre";
    
    public final static String RANKING_DATA_ROW_TAG = "data";

    public final static String EVENT_NAME_ID = "event_id";
    public final static String EVENT_NAME_TITLE = "title";
    public final static String EVENT_NAME_COMMENT = "comment";
    public final static String EVENT_NAME_OPEN_DATE = "open_date";
    public final static String EVENT_NAME_CLOSE_DATE = "close_date";
    public final static String EVENT_NAME_OPEN_TIME = "open_time";
    public final static String EVENT_NAME_CLOSE_TIME = "close_time";
    public final static String EVENT_NAME_USE_FLAG = "use_flag";
    public final static String EVENT_NAME_VERSION = "version";
    public final static String EVENT_NAME_REGION = "region";
    public final static String EVENT_NAME_SCORE_TYPE = "score_type";
    public final static String EVENT_NAME_SPECIFIED_NUM = "specified_num";
    public final static String EVENT_NAME_CHALLENGE_NUM = "challenge_num";
    public final static String EVENT_NAME_REACHED_SCORE = "reached_score";

    public final static String EVENT_NAME_ROW_TAG = "data";

    //インスタンス化を禁止
    private Const() {}

}
