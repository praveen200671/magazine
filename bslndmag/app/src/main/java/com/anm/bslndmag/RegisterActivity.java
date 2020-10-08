package com.anm.bslndmag;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.Model.LoginRequest;
import com.anm.bslndmag.Model.LoginResponse;
import com.anm.bslndmag.Model.LoginResponseData;
import com.anm.bslndmag.Model.RegisterRequest;
import com.anm.bslndmag.Session.LoginSession;
import com.chaos.view.PinView;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    String mobile;
    EditText et_name, et_emailid;
    PinView otp_view,confirmotp_view;
    Button btn_lgoin;
    LoginSession ls;
    private LinearLayout ll_termsandconditions;
    private LinearLayout ll_privacypolicy;
    private AppCompatCheckBox cb_termsandconditions;
    private AppCompatCheckBox cb_privacypolicy;
    private TextView tv_termsandconditions;
    private TextView tv_privacypolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ls = new LoginSession(this);
        Bundle bundle=getIntent().getExtras();
        mobile=  bundle.getString("Mobile");
        et_name=findViewById(R.id.et_name);
        et_emailid=findViewById(R.id.et_emailid);
        otp_view=findViewById(R.id.otp_view);
        ll_termsandconditions= findViewById(R.id.ll_termsandconditions);

        cb_termsandconditions= findViewById(R.id.cb_termsandconditions);
        tv_termsandconditions= findViewById(R.id.tv_termsandconditions);
        tv_privacypolicy= findViewById(R.id.tv_privacypolicy);
        ll_privacypolicy= findViewById(R.id.ll_privacypolicy);
        cb_privacypolicy= findViewById(R.id.cb_privacypolicy);
        confirmotp_view=findViewById(R.id.confirmotp_view);

        tv_termsandconditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               open_TermsandConditions();
            }
        });

        tv_privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_PrivacyandPolicy();
            }
        });
        btn_lgoin=findViewById(R.id.btn_lgoin);
        btn_lgoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );

//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(otpTextView.getWindowToken(), 0);

                Send_Login_Request();
            }
        });

    }

    private void open_TermsandConditions() {
        Intent intent=   new Intent(this, Privacypolicy_Tnc.class);
        intent.putExtra("istermsandconditions","true");
        startActivity(intent);
    }

    private void open_PrivacyandPolicy() {
        Intent intent=   new Intent(this, Privacypolicy_Tnc.class);
        intent.putExtra("istermsandconditions","false");
        startActivity(intent);
    }

    public void Send_Login_Request()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String username=et_name.getText().toString().trim();
        String emailid=et_emailid.getText().toString().trim();
        String otp=otp_view.getText().toString().trim();
        String confirmotp=confirmotp_view.getText().toString().trim();
        if(username.matches(""))
        {
            Toasty.error(RegisterActivity.this, R.string.entermobileno,Toasty.LENGTH_LONG).show();;
            return;
        }
        if(emailid.trim().length()<1)
        {
            Toasty.error(RegisterActivity.this, R.string.entercorrectmobileno,Toasty.LENGTH_LONG).show();;
            return;
        }

        if (!emailid.trim().matches(emailPattern)) {

            Toasty.error(RegisterActivity.this, R.string.entercorrectmobileno,Toasty.LENGTH_LONG).show();;
            return;

        }
          if(otp.trim().length()<4)
        {
            Toasty.error(RegisterActivity.this, R.string.enterpasscode,Toasty.LENGTH_LONG).show();;
            return;
        }
        if(!otp.trim().equalsIgnoreCase(confirmotp))
        {
            Toasty.error(RegisterActivity.this, R.string.enterpasscode,Toasty.LENGTH_LONG).show();;
            return;
        }
        else if(cb_termsandconditions.isChecked()==false)
        {
            Toasty.error(RegisterActivity.this, R.string.enterpasscode,Toasty.LENGTH_LONG).show();;
        }
        else if(cb_privacypolicy.isChecked()==false)
        {
            Toasty.error(RegisterActivity.this, R.string.enterpasscode,Toasty.LENGTH_LONG).show();;
        }
        else {
            final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
            try {
                final RegisterRequest registerRequest =new RegisterRequest(username,mobile,emailid ,otp,
                        //  ls.getStr(getString(R.string.language))
                        "1" ,
                        ls.getdeviceid(),
                        "android");
                //String header="eyJpdiI6IldLZnFCWVd4STRXOGdzeFhySmJZNEE9PSIsInZhbHVlIjoiUW95KzBzYkh5RzdxTnFwXC9PZXBJRGI5eWhmSTJJUFkxYnQ0dndwMHpycURyYXg3V3M2TWZwTlM4WDcyRzZUTmoiLCJtYWMiOiI2MDhlNzE2OGZiNTE0NTI4ZjdlN2Y0ZTVlZDlmYTNkMTBhOTE4MDQwNzliNGY2YTA5MzU4ZDgzMmNjYzFiYWMxIn0%3D";
                Call<LoginResponse> logincall = Api.getApi().RegisterUser(registerRequest);
//                dialog.setMessage(getString(R.string.pleasewaitlogin));
                dialog.setCancelable(false);
                dialog.show();
                logincall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginres = response.body();
                        ;//message();//body();
                        if(loginres.getStatusCode()==422)
                        {
                            String responsemsg = loginres.getMessage();//body()
                            Toasty.error(RegisterActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                        }
                        else if(loginres.getStatusCode()!=200)
                        {
                            if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                            {
                                Toasty.error(RegisterActivity.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                            }
                            else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                            {
                                Toasty.error(RegisterActivity.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toasty.error(RegisterActivity.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                            }
                        }
                        else {
                            boolean responsestatus = loginres.getStatus();//message();//body();
                            String responsemsg = loginres.getMessage();//body();
                            if (responsestatus == false) {
                                    if(loginres.getStatusCode()==422)
                                    {
                                       finish();
                                    }

                                    Toasty.error(RegisterActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                            } else {



                                LoginResponseData loginResponseData = loginres.getData();
                                String userid = loginResponseData.getId();
                                String usertoken = loginResponseData.getToken();
                                String mobile = loginResponseData.getMobile();
                                String email = loginResponseData.getEmail();
                                String name = loginResponseData.getName();


                                if (userid.matches("") || usertoken.matches("")) {
                                    if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                                    {
                                        Toasty.error(RegisterActivity.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                                    }
                                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                                    {
                                        Toasty.error(RegisterActivity.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toasty.error(RegisterActivity.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toasty.success(RegisterActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                                    ls.set_isneedRefresh("y");
                                    ls.save_login_data(userid, name, mobile, email, usertoken);
                                    ls.setBool("is_login", true);
                                    Intent returnIntent = new Intent();
                                    setResult(488, returnIntent);
                                    finish();
                                }
                            }
                        }



                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                        {
                            Toasty.error(RegisterActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                        }
                        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                        {
                            Toasty.error(RegisterActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(RegisterActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
            }catch (Exception ex)
            {
                ex.getMessage();
                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                {
                    Toasty.error(RegisterActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                }
                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                {
                    Toasty.error(RegisterActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                }
                else
                {
                    Toasty.error(RegisterActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
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

        if(resultCode == 488){
            finishAffinity();
        }
    }

}
