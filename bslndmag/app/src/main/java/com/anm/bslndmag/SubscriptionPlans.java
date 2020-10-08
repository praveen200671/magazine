package com.anm.bslndmag;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.Adapter.SubscriptionPlansAdapter;
import com.anm.bslndmag.Model.LoginRequest;
import com.anm.bslndmag.Model.LoginResponse;
import com.anm.bslndmag.Model.LoginResponseData;
import com.anm.bslndmag.Model.SubscribePlanRequest;
import com.anm.bslndmag.Model.SubscribePlanResponse;
import com.anm.bslndmag.Model.SubscriptionPlansData;
import com.anm.bslndmag.Model.SubscriptionPlansRequest;
import com.anm.bslndmag.Model.SubscriptionPlansResponse;
import com.anm.bslndmag.Session.LoginSession;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionPlans extends AppCompatActivity {

    LoginSession ls;
    RecyclerView rv_plans;
    SubscriptionPlansAdapter plansAdapter;
    ArrayList<SubscriptionPlansData> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_plans);
        rv_plans =findViewById(R.id.rv_plans);
        ls=new LoginSession(this);
        plansAdapter=new SubscriptionPlansAdapter(this, arrayList, new SubscriptionPlansAdapter.retailerdatalistener() {
            @Override
            public void cardviewclicklistener(SubscriptionPlansData retailerdata) {
                if(ls.get_auth_token().length()>0) {
                    send_Request_SubscribePlan(retailerdata.getId());
                }
                else
                {
                    if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                         Toasty.info(SubscriptionPlans.this,getString(R.string.loginagain_english)).show();
                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                         Toasty.info(SubscriptionPlans.this,getString(R.string.loginagain_hindi)).show();
                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("3"))
                         Toasty.info(SubscriptionPlans.this,getString(R.string.loginagain_gurmukhi)).show();

                }
            }
        });
        LinearLayoutManager horizontal
                = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);
        rv_plans.setLayoutManager(horizontal);
        rv_plans.setAdapter(plansAdapter);
        send_Request_getPlan();
    }

    private void send_Request_getPlan() {
        final ProgressDialog dialog = new ProgressDialog(SubscriptionPlans.this);
        try {
            final SubscriptionPlansRequest loginRequest =new SubscriptionPlansRequest(ls.getdeviceid(),
                    ls.getStr(getString(R.string.language)),
                    "android");
            String token=ls.get_auth_token();
//            if(!token.startsWith("bearer"))
//            {
//                token=""+token;
//            }
            //String header="eyJpdiI6IldLZnFCWVd4STRXOGdzeFhySmJZNEE9PSIsInZhbHVlIjoiUW95KzBzYkh5RzdxTnFwXC9PZXBJRGI5eWhmSTJJUFkxYnQ0dndwMHpycURyYXg3V3M2TWZwTlM4WDcyRzZUTmoiLCJtYWMiOiI2MDhlNzE2OGZiNTE0NTI4ZjdlN2Y0ZTVlZDlmYTNkMTBhOTE4MDQwNzliNGY2YTA5MzU4ZDgzMmNjYzFiYWMxIn0%3D";
            Call<SubscriptionPlansResponse> logincall = Api.getApi().getSubscribePlan(loginRequest,token);
//                dialog.setMessage(getString(R.string.pleasewaitlogin));
            dialog.setCancelable(false);
            dialog.show();
            logincall.enqueue(new Callback<SubscriptionPlansResponse>() {
                @Override
                public void onResponse(Call<SubscriptionPlansResponse> call, Response<SubscriptionPlansResponse> response) {
                    SubscriptionPlansResponse loginres = response.body();
                    if(response.code()!=200)
                    {
                        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                        {
                            Toasty.error(SubscriptionPlans.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                        }
                        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                        {
                            Toasty.error(SubscriptionPlans.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(SubscriptionPlans.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                        }
                    }else {
                        boolean responsestatus = loginres.isStatus();//message();//body();
                        String responsemsg = loginres.getMessage();//body();
                        if (responsestatus == false) {
                            Toasty.error(SubscriptionPlans.this, responsemsg, Toasty.LENGTH_LONG).show();
                            dialog.dismiss();
                            return;
                        } else {
//                            Toasty.success(SubscriptionPlans.this, loginres.getMessage(), Toasty.LENGTH_LONG).show();
                            arrayList = loginres.getData();
                            if (arrayList.size()==0) {
                                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                                {
                                    Toasty.error(SubscriptionPlans.this, R.string.subscribenoplan_english, Toasty.LENGTH_LONG).show();
                                }
                                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                                {
                                    Toasty.error(SubscriptionPlans.this, R.string.subscribenoplan_hindi, Toasty.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toasty.error(SubscriptionPlans.this, R.string.subscribenoplan_gurmukhi, Toasty.LENGTH_LONG).show();
                                }
                            } else {
                                ls.set_isneedRefresh("y");
                                plansAdapter.notifychanges(arrayList);
//                                rv_plans.notify();

                            }
                        }
                    }

                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<SubscriptionPlansResponse> call, Throwable t) {
                    if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                    {
                        Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                    }
                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                    {
                        Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });
        }catch (Exception ex)
        {
            ex.getMessage();
            if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
            {
                Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
            }
            else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
            {
                Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
            }
            else
            {
                Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
//            }
//            else{
//                Toasty.error(LoginActivity.this, "Something Went Wrong. Please try again.",Toasty.LENGTH_LONG).show();;
//            }

    }

    private void send_Request_SubscribePlan(String planid) {
        final ProgressDialog dialog = new ProgressDialog(SubscriptionPlans.this);
        try {
            final SubscribePlanRequest loginRequest =new SubscribePlanRequest(ls.getStr(getString(R.string.language)),
                    planid,ls.getdeviceid(),
                    "android",
                    "plan"
            );
            String token=ls.get_auth_token();
            if(!token.toLowerCase().startsWith("bearer"))
            {
                token=""+token;
            }
            Call<SubscribePlanResponse> logincall = Api.getApi().subscribePlan(loginRequest,token);
            dialog.setCancelable(false);
            dialog.show();
            logincall.enqueue(new Callback<SubscribePlanResponse>() {
                @Override
                public void onResponse(Call<SubscribePlanResponse> call, Response<SubscribePlanResponse> response) {
                    SubscribePlanResponse loginres = response.body();
                    if(response.code()!=200)
                    {
                        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                        {
                            Toasty.error(SubscriptionPlans.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                        }
                        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                        {
                            Toasty.error(SubscriptionPlans.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(SubscriptionPlans.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                        }
                    }else {
                        ;//message();//body();
                        boolean responsestatus = loginres.isStatus();//message();//body();
                        String responsemsg = loginres.getMessage();//body();
                        if (responsestatus == false) {
                            Toasty.error(SubscriptionPlans.this, responsemsg, Toasty.LENGTH_LONG).show();
                            dialog.dismiss();
                            return;
                        } else {
//                            Toasty.success(SubscriptionPlans.this, loginres.getMessage(), Toasty.LENGTH_LONG).show();
                            String loginResponseData = loginres.getData();
                            Intent intent=new Intent(SubscriptionPlans.this,Privacypolicy_Tnc.class);
                            intent.putExtra("istermsandconditions","plan");
                            intent.putExtra("url",loginres.getData());
                            startActivityForResult(intent,888);

                        }
                    }


                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<SubscribePlanResponse> call, Throwable t) {
                    if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                    {
                        Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                    }
                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                    {
                        Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });
        }catch (Exception ex)
        {
            ex.getMessage();
            if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
            {
                Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
            }
            else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
            {
                Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
            }
            else
            {
                Toasty.error(SubscriptionPlans.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
//            }
//            else{
//                Toasty.error(LoginActivity.this, "Something Went Wrong. Please try again.",Toasty.LENGTH_LONG).show();;
//            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 888){
            send_Request_getPlan();
        }
    }
}
