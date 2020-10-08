package com.anm.bslndmag.Session;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.ActivityCompat;


import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.LoginActivity;
import com.anm.bslndmag.Model.AuthResponse;
import com.anm.bslndmag.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSession {
    private Context context;
    private SharedPreferences prefs;
    public final static String PREFS_NAME = "login_pref";
    public LoginSession(Context context) {
        this.context=context;
        prefs = this.context.getSharedPreferences(PREFS_NAME, 0);
    }
    public void save_login_data(String id,
                                String name,
                                String mobile,
                                String email,
                                String auth_token


                                )
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("id",id);
        editor.putString("name",name);
        editor.putString("mobile",mobile);
        editor.putString("email",email);
        editor.putString("auth_token",auth_token);


        editor.commit();

    }

    public String getAuthtoken() {
        return prefs.getString("auth_token","");
    }
    public void save_Vaccinations(ArrayList<String> vaccinationdata)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("vaccinationdata",vaccinationdata.toString());
        editor.commit();

    }


    public void setInt( String key, int value) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return prefs.getInt(key, 0);
    }

    public void setdeviceid(String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("deviceid", value);
        editor.commit();
    }

    public String getdeviceid() {
        return prefs.getString("deviceid","");
    }
    public void setStr(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getStr(String key) {
        return prefs.getString(key,"");
    }
    public void setBool(String key, boolean value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private void Logout_Session() {
      logout();
        Intent i=new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }
    public void logout(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("auth_token", "");
        editor.putBoolean("is_login", false);
        setSecurityPin("");

        editor.commit();
    }

    public String getTermsandcondition()
    {
        return prefs.getString("termsandcondition","");
    }
    public String getPrivacypolicy()
    {
        return prefs.getString("Privacypolicy","");
    }
    public void SaveTermsandCondition(String url){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("termsandcondition", url);
        editor.commit();
    }
    public void Save_Privacypolicy(String url){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Privacypolicy", url);
        editor.commit();
    }


    public String get_id()
    {
        return prefs.getString("id","");
    }
    public String get_user_id()
    {
        return prefs.getString("user_id","");
    }
    public String get_first_name()
    {
        return prefs.getString("first_name","");
    }
    public String get_user_mobile()
    {
        return prefs.getString("user_mobile","");
    }
    public String get_auth_token()
    {
        return prefs.getString("auth_token","");
    }
    public void set_auth_token(String logout)
    {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("auth_token", logout);
        editor.commit();
        //return prefs.getString("auth_token",logout);
    }


    public String get_isneedRefresh()
    {
        return prefs.getString("isrefresh","");
    }
    public void set_isneedRefresh(String refresh)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("isrefresh", refresh);
        editor.commit();
       // return prefs.("isrefresh",refresh);
    }

    public String get_is_active()
    {
        return prefs.getString("is_active","");
    }
    public String get_last_login()    {
        return prefs.getString("last_login","");
    }
    public String get_anm_relation_id()
    {
        return prefs.getString("anm_relation_id","");
    }
    public String get_dialno()
    {
        return prefs.getString("dialno","");
    }
    public String get_anm_name()
    {
        return prefs.getString("anm_name","");
    }
   public boolean is_login()
    {
        return prefs.getBoolean("is_login",false);
    }


    public boolean getBool(String key) {
        return prefs.getBoolean(key,false);
    }

    //Seller Data Start



public void setSecurityPin(String pin)
{
    SharedPreferences.Editor editor = prefs.edit();
    editor.putString(context.getString(R.string.securitypin), pin);

    editor.commit();
}

public String getSecurityPin()
{

    return prefs.getString(context.getString(R.string.securitypin),"");

}


    private void finishactivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Intent i=new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
            ((Activity)context).finishAffinity();
        }
        else{
            Intent i=new Intent(context,LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
            ActivityCompat.finishAffinity((Activity)context);
        }
    }

}
