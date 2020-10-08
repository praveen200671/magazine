package com.anm.bslndmag.Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.Model.AuthResponse;
import com.anm.bslndmag.Model.RequestTokenData;


import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenSession {
    private Context context;
    private SharedPreferences prefs;
    public final static String PREFS_NAME = "token_pref";
    public TokenSession(Context context) {
        this.context=context;
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public void get_token()
    {

    }
    private void get_Auth_Token(){
        Call<AuthResponse> auth= Api.getApi().getAuthToken();
        auth.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                AuthResponse res=response.body();
                if(res!=null) {
                    if (res.getFlag() == 1) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("Auth_Token", true);
                        RequestTokenData token = res.getData();
                        editor.putString("Auth_Token_id", token.getRequestToken());


                        Headers headers = response.headers();
                        List<String> lst = response.headers().values("Set-Cookie");
                        int i = 0;
                        for (String myVal : lst) {
                            if (i == 0) {
                                editor.putString("XSRF_TOKEN", myVal.trim());
                            } else if (i == 1) {
                                editor.putString("larravel_session", myVal.trim());
                            }
                            i++;

                        }
                        editor.commit();

                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                //Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getTokenVal() {
        if(prefs.getString("Auth_Token_id","")!="")
        {
            return prefs.getString("Auth_Token_id","");
        }
        else{
            get_Auth_Token();
            return prefs.getString("Auth_Token_id","");
        }
    }
    public void generate_new_token()
    {

        get_Auth_Token();
    }
    public String getheader(){
        return prefs.getString("XSRF_TOKEN","")+";"+prefs.getString("larravel_session","");
    }
    public String getXSRF_Token()
    {
        return prefs.getString("XSRF_TOKEN","");
    }
    public String getlaravelsession(){
        return prefs.getString("larravel_session","");
    }
    public void flush_token()
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }
}
