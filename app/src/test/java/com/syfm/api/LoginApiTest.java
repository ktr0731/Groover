package com.syfm.api;

import com.syfm.CustomRobolectricGradleTestRunner;
import com.syfm.groover.BuildConfig;
import com.syfm.groover.model.api.LoginApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by lycoris on 2016/05/05.
 */

@RunWith(CustomRobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginApiTest {
    LoginApi api;
    LoginApi.LoginClient loginClient;
    HashMap<String, String> params;

    @Before
    public void setup() throws Exception {
        api = new LoginApi("hoge", "fuga");
        loginClient = mock(LoginApi.LoginClient.class);
        api.loginClient = loginClient;

        params = api.getParams();
    }

    @Test
    public void login_successful() throws Exception {
        // Set up
        when(loginClient.sendRequest(params))
                .thenReturn("https://mypage.groovecoaster.jp/sp/#/");

        // Verify
        assertThat(api.tryLogin(), is(true));
    }

    @Test
    public void login_failure() throws Exception {
        // Set up
        // TODO: AuthUserクラスの作成
        when(loginClient.sendRequest(params))
                .thenReturn("https://mypage.groovecoaster.jp/sp/login/auth.php?isError=true&val=0");

        // Verify
        assertThat(api.tryLogin(), is(false));
    }

}
