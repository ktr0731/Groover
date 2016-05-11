package com.syfm.groover.model.network;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lycoris on 2016/05/11.
 */
public class LoginApi {

    private final static String URL = "https://mypage.groovecoaster.jp/sp/login/auth_con.php";
    private final static String SERIAL_ID = "nesicaCardId";
    private final static String PASSWORD  = "password";

    private String serial_id;
    private String password;

    LoginClient loginClient = (HashMap<String, String> params) -> {
            RequestBody body = new FormBody.Builder()
                    .add(SERIAL_ID, params.get(SERIAL_ID))
                    .add(PASSWORD, params.get(PASSWORD))
                    .build();

            Request request = new okhttp3.Request.Builder()
                    .url(URL)
                    .post(body)
                    .build();

            Response response = AppController.getOkHttpClient().newCall(request).execute();

            // login判定
            String res_url = response.request().url().toString();
            response.body().close();

            return res_url;
        };

    public LoginApi(String serialId, String passwordKey) {
        this.serial_id = serialId;
        this.password  = passwordKey;
    }

    /**
     * Tries login to Groove Coaster.
     *
     * @return {@code true} if login was successfully, {@Code false} login was failed
     * @throws IOException
     */
    public boolean tryLogin() throws IOException {
        String res_url = loginClient.sendRequest(getParams());

        if (res_url.contains("login_stop")) {
            return false;
        }

        if (res_url.contains("isError=true")) {
            return false;
        }

        return true;
    }

    /**
     * Generate params of nesica id and password
     *
     * @return {@code HashMap<String, String>}
     */
    public HashMap<String, String> getParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(SERIAL_ID, serial_id);
        params.put(PASSWORD , password);

        return params;
    }

    /**
     * Send post request for login.
     * sendRequest() is return response url.
     */
    public interface LoginClient {
        String sendRequest(HashMap<String, String> params) throws IOException;
    }
}
