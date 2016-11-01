package com.syfm.groover.model.api

import android.util.Log

import com.syfm.groover.controller.entities.AppController
import com.syfm.groover.model.constants.Const
import com.syfm.groover.model.constants.SPConst
import com.syfm.groover.model.databases.SharedPreferenceHelper
import com.syfm.groover.model.databases.ScoreRankData

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import io.realm.Realm
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody


/**
 * Created by lycoris on 2015/09/27.
 */
class ApiClient {
    private var realm: Realm? = null

    var client: ClientInterface = object : ClientInterface {
        @Throws(IOException::class, InvalidResponseException::class)
        override fun sendRequest(target_url: String): String {
            val request = okhttp3.Request.Builder().url(target_url).get().build()

            val response = AppController.getOkHttpClient().newCall(request).execute()
            if (!response.isSuccessful) {
                Log.d("HttpRequest", "Bad Response")
                throw InvalidResponseException(response.message())
            }

            val body = response.body()

            val json = body.string()
            body.close()

            return json
        }

        // Javaとの互換性のためだけ
        @Throws(IOException::class, InvalidResponseException::class)
        override fun sendRequestForByteArray(target_url: String): ByteArray {
            val request = okhttp3.Request.Builder().url(target_url).get().build()

            val response = AppController.getOkHttpClient().newCall(request).execute()
            if (!response.isSuccessful) {
                Log.d("HttpRequest", "Bad Response")
                throw InvalidResponseException(response.message())
            }

            val body = response.body()

            val bytes = body.bytes()
            body.close()

            return bytes
        }
    }

    /**
     * Fetches player_data.php from mypage of Groove Coaster.

     * @return @{code JSONObject} Response JSON from player_data.php
     * *
     * @throws IOException
     * *
     * @throws JSONException
     */
    @Throws(IOException::class, JSONException::class)
    fun fetchPlayerData(): JSONObject {
        Log.d("PlayDataApi", "fetchPlayerData")
        val url = "https://mypage.groovecoaster.jp/sp/json/player_data.php"

        val jsonString = client.sendRequest(url)
        val `object` = JSONObject(jsonString)

        checkAuthorization(`object`)

        return `object`
    }

    /**
     * Fetches shop_sales_data.php from mypage of Groove Coaster.

     * @return @{code JSONObject} Response json from shop_sales_data.php
     * *
     * @throws IOException
     * *
     * @throws JSONException
     */
    @Throws(IOException::class, JSONException::class)
    fun fetchShopSalesData(): JSONObject {
        Log.d("PlayDataApi", "fetchShopSalesData")
        val url = "https://mypage.groovecoaster.jp/sp/json/shop_sales_data.php"

        val `object` = JSONObject(client.sendRequest(url))

        checkAuthorization(`object`)

        return `object`
    }

    /**
     * MusicData API
     */

    /**
     * Fetches music_list.php from mypage of Groove Coaster.

     * @throws IOException
     * *
     * @throws JSONException
     */
    @Throws(IOException::class, JSONException::class)
    fun fetchMusicList(): JSONArray {
        val url = "https://mypage.groovecoaster.jp/sp/json/music_list.php"
        val music_list_data = "music_list"

        val jsonString = client.sendRequest(url)
        val `object` = JSONObject(jsonString)
        checkAuthorization(`object`)

        return `object`.getJSONArray(music_list_data)
    }

    /**
     * Fetches music_detail.php from mypage of Groove Coaster.

     * @return `JSONObject` if fetching was successfully, `null` if cannot fetches data.
     * *
     * @throws IOException
     * *
     * @throws JSONException
     */
    @Throws(IOException::class, JSONException::class)
    fun fetchMusicDetail(music_id: Int): JSONObject {
        var url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id="

        url += music_id

        val jsonString = client.sendRequest(url)
        val `object` = JSONObject(jsonString)
        checkAuthorization(`object`)

        return `object`.getJSONObject("music_detail")
    }

    /**
     * Fetches music_image from mypage of Groove Coaster.

     * @param music_id The id of the music's
     * *
     * @return `byte[]` if fetching was successfully, `null` fetching was failed.
     * *
     * @throws IOException
     */
    @Throws(IOException::class, RuntimeException::class)
    fun fetchMusicThumbnail(music_id: Int): ByteArray {
        var url = "https://mypage.groovecoaster.jp/sp/music/music_image.php?music_id="
        url += music_id

        val bytes = client.sendRequestForByteArray(url)

        if (bytes.size <= 0) {
            Log.d("ktr", "thumb error")
            throw RuntimeException("サムネイルの取得に失敗しました。")
        }

        return bytes
    }

    /**
     * Fetches score_ranking.php of the target music id.

     * @param id   The target music's id
     * *
     * @param diff A difficulty of target music
     */
    fun fetchScoreRanking(id: String, diff: Int) {
        val url = "https://mypage.groovecoaster.jp/sp/json/score_ranking_bymusic_bydifficulty.php?music_id=$id&difficulty=$diff"

        realm = Realm.getDefaultInstance()

        val score_rank = "score_rank"

        val request = okhttp3.Request.Builder().url(url).get().build()

        try {
            val response = AppController.getOkHttpClient().newCall(request).execute()
            if (!response.isSuccessful) {
                // TODO: エラー処理
                Log.d("ktr", "ApiClient.fetchScoreRanking response is not successful")
                return
            }

            val body = response.body()

            val array = JSONObject(body.string()).getJSONArray(score_rank)

            body.close()

            if (array.length() <= 0) {
                return
            }

            for (i in 0..4) {
                realm!!.beginTransaction()
                val item = realm!!.createObjectFromJson(ScoreRankData::class.java, array.get(i).toString())
                item.diff = diff.toString()
                item.id = id
                realm!!.commitTransaction()
            }

        } catch (e: IOException) {
            Log.d("ktr", e.toString())

        } catch (e: JSONException) {
            Log.d("ktr", e.toString())
        }

    }

    /**
     * RankingData API
     */

    /**
     * Fetches ranking data from groovecoaster.jp

     * @param RANKING_TYPE A type of fetches
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun fetchRankingData(RANKING_TYPE: String) {
        var url = "http://groovecoaster.jp/xml/fmj2100/rank/"

        when (RANKING_TYPE) {
            SPConst.LEVEL_ALL_RANKING -> url += "all/rank_1.xml"

            SPConst.LEVEL_SIMPLE_RANKING -> url += "diff/0/rank_1.xml"

            SPConst.LEVEL_NORMAL_RANKING -> url += "diff/1/rank_1.xml"

            SPConst.LEVEL_HARD_RANKING -> url += "diff/2/rank_1.xml"

            SPConst.LEVEL_EXTRA_RANKING -> url += "diff/3/rank_1.xml"

            SPConst.GENRE_JPOP_RANKING -> url += "genre/J-POP/rank_1.xml"

            SPConst.GENRE_ANIME_RANKING -> url += "genre/ANIME/rank_1.xml"

            SPConst.GENRE_VOCALOID_RANKING -> url += "genre/VOCALOID/rank_1.xml"

            SPConst.GENRE_TOUHOU_RANKING -> url += "genre/TOUHOU/rank_1.xml"

            SPConst.GENRE_GAME_RANKING -> url += "genre/GAME/rank_1.xml"

            SPConst.GENRE_VARIETY_RANKING -> url += "genre/VARIETY/rank_1.xml"

            SPConst.GENRE_ORIGINAL_RANKING -> url += "genre/ORIGINAL/rank_1.xml"

            else -> url += "all/rank_1.xml"
        }

        val request = okhttp3.Request.Builder().url(url).get().build()

        val response = AppController.getOkHttpClient().newCall(request).execute()
        if (!response.isSuccessful) {
            // TODO: エラー処理
            return
        }

        val body = response.body()
        val value = body.string()

        if (!value.isEmpty()) {
            SharedPreferenceHelper.setRankingData(RANKING_TYPE, value)
        }

        body.close()

    }

    /**
     * Fetches event ranking data from groovecoaster.jp

     * @param SP_NAME The name of the SharedPreference for save this ranking data
     * *
     * @param number  The id of the target ranking
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun fetchEventRankingData(SP_NAME: String, number: Int) {
        val url = String.format("http://groovecoaster.jp/xml/fmj2100/rank/event/%03d/rank_1.xml", number + 1)

        Log.d("ktr", "url : " + url)

        val request = okhttp3.Request.Builder().url(url).get().build()

        val response = AppController.getOkHttpClient().newCall(request).execute()
        if (!response.isSuccessful) {
            // TODO: エラー処理
            return
        }

        val body = response.body()
        val value = body.string()

        Log.d("ktr", value)

        if (!value.isEmpty()) {
            SharedPreferenceHelper.setRankingData(SP_NAME, value)
        }

        body.close()
    }

    /**
     * Fetches event.xml to get a list of all event name.

     * @throws IOException
     */
    @Throws(IOException::class)
    fun fetchEventNameList() {
        val url = "http://groovecoaster.jp/xml/fmj2100/rank/event.xml"

        val request = okhttp3.Request.Builder().url(url).get().build()

        val response = AppController.getOkHttpClient().newCall(request).execute()
        if (!response.isSuccessful) {
            // TODO: エラー処理
            Log.d("ktr", "[RankingDataUseCase] fetchEventNameList OkHttp isNotSuccessful")
            return
        }

        val body = response.body()
        val value = body.string()

        if (!value.isEmpty()) {
            SharedPreferenceHelper.setEventNameList(value)
        }

        body.close()
    }

    /**
     * Now event API
     */

    // TODO: イベントがない時の処理

    /**
     * Fetches now event information.

     * @return @{code JSONObject} Response JSON from event_data.php
     * *
     * @throws IOException
     * *
     * @throws JSONException
     */
    @Throws(IOException::class, JSONException::class)
    fun fetchCurrentEventDetail(): JSONObject {
        Log.d("CurrentEventApi", "fetchCurrentEventDetail")

        val url = "https://mypage.groovecoaster.jp/sp/json/event_data.php"

        val `object` = JSONObject(client.sendRequest(url))

        checkAuthorization(`object`)

        return `object`
    }

    // TODO: イベントがない時の処理
    /**
     * Fetches now event destination.
     * @return @{code JSONObject} Response JSON from event_destination.php
     * *
     * @throws IOException
     * *
     * @throws JSONException
     */
    @Throws(IOException::class, JSONException::class)
    fun fetchCurrentEventDestination(): JSONObject {
        Log.d("CurrentEventApi", "fetchCurrentEventDestination")

        val url = "https://mypage.groovecoaster.jp/sp/json/event_destination.php"

        val `object` = JSONObject(client.sendRequest(url))

        checkAuthorization(`object`)

        return `object`
    }

    /**
     * Other Methods
     */

    // TODO: すごく汚いから治したい

    /**
     * Replaces `null` to safety value.

     * @param obj The target object
     * *
     * @param key A key of target object
     */
    private fun resultDataJsonReplaceNull(obj: JSONObject, key: String) {
        if (obj.isNull(key) && obj.has(key)) {
            val result = JSONObject()
            try {
                result.put(Const.MUSIC_RESULT_ADLIB, 0)
                result.put(Const.MUSIC_RESULT_FULL_CHAIN, 0)
                result.put(Const.MUSIC_RESULT_IS_CLEAR_MARK, "false")
                result.put(Const.MUSIC_RESULT_IS_FAILED_MARK, "false")
                result.put(Const.MUSIC_RESULT_MAX_CHAIN, 0)
                result.put(Const.MUSIC_RESULT_LEVEL, "-")
                result.put(Const.MUSIC_RESULT_NO_MISS, 0)
                result.put(Const.MUSIC_LIST_PLAY_COUNT, 0)
                result.put(Const.MUSIC_RESULT_RATING, "-")
                result.put(Const.MUSIC_RESULT_SCORE, 0)
                obj.put(key, result)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }

    // TODO: すごく汚いから治したい

    /**
     * Replaces `null` to safety value.

     * @param array The target array
     */
    private fun userRankJsonReplaceNull(array: JSONArray) {
        for (i in 0..array.length() - 1) {
            if (array.isNull(i)) {
                val rank = JSONObject()
                try {
                    rank.put(Const.MUSIC_USER_RANK, 0)
                    array.put(i, rank)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * Checks status from JSON

     * @param object @{code JSONObject} Fetched JSON data
     * *
     * @throws JSONException If status is 1
     */
    @Throws(JSONException::class)
    private fun checkAuthorization(`object`: JSONObject) {
        if (`object`.getInt("status") == 1) {
            throw RuntimeException("Unauthorized access")
        }
    }

    /**
     * Send post request for fetching.
     * sendRequest() is return body string written by JSON.
     */
    interface ClientInterface {
        @Throws(IOException::class)
        fun sendRequest(target_url: String): String

        @Throws(IOException::class)
        fun sendRequestForByteArray(target_url: String): ByteArray
    }

    class InvalidResponseException: RuntimeException {
        constructor(msg: String) : super("Invalid response: %s".format(msg)) {}
    }

}
