package com.syfm;

import com.syfm.groover.BuildConfig;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

/**
 * Created by lycoris on 2016/06/04.
 */

public class CustomRobolectricGradleTestRunner extends RobolectricGradleTestRunner {
    public CustomRobolectricGradleTestRunner(Class klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        String buildVariant = (BuildConfig.FLAVOR.isEmpty() ? "" : BuildConfig.FLAVOR + "/")
                + BuildConfig.BUILD_TYPE;
        return new AndroidManifest(
                Fs.fileFromPath("src/test/AndroidManifest.xml"),
                Fs.fileFromPath("build/intermediates/res/" + buildVariant),
                Fs.fileFromPath("src/test/assets")
        );
    }
}