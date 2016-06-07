package com.syfm.groover.model.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.syfm.CustomRobolectricGradleTestRunner;
import com.syfm.groover.BuildConfig;
import com.syfm.groover.model.storage.databases.Music;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

/**
 * Created by lycoris on 2016/06/07.
 */
@RunWith(CustomRobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MusicFormatterTest {
    MusicFormatter formatter;
    Gson gson;

    @Before
    public void setUp() {
        formatter = new MusicFormatter();
        gson = new GsonBuilder().create();
    }

    @Test
    public void getFormattedMusicRecord_successful() throws Exception {
        // Set up
        // TODO: stackoverflow error
//        String lastPlayTime = "2016-06-03 15:29:20";
//        byte[] thumbnail = getFileContent("music_image.jpeg");
//
//        String jsonString = new String(getFileContent("music_detail_ok.json"), "UTF-8");
//        String expectString = new String(getFileContent("get_formatted_music_record_ok.json"), "UTF-8");
//
//        JSONObject detail = new JSONObject(jsonString).getJSONObject("music_detail");
//        Music expected = gson.fromJson(expectString, Music.class);
//
//        // Exercise
//        Music music = formatter.getFormattedMusicRecord(lastPlayTime, thumbnail, detail);
//
//        // Verify
//        assertThat(music, is(0));
    }

    private byte[] getFileContent(String fileName) throws Exception {
        InputStream stream = RuntimeEnvironment.application.getAssets().open(fileName);
        byte[] buffer = new byte[stream.available()];
        stream.read(buffer);
        stream.close();

        return buffer;
    }
}