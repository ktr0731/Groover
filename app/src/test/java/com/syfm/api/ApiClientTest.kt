package com.syfm.api

import com.syfm.CustomRobolectricGradleTestRunner
import com.syfm.groover.BuildConfig
import com.syfm.groover.model.api.ApiClient

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

import java.io.IOException
import java.io.InputStream

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertThat
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

/**
 * Created by lycoris on 2016/05/17.
 */

@RunWith(CustomRobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class)
class ApiClientTest {
    internal lateinit var apiClient: ApiClient
    internal lateinit var client: ApiClient.ClientInterface


    @Before
    @Throws(Exception::class)
    fun setUp() {
        apiClient = ApiClient()
        client = mock(ApiClient.ClientInterface::class.java)

        apiClient.client = client
    }

    /**
     * PlayerData
     */

    @Test
    @Throws(Exception::class)
    fun fetchPlayerData_successful() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/player_data.php"
        val player_data = "player_data"

        val jsonString = String(getFileContent("player_data_ok.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(jsonString)

        val `object` = JSONObject(jsonString)

        // Verify
        assertThat(apiClient.fetchPlayerData().toString(), equalTo(`object`.toString()))
    }

    @Test(expected = RuntimeException::class)
    @Throws(Exception::class)
    fun fetchPlayerData_failure_unauthorized() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/player_data.php"

        val jsonString = String(getFileContent("player_data_ng_unauthorized.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(jsonString)

        // Verify
        apiClient.fetchPlayerData()
    }

    @Test(expected = JSONException::class)
    @Throws(Exception::class)
    fun fetchPlayerData_failure_empty_string() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/player_data.php"

        // Exercise
        `when`(client.sendRequest(url)).thenReturn("")

        // Verify
        apiClient.fetchPlayerData()
    }

    @Test(expected = IOException::class)
    @Throws(Exception::class)
    fun fetchPlayerData_failure_disconnection() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/player_data.php"

        // Exercise
        `when`(client.sendRequest(url)).thenThrow(IOException::class.java)

        // Verify
        apiClient.fetchPlayerData()
    }

    /**
     * MusicList
     */

    @Test
    @Throws(Exception::class)
    fun fetchMusicList_successful() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/music_list.php"
        val jsonString = String(getFileContent("music_list_ok.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(jsonString)
        val array = JSONObject(jsonString).getJSONArray("music_list")

        // Verify
        assertThat(apiClient.fetchMusicList().toString(), equalTo(array.toString()))
    }


    @Test(expected = RuntimeException::class)
    @Throws(Exception::class)
    fun fetchMusicList_failure_unauthorized() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/music_list.php"

        val jsonString = String(getFileContent("music_list_ng_unauthorized.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(jsonString)

        // Verify
        apiClient.fetchMusicList()
    }

    @Test(expected = JSONException::class)
    @Throws(Exception::class)
    fun fetchMusicList_failure_empty_string() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/music_list.php"

        // Exercise
        `when`(client.sendRequest(url)).thenReturn("")

        // Verify
        apiClient.fetchMusicList()
    }

    /**
     * MusicDetail
     */

    @Test
    @Throws(Exception::class)
    fun fetchMusicDetail_successful() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=0"
        val jsonString = String(getFileContent("music_detail_ok.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(jsonString)
        val `object` = JSONObject(jsonString).getJSONObject("music_detail")

        // Verify
        assertThat(apiClient.fetchMusicDetail(0).toString(), equalTo(`object`.toString()))
    }


    @Test(expected = RuntimeException::class)
    @Throws(Exception::class)
    fun fetchMusicDetail_failure_unauthorized() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=0"

        val jsonString = String(getFileContent("music_detail_ng_unauthorized.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(jsonString)

        // Verify
        apiClient.fetchMusicDetail(0)
    }

    @Test(expected = JSONException::class)
    @Throws(Exception::class)
    fun fetchMusicDetail_failure_empty_string() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=0"

        // Exercise
        `when`(client.sendRequest(url)).thenReturn("")

        // Verify
        apiClient.fetchMusicDetail(0)
    }


    /**
     * MusicThumbnail
     */

    @Test
    @Throws(Exception::class)
    fun fetchMusicThumbnail_successful() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/music/music_image.php?music_id=0"
        val bytes = getFileContent("music_image.jpeg")

        // Exercise
        `when`(client.sendRequestForByteArray(url)).thenReturn(bytes)

        // Verify
        assertArrayEquals(bytes, apiClient.fetchMusicThumbnail(0))
    }

    @Test(expected = RuntimeException::class)
    @Throws(Exception::class)
    fun fetchMusicThumbnail_failure_unauthorized() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/music_image.php?music_id=0"

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(null)

        // Verify
        apiClient.fetchMusicThumbnail(0)
    }

    @Throws(Exception::class)
    private fun getFileContent(fileName: String): ByteArray {
        val stream = RuntimeEnvironment.application.assets.open(fileName)
        val buffer = ByteArray(stream.available())
        stream.read(buffer)
        stream.close()

        return buffer
    }

    /**
     * CurrentEvent
     */

    @Test
    @Throws(Exception::class)
    fun fetchCurrentEventDetail_successful() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/event_data.php"
        val jsonString = String(getFileContent("current_event_detail_ok.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(jsonString)

        val `object` = JSONObject(jsonString)

        // Verify
        assertThat(apiClient.fetchCurrentEventDetail().toString(), equalTo(`object`.toString()))
    }

    @Test(expected = RuntimeException::class)
    @Throws(Exception::class)
    fun fetchCurrentEventDetail_failure_unauthorized() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/event_data.php"
        val jsonString = String(getFileContent("current_event_detail_ng_unauthorized.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(jsonString)

        val `object` = JSONObject(jsonString)

        // Verify
        apiClient.fetchCurrentEventDetail()
    }

    @Test(expected = JSONException::class)
    @Throws(Exception::class)
    fun fetchCurrentEventDetail_failure_empty_string() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/event_data.php"
        val jsonString = String(getFileContent("current_event_detail_ng_unauthorized.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn("")

        // Verify
        apiClient.fetchCurrentEventDetail()
    }


    @Test
    @Throws(Exception::class)
    fun fetchCurrentEventDestination_successful() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/event_destination.php"
        val jsonString = String(getFileContent("current_event_destination_ok.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(jsonString)

        val `object` = JSONObject(jsonString)

        // Verify
        assertThat(apiClient.fetchCurrentEventDestination().toString(), equalTo(`object`.toString()))
    }

    @Test(expected = RuntimeException::class)
    @Throws(Exception::class)
    fun fetchCurrentEventDestination_failure_unauthorized() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/event_destination.php"
        val jsonString = String(getFileContent("current_event_destination_ng_unauthorized.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn(jsonString)

        val `object` = JSONObject(jsonString)

        // Verify
        apiClient.fetchCurrentEventDestination()
    }

    @Test(expected = JSONException::class)
    @Throws(Exception::class)
    fun fetchCurrentEventDestination_failure_empty_string() {
        // Set up
        val url = "https://mypage.groovecoaster.jp/sp/json/event_destination.php"
        val jsonString = String(getFileContent("current_event_destination_ng_unauthorized.json"))

        // Exercise
        `when`(client.sendRequest(url)).thenReturn("")

        // Verify
        apiClient.fetchCurrentEventDestination()
    }
}