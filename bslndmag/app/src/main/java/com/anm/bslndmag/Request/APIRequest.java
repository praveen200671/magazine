package com.anm.bslndmag.Request;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.LoginActivity;
import com.anm.bslndmag.Session.LoginSession;
import com.anm.bslndmag.Session.TokenSession;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIRequest {
    Context context;
    public APIRequest(Context context) {
        this.context=context;
    }

    public void show_success_msg(String msg)
    {
        Toasty.success(context,msg,Toasty.LENGTH_LONG).show();
    }
    public void show_error_msg(String msg)
    {
        Toasty.error(context,msg,Toasty.LENGTH_LONG).show();
    }


}
