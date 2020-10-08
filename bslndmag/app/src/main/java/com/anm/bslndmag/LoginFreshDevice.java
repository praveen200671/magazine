package com.anm.bslndmag;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.Model.ForgotPinResponse;
import com.anm.bslndmag.Model.LoginRequest;
import com.anm.bslndmag.Model.LoginResponse;
import com.anm.bslndmag.Model.LoginResponseData;
import com.anm.bslndmag.Model.LoginwithPinRequest;
import com.anm.bslndmag.Session.LoginSession;
import com.chaos.view.PinView;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFreshDevice extends AppCompatActivity {
    String mobile;
    TextView tv_forgetPin;
    Button submitbutton;
    PinView pinView;
    LoginSession ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fresh_device);
        ls =new LoginSession(this);
        Bundle bundle=getIntent().getExtras();
        mobile=  bundle.getString("Mobile");
        pinView = findViewById(R.id.otp_view);
        tv_forgetPin =findViewById(R.id.tv_forgetPin);
        submitbutton =findViewById(R.id.btn_lgoin);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send_Login_Request();
            }
        });
        tv_forgetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send_Forgetpin_Request();
            }
        });



    }


    public void Send_Forgetpin_Request()
    {

        {
            final ProgressDialog dialog = new ProgressDialog(LoginFreshDevice.this);
            try {
                final LoginRequest loginRequest =new LoginRequest(mobile,
                        ls.getdeviceid(),
                          ls.getStr(getString(R.string.language)),
                        "android");
                //String header="eyJpdiI6IldLZnFCWVd4STRXOGdzeFhySmJZNEE9PSIsInZhbHVlIjoiUW95KzBzYkh5RzdxTnFwXC9PZXBJRGI5eWhmSTJJUFkxYnQ0dndwMHpycURyYXg3V3M2TWZwTlM4WDcyRzZUTmoiLCJtYWMiOiI2MDhlNzE2OGZiNTE0NTI4ZjdlN2Y0ZTVlZDlmYTNkMTBhOTE4MDQwNzliNGY2YTA5MzU4ZDgzMmNjYzFiYWMxIn0%3D";
                Call<ForgotPinResponse> logincall = Api.getApi().ForgotPin(loginRequest);
//                dialog.setMessage(getString(R.string.pleasewaitlogin));
                dialog.setCancelable(false);
                dialog.show();
                logincall.enqueue(new Callback<ForgotPinResponse>() {
                    @Override
                    public void onResponse(Call<ForgotPinResponse> call, Response<ForgotPinResponse> response) {
                        ForgotPinResponse loginres = response.body();
                        if(response.code()!=200)
                        {
                            if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                            {
                                Toasty.error(LoginFreshDevice.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                            }
                            else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                            {
                                Toasty.error(LoginFreshDevice.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toasty.error(LoginFreshDevice.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                            }
                        }
                        else {
                            ;//message();//body();
                            boolean responsestatus = loginres.getStatus();//message();//body();
                            String responsemsg = loginres.getMessage();//body();
                            if (responsestatus == false) {
                                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                                {
                                    Toasty.error(LoginFreshDevice.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                                }
                                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                                {
                                    Toasty.error(LoginFreshDevice.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toasty.error(LoginFreshDevice.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                                }
                            } else {
                                Intent intent = new Intent(LoginFreshDevice.this, VerifyOTPActivity.class);
                                intent.putExtra("Mobile", loginRequest.getMobile());
                                startActivityForResult(intent, 588);
                            }
                        }



                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ForgotPinResponse> call, Throwable t) {
                        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                        {
                            Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                        }
                        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                        {
                            Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                        }

                        dialog.dismiss();
                    }
                });
            }catch (Exception ex)
            {
                ex.getMessage();
                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                {
                    Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                }
                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                {
                    Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                }
                else
                {
                    Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
//            }
//            else{
//                Toasty.error(LoginActivity.this, "Something Went Wrong. Please try again.",Toasty.LENGTH_LONG).show();;
//            }
        }
    }
    public void Send_Login_Request()
    {
        if(pinView.getText().toString().matches(""))
        {
            Toasty.error(LoginFreshDevice.this, R.string.enterpinnumber,Toasty.LENGTH_LONG).show();;
        }
        else if(pinView.getText().toString().length()<4)
        {
            Toasty.error(LoginFreshDevice.this, R.string.entercorrectpasscode,Toasty.LENGTH_LONG).show();;
        }
       /* else if(password.matches(""))
        {
            Toasty.error(LoginActivity.this, R.string.enterpasscode,Toasty.LENGTH_LONG).show();;
        }
        else if(password.length()!=4)
        {
            Toasty.error(LoginActivity.this,  R.string.entercorrectpasscode,Toasty.LENGTH_LONG).show();;
        }*/
        else {
            final ProgressDialog dialog = new ProgressDialog(LoginFreshDevice.this);
            try {
//                JSONObject request = new JSONObject();
//                request.put("mobile", username);
//                request.put("device_id", ls.getdeviceid());
//                request.put("language", ls.getStr(getString(R.string.language),getString(R.string.language_english)));
//                request.put("device_type", "android");


                LoginwithPinRequest loginRequest =new LoginwithPinRequest(mobile,

                          ls.getStr(getString(R.string.language)),
                        "android",
                        ls.getdeviceid(),
                        pinView.getText().toString());

                //String header="eyJpdiI6IldLZnFCWVd4STRXOGdzeFhySmJZNEE9PSIsInZhbHVlIjoiUW95KzBzYkh5RzdxTnFwXC9PZXBJRGI5eWhmSTJJUFkxYnQ0dndwMHpycURyYXg3V3M2TWZwTlM4WDcyRzZUTmoiLCJtYWMiOiI2MDhlNzE2OGZiNTE0NTI4ZjdlN2Y0ZTVlZDlmYTNkMTBhOTE4MDQwNzliNGY2YTA5MzU4ZDgzMmNjYzFiYWMxIn0%3D";
                Call<LoginResponse> logincall = Api.getApi().LoginFreshdevice(loginRequest);
               // dialog.setMessage(getString(R.string.pleasewaitlogin));
                dialog.setCancelable(false);
                dialog.show();
                logincall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginres = response.body();
                        ;//message();//body();
                        boolean responsestatus= loginres.getStatus();//message();//body();
                        String responsemsg = loginres.getMessage();//body();
                        if (responsestatus == false) {
                            if(loginres.getStatusCode()==422)
                            {
                                Toasty.error(LoginFreshDevice.this,responsemsg,Toasty.LENGTH_LONG).show();;
                            }
                            //Toasty.error(LoginActivity.this,responsemsg,Toasty.LENGTH_LONG).show();;
                            //ls.logout();
                            dialog.dismiss();
//                            Intent intent= new Intent(LoginActivity.this,LoginActivity.class);
//                            finishAffinity();
//                            startActivity(intent);
                            return;
                        }else {


                            Toasty.success(LoginFreshDevice.this, loginres.getMessage(), Toasty.LENGTH_LONG).show();
                            LoginResponseData loginResponseData = loginres.getData();
                            String userid = loginResponseData.getId();
                            String usertoken = loginResponseData.getToken();
                            String mobile = loginResponseData.getMobile();
                            String email = loginResponseData.getEmail();
                            String name = loginResponseData.getName();


                            {
                                Toasty.success(LoginFreshDevice.this,responsemsg, Toasty.LENGTH_LONG).show();
                                ls.set_isneedRefresh("y");
                                ls.save_login_data(userid, name, mobile, email, usertoken);
                                ls.setBool("is_login", true);
                                Intent returnIntent = new Intent();
                                setResult(488,returnIntent);
                                finish();

                            }
                        }



                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                        {
                            Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                        }
                        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                        {
                            Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
            }catch (Exception ex)
            {
                ex.getMessage();
                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                {
                    Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                }
                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                {
                    Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                }
                else
                {
                    Toasty.error(LoginFreshDevice.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
//            }
//            else{
//                Toasty.error(LoginActivity.this, "Something Went Wrong. Please try again.",Toasty.LENGTH_LONG).show();;
//            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 588){
            Intent returnIntent = new Intent();
            setResult(488,returnIntent);
            finish();
        }
    }
}
