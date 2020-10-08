package com.anm.bslndmag.Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class CartSession {
    private Context context;
    private SharedPreferences prefs;
    private SharedPreferences retailerprefs;
    public final static String PREFS_NAME = "cart_sess_pref";
    public final static String Retailer_PREFS_NAME = "cart_retailer_sess_pref";
    public CartSession(Context context) {
        this.context=context;

    }

    public SharedPreferences return_cart_value()
    {
        return prefs;
    }

    public void setCount(int amt)
    {
        SharedPreferences pref1= this.context.getSharedPreferences("unique_no", 0);
        SharedPreferences.Editor editor = pref1.edit();
        editor.putString("count", amt+"");
        editor.commit();
    }
    public String getCount()
    {
        SharedPreferences pref1= this.context.getSharedPreferences("unique_no", 0);
        String countstr =pref1.getString("count","0");
        int count= Integer.parseInt(countstr)+1;
        if(count==99)
        {
            count=1;
        }
        setCount(count);
        return count+"";
    }
    public void clear_cart_data()
    {
        SharedPreferences.Editor editor=prefs.edit();
        editor.clear();
        editor.commit();
        SharedPreferences pref2= this.context.getSharedPreferences("total_amount_pref", 0);
        SharedPreferences.Editor editor1=pref2.edit();
        editor1.clear();
        editor1.commit();
    }
}
