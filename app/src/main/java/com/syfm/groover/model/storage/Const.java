package com.syfm.groover.model.storage;

/**
 * Created by lycoris on 2015/10/06.
 */
public class Const {

    /*
     *
     *      パラメータ名は必ず
     *          (API名) + (パラメータ名) とする(名前の衝突を防ぐため)
     *
     */


    /*               PlayData ( PlayerData + ShopSalesData + AverageScore + StageData )           */

    public final static String TABLE_NAME_PLAYER_DATA = "PlayerData";
    public final static String TABLE_NAME_SHOP_SALES_DATA = "ShopSalesData";
    public final static String TABLE_NAME_AVERAGE_SCORE = "AverageScores";
    public final static String TABLE_NAME_STAGE_DATA = "StageData";

    public final static String PLAYER_DATA_NAME = "player_name";
    public final static String PLAYER_DATA_AVATAR = "avatar";
    public final static String PLAYER_DATA_LEVEL = "level";
    public final static String PLAYER_DATA_TITLE = "title";
    public final static String PLAYER_DATA_TOTAL_SCORE = "total_score";
    public final static String PLAYER_DATA_TOTAL_PLAY_MUSIC = "total_play_music";
    public final static String PLAYER_DATA_TOTAL_MUSIC = "total_music";
    public final static String PLAYER_DATA_TOTAL_TROPHY = "total_trophy";
    public final static String PLAYER_DATA_RANK = "rank";
    public final static String PLAYER_DATA_DATE = "date";

    public final static String SHOP_SALES_DATA_COIN = "current_coin";

    public final static String AVERAGE_SCORE_AVERAGE_SCORE = "average_score";

    public final static String STAGE_DATA_ALL = "all_stage";
    public final static String STAGE_DATA_CLEAR = "clear";
    public final static String STAGE_DATA_FULL_CHAIN = "fullchain";
    public final static String STAGE_DATA_NO_MISS = "nomiss";
    public final static String STAGE_DATA_S = "s";
    public final static String STAGE_DATA_SS = "ss";
    public final static String STAGE_DATA_SSS = "sss";


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

    public final static String SP_LOGIN_NESICA_ID = "login_nesica_id";
    public final static String SP_LOGIN_PASSWORD  = "login_password";

    public final static String SP_MUSIC_LIST_LIST_VIEW_POSITION = "music_list_list_view_position";
    public final static String SP_MUSIC_LIST_LIST_VIEW_Y        = "music_list_list_view_y";
    public final static String SP_MUSIC_LIST_SORT_ORDER_BY = "music_list_order_by";
    public final static String SP_MUSIC_LIST_SORT_SORT_TYPE = "music_list_sort_type";

    public final static String SP_LEVEL_ALL_RANKING = "level_all_ranking";
    public final static String SP_LEVEL_SIMPLE_RANKING = "level_simple_ranking";
    public final static String SP_LEVEL_NORMAL_RANKING = "level_normal_ranking";
    public final static String SP_LEVEL_HARD_RANKING = "level_hard_ranking";
    public final static String SP_LEVEL_EXTRA_RANKING = "level_extra_ranking";


    public final static int RANKING_LEVEL = 0;
    public final static int RANKING_GENRE = 1;
    public final static int RANKING_EVENT = 2;

    //インスタンス化を禁止
    private Const() {}

}
