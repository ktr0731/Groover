package com.syfm.usecases;

import com.syfm.CustomRobolectricGradleTestRunner;
import com.syfm.groover.BuildConfig;
import com.syfm.groover.controller.usecases.EventDataUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.InputStream;

/**
 * Created by lycoris on 2016/08/15.
 */

@RunWith(CustomRobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class EventDataUseCaseTest {
    EventDataUseCase useCase;

    @Before
    public void setUp() throws Exception {
        useCase = new EventDataUseCase();
    }

    @Test
    public void convertJSONArrayToJSONObject_successful() throws Exception {
        // TODO: Not yet implemented.
        String jsonString = new String(getFileContent(""));
    }

    // From ApiClientTest.java
    private byte[] getFileContent(String fileName) throws Exception {
        InputStream stream = RuntimeEnvironment.application.getAssets().open(fileName);
        byte[] buffer = new byte[stream.available()];
        stream.read(buffer);
        stream.close();

        return buffer;
    }

}
