package com.syfm.groover.model.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;

import java.util.HashMap;

import okhttp3.Request;
import okhttp3.Response;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by lycoris on 2016/05/05.
 */
public class ApiClientTest {


    ApiClient client;
    ApiClient.LoginClient loginClient;
    HashMap<String, String> params;

    @Before
    public void setup() throws Exception {
        client = new ApiClient();
        loginClient = mock(ApiClient.LoginClient.class);
        client.loginClient = loginClient;

        params = new HashMap<>();
        params.put("id"      , "hoge");
        params.put("password", "fuga");
    }

    @Test
    public void login_successful() throws Exception {
        // Set up
        when(loginClient.sendRequest(params))
                .thenReturn("https://mypage.groovecoaster.jp/sp/#/");

        // Verify
        assertThat(client.tryLogin("hoge", "fuga"), is(true));
    }

    @Test
    public void login_failure() throws Exception {
        // Set up
        // TODO: AuthUserクラスの作成
        when(loginClient.sendRequest(params))
                .thenReturn("https://mypage.groovecoaster.jp/sp/login/auth.php?isError=true&val=0");

        // Verify
        assertThat(client.tryLogin("hoge", "fuga"), is(false));
    }

}
