package com.anm.bslndmag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.Model.LoginResponse;
import com.anm.bslndmag.Model.LoginResponseData;
import com.anm.bslndmag.Model.LoginwithPinRequest;
import com.anm.bslndmag.Session.LoginSession;
import com.chaos.view.PinView;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTPActivity extends AppCompatActivity {
    String mobile;
    PinView otp_view;
    Button btn_lgoin;
    LoginSession ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);
        ls=new LoginSession(this);
        otp_view=findViewById(R.id.otp_view);
        btn_lgoin=findViewById(R.id.btn_lgoin);

        Bundle bundle=getIntent().getExtras();
        mobile=  bundle.getString("Mobile");

        btn_lgoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );

//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(otpTextView.getWindowToken(), 0);
                Send_verify_Request();

            }
        });


    }

    public void Send_verify_Request()
    {
        if(otp_view.getText().toString().matches(""))
        {
            Toasty.error(VerifyOTPActivity.this, R.string.enterpinnumber,Toasty.LENGTH_LONG).show();;
        }
        else if(otp_view.getText().toString().length()<4)
        {
            Toasty.error(VerifyOTPActivity.this, R.string.entercorrectpasscode,Toasty.LENGTH_LONG).show();;
        }
        else {
            final ProgressDialog dialog = new ProgressDialog(VerifyOTPActivity.this);
            try {
                LoginwithPinRequest loginRequest =new LoginwithPinRequest(mobile,
                        ls.getStr(getString(R.string.language)),
                        "android",
                        ls.getdeviceid(),
                        otp_view.getText().toString().trim());
                //String header="eyJpdiI6IldLZnFCWVd4STRXOGdzeFhySmJZNEE9PSIsInZhbHVlIjoiUW95KzBzYkh5RzdxTnFwXC9PZXBJRGI5eWhmSTJJUFkxYnQ0dndwMHpycURyYXg3V3M2TWZwTlM4WDcyRzZUTmoiLCJtYWMiOiI2MDhlNzE2OGZiNTE0NTI4ZjdlN2Y0ZTVlZDlmYTNkMTBhOTE4MDQwNzliNGY2YTA5MzU4ZDgzMmNjYzFiYWMxIn0%3D";
                Call<LoginResponse> logincall = Api.getApi().VerifyOTP(loginRequest);
//                dialog.setMessage(getString(R.string.pleasewaitlogin));
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
                            if(loginres.getStatusCode()==404)
                            {
                                Toasty.error(VerifyOTPActivity.this,responsemsg,Toasty.LENGTH_LONG).show();;
                            }
                            //Toasty.error(LoginActivity.this,responsemsg,Toasty.LENGTH_LONG).show();;
                            //ls.logout();
                            dialog.dismiss();
//                            Intent intent= new Intent(LoginActivity.this,LoginActivity.class);
//                            finishAffinity();
//                            startActivity(intent);
                            return;
                        }else {
                            Toasty.success(VerifyOTPActivity.this, loginres.getMessage(), Toasty.LENGTH_LONG).show();
                            LoginResponseData loginResponseData = loginres.getData();
                            String userid = loginResponseData.getId();
                            String usertoken = loginResponseData.getToken();
                            String mobile = loginResponseData.getMobile();
                            String email = loginResponseData.getEmail();
                            String name = loginResponseData.getName();
                            if (userid.matches("") || usertoken.matches("")) {
                                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                                {
                                    Toasty.error(VerifyOTPActivity.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                                }
                                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                                {
                                    Toasty.error(VerifyOTPActivity.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toasty.error(VerifyOTPActivity.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                                }
                            } else {
                               // Toasty.success(VerifyOTPActivity.this, R.string.somethingwentwrong, Toasty.LENGTH_LONG).show();
                                ls.set_isneedRefresh("y");
                                ls.save_login_data(userid, name, mobile, email, usertoken);
                                ls.setBool("is_login", true);
                                Intent returnIntent = new Intent();
                                setResult(588,returnIntent);
                                finish();
                            }
                        }

                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                        {
                            Toasty.error(VerifyOTPActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                        }
                        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                        {
                            Toasty.error(VerifyOTPActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(VerifyOTPActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
            }catch (Exception ex)
            {
                ex.getMessage();
                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                {
                    Toasty.error(VerifyOTPActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                }
                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                {
                    Toasty.error(VerifyOTPActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                }
                else
                {
                    Toasty.error(VerifyOTPActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
//            }
//            else{
//                Toasty.error(LoginActivity.this, "Something Went Wrong. Please try again.",Toasty.LENGTH_LONG).show();;
//            }
        }
    }
}
