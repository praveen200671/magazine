package com.anm.bslndmag;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.Model.ANMChildData;
import com.anm.bslndmag.Model.ANMData;
import com.anm.bslndmag.Model.ANMDetailsRequest;
import com.anm.bslndmag.Model.ANMDetailsResponse;
import com.anm.bslndmag.Model.ANMMotherData;
import com.anm.bslndmag.Model.Locations;
import com.anm.bslndmag.Model.LoginRequest;
import com.anm.bslndmag.Model.LoginResponse;
import com.anm.bslndmag.Model.LoginResponseData;
import com.anm.bslndmag.Model.OfflinechildDetails;
import com.anm.bslndmag.Model.OfflinemotherChildResponse;
import com.anm.bslndmag.Model.Offlinemotherchilddata;
import com.anm.bslndmag.Model.RequestANMMotherChild;
import com.anm.bslndmag.Model.ResponseData;
import com.anm.bslndmag.Session.LoginSession;
import com.anm.bslndmag.Utils.Utilsclass;
import com.chaos.view.PinView;

import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class LoginActivity extends AppCompatActivity {


    String tokendevice="";
    TextView tv_mobileno;
    EditText et_loginmobile;
    Button btn_lgoin;
    LoginSession ls;
    String loginfrom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ls = new LoginSession(this);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            TextView tv_version=findViewById(R.id.tv_version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final EditText txtmobile=(EditText)findViewById(R.id.et_loginmobile);

        txtmobile.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {

                }
                return false;
            }
        });

        btn_lgoin=(Button)findViewById(R.id.btn_lgoin);
        btn_lgoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
             Send_Login_Request(txtmobile.getText().toString());

            }
        });

        try {
        /*    FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("Firebase", "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            tokendevice = task.getResult().getToken();
                            Log.w("--tokendevice--", "getInstanceId failed"+ tokendevice);
                            //Toast.makeText(LoginActivity.this, tokendevice, Toast.LENGTH_LONG).show();
//                            // Log and toast
//                            if(dialog.isShowing())
//                            dialog.dismiss();
                            //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });*/
        }
        catch (Exception ex)
        {
//            if(dialog.isShowing())
//            dialog.dismiss();
            ex.printStackTrace();
            tokendevice="";
        }
    }

    private void setLanguage() {

    }


    public void Send_Login_Request(final String username)
    {
        if(username.matches(""))
        {
            Toasty.error(LoginActivity.this, R.string.entermobileno,Toasty.LENGTH_LONG).show();;
        }
        else if(username.trim().length()<10)
        {
            Toasty.error(LoginActivity.this, R.string.entercorrectmobileno,Toasty.LENGTH_LONG).show();;
        }

        else {
            final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
            try {
                final LoginRequest loginRequest =new LoginRequest(username,ls.getdeviceid(),
                        ls.getStr(getString(R.string.language)),
                        "android");
                //String header="eyJpdiI6IldLZnFCWVd4STRXOGdzeFhySmJZNEE9PSIsInZhbHVlIjoiUW95KzBzYkh5RzdxTnFwXC9PZXBJRGI5eWhmSTJJUFkxYnQ0dndwMHpycURyYXg3V3M2TWZwTlM4WDcyRzZUTmoiLCJtYWMiOiI2MDhlNzE2OGZiNTE0NTI4ZjdlN2Y0ZTVlZDlmYTNkMTBhOTE4MDQwNzliNGY2YTA5MzU4ZDgzMmNjYzFiYWMxIn0%3D";
                Call<LoginResponse> logincall = Api.getApi().LoginAuth(loginRequest);
//                dialog.setMessage(getString(R.string.pleasewaitlogin));
                dialog.setCancelable(false);
                dialog.show();
                logincall.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginres = response.body();
                        if(response.code()!=200)
                        {
                            if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                            {
                                Toasty.error(LoginActivity.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                            }
                            else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                            {
                                Toasty.error(LoginActivity.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toasty.error(LoginActivity.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                            }
                            }else {
                            ;//message();//body();
                            boolean responsestatus = loginres.getStatus();//message();//body();
                            String responsemsg = loginres.getMessage();//body();
                            if (responsestatus == false) {
                                String tnc = loginres.getTermsandcondition();
                                String pvc = loginres.getPrivacypolicy();
                                ls.SaveTermsandCondition(tnc);
                                ls.Save_Privacypolicy(pvc);
                                if (loginres.getStatusCode() == 101) {
                                    Intent intent = new Intent(LoginActivity.this, LoginFreshDevice.class);
                                    intent.putExtra("Mobile", loginRequest.getMobile());
                                    startActivityForResult(intent, 488);
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                    intent.putExtra("Mobile", loginRequest.getMobile());
                                    startActivityForResult(intent, 488);
                                }
                                Toasty.error(LoginActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                                ;
                                //ls.logout();
                                dialog.dismiss();
//                            Intent intent= new Intent(LoginActivity.this,LoginActivity.class);
//                            finishAffinity();
//                            startActivity(intent);
                                return;
                            } else {


                                Toasty.success(LoginActivity.this, loginres.getMessage(), Toasty.LENGTH_LONG).show();
                                LoginResponseData loginResponseData = loginres.getData();
                                String userid = loginResponseData.getId();
                                String usertoken = loginResponseData.getToken();
                                String mobile = loginResponseData.getMobile();
                                String email = loginResponseData.getEmail();
                                String name = loginResponseData.getName();
                                String tnc = loginResponseData.getTermsandcondition();
                                String pvc = loginResponseData.getPrivacypolicy();


                                if (userid.matches("") || usertoken.matches("")) {
                                    if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                                    {
                                        Toasty.error(LoginActivity.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                                    }
                                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                                    {
                                        Toasty.error(LoginActivity.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toasty.error(LoginActivity.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                                    }
                                } else {
                                   // Toasty.success(LoginActivity.this, R.string.somethingwentwrong, Toasty.LENGTH_LONG).show();

                                    ls.save_login_data(userid, name, mobile, email, usertoken);
                                    ls.SaveTermsandCondition(tnc);
                                    ls.Save_Privacypolicy(pvc);
                                    ls.setBool("is_login", true);
                                    ls.set_isneedRefresh("y");
                                   /* if(!Api.env.equalsIgnoreCase("P")) {
                                        if (Utilsclass.validateDate())
                                            Send_ANMDetails_Request(userid, usertoken, dialno);
                                        else {
                                            Toasty.error(LoginActivity.this, "licence Expired. ", Toasty.LENGTH_LONG).show();
                                            ;
                                        }
                                    }
                                    else
                                    {
                                        Send_ANMDetails_Request(userid, usertoken, dialno);
                                    }*/
                                    finish();
//                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);

                                }
                            }
                        }


                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                        {
                            Toasty.error(LoginActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                        }
                        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                        {
                            Toasty.error(LoginActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(LoginActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
            }catch (Exception ex)
            {
                ex.getMessage();
                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                {
                    Toasty.error(LoginActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                }
                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                {
                    Toasty.error(LoginActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                }
                else
                {
                    Toasty.error(LoginActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
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
                finish();
            }
    }

    private void getANMMotherData(final String anmmobile, final String userid, final String token) {

        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        try {
            RequestANMMotherChild requestANMMotherChild=new RequestANMMotherChild();
            requestANMMotherChild.setAnm_mobile(anmmobile);
            Call<OfflinemotherChildResponse> logincall = Api.getApioffline().getANMMotherChildData(token, userid,requestANMMotherChild);
//            dialog.setMessage(getString(R.string.pleasewaitlogin));
            dialog.setCancelable(false);
            dialog.show();
            logincall.enqueue(new Callback<OfflinemotherChildResponse>() {
                @Override
                public void onResponse(Call<OfflinemotherChildResponse> call, Response<OfflinemotherChildResponse> response) {
                    OfflinemotherChildResponse responseData = response.body();
                    int responsecode = response.code();//message();//body();
                    String responsemsg = response.message();//body();
                    if (responsecode == 401) {
                        LoginSession ls=new LoginSession(LoginActivity.this);
                        Toasty.error(LoginActivity.this,responsemsg,Toasty.LENGTH_LONG).show();;
                        ls.logout();
                        dialog.dismiss();
//                        Intent intent= new Intent(LoginActivity.this,LoginActivity.class);
//                        finishAffinity();
//                        startActivity(intent);
                        return;
                    }
                    else if (responsecode == 204) {
                        dialog.dismiss();
                        Toasty.error(LoginActivity.this, responsemsg,Toasty.LENGTH_LONG).show();;
                        return;
                    }
                    if (responseData != null) {
                        if (responseData.getStatus() == 200) {

                                ArrayList<OfflinechildDetails> data=responseData.getData();
                                DBInstance dbInstance=DBInstance.getInstance(LoginActivity.this);
                                dbInstance.clearOfflineTable();
                                ArrayList<Offlinemotherchilddata> offlinemotherchilddata=setDatainArrayList(data);
                                dbInstance.insert_OfflineMotherChilddata(offlinemotherchilddata);
                               // getANMChildData(userid,token,anmmobile);
                                LoginSession ls=new LoginSession(LoginActivity.this);
                                ls.setBool("is_login", true);
                                try {
                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                }
                                finish();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("isvialogin",true);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                        } else {
                            if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                            {
                                Toasty.error(LoginActivity.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                            }
                            else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                            {
                                Toasty.error(LoginActivity.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toasty.error(LoginActivity.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                        {
                            Toasty.error(LoginActivity.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                        }
                        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                        {
                            Toasty.error(LoginActivity.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(LoginActivity.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                        }
                    }
                    dialog.dismiss();
                    new Api().postService=null;
                }
                @Override
                public void onFailure(Call<OfflinemotherChildResponse> call, Throwable t) {
                    if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                    {
                        Toasty.error(LoginActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                    }
                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                    {
                        Toasty.error(LoginActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toasty.error(LoginActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
            if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
            {
                Toasty.error(LoginActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
            }
            else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
            {
                Toasty.error(LoginActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
            }
            else
            {
                Toasty.error(LoginActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
    }

    private ArrayList<Offlinemotherchilddata> setDatainArrayList(ArrayList<OfflinechildDetails> data) {
        ArrayList<Offlinemotherchilddata> offlinemotherchilddata=new ArrayList<>();
        if(data!=null)
        {
            Offlinemotherchilddata offlinemotherchilddata1=null;
            for(OfflinechildDetails offlinechildDetails:data)
            {
                try {
                    offlinemotherchilddata1 = new Offlinemotherchilddata();
                    offlinemotherchilddata1.setChild_id(offlinechildDetails.getChild_id());
                    offlinemotherchilddata1.setChild_name(offlinechildDetails.getChild_name());
                    offlinemotherchilddata1.setChild_contact(offlinechildDetails.getChild_contact());
                    offlinemotherchilddata1.setChild_dob(offlinechildDetails.getChild_dob());
                    offlinemotherchilddata1.setChild_status(offlinechildDetails.getChild_status());
                    offlinemotherchilddata1.setChild_unq_id(offlinechildDetails.getChild_unq_id());
                    offlinemotherchilddata1.setMthrs_db_id(offlinechildDetails.getMothers_data().get(0).get(0).getMthrs_db_id());
                    offlinemotherchilddata1.setMthrs_mbl_no(offlinechildDetails.getMothers_data().get(0).get(0).getMthrs_mbl_no());
//                offlinemotherchilddata1.setMthrs_mbl_no(offlinechildDetails.getMothers_data().get(0).get(0).getMthrs_mbl_no());
                    offlinemotherchilddata1.setMother_name(offlinechildDetails.getMother_name());
                    offlinemotherchilddata1.setDone_vaccines(Utilsclass.GetStringlisttostring(offlinechildDetails.getDone_vaccines()));
                    offlinemotherchilddata1.setToday_due_vaccines(Utilsclass.GetStringlisttostring(offlinechildDetails.getToday_due_vaccines()));
                    offlinemotherchilddata1.setFuture_due_vaccines(Utilsclass.GetStringlisttostring(offlinechildDetails.getFuture_due_vaccines()));
                    offlinemotherchilddata.add(offlinemotherchilddata1);
                }catch (Exception exeption)
                {

                }
            }
        }
        return offlinemotherchilddata;

    }

   /* private void getANMChildData(String userid, String token, String anmmobile) {

        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        try {
            RequestANMMotherChild requestANMMotherChild=new RequestANMMotherChild();
            requestANMMotherChild.setAnm_mobile(anmmobile);
            Call<ANMChildData> logincall = Api.getApi().getANMChildData(token, userid,requestANMMotherChild);
            dialog.setMessage(getString(R.string.pleasewaitlogin));
            dialog.setCancelable(false);
            dialog.show();
            logincall.enqueue(new Callback<ANMChildData>() {
                @Override
                public void onResponse(Call<ANMChildData> call, Response<ANMChildData> response) {
                    ANMChildData responseData = response.body();
                    int responsecode = response.code();//message();//body();
                    String responsemsg = response.message();//body();
                    if (responsecode == 401) {
                        LoginSession ls=new LoginSession(LoginActivity.this);
                        Toasty.error(LoginActivity.this,responsemsg,Toasty.LENGTH_LONG).show();;
                        ls.logout();
                        dialog.dismiss();
                        Intent intent= new Intent(LoginActivity.this,LoginActivity.class);
                        finishAffinity();
                        startActivity(intent);
                        return;
                    }
                    else if (responsecode == 204) {
                        dialog.dismiss();
                        Toasty.error(LoginActivity.this, responsemsg,Toasty.LENGTH_LONG).show();;
                        return;
                    }
                    if (responseData != null) {
                        if (responseData.getStatus() == 200) {


                            DBInstance dbInstance=DBInstance.getInstance(LoginActivity.this);
                            dbInstance.clearTable();
                          //  dbInstance.insert_Location(loginres.getLocations());


//                                finish();
//
//                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                intent.putExtra("isvialogin",true);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
                        } else {
                            Toasty.error(LoginActivity.this, R.string.somethingwentwrong,Toasty.LENGTH_LONG).show();
                        }
                    } else {
                        Toasty.error(LoginActivity.this, R.string.somethingwentwrong,Toasty.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
                @Override
                public void onFailure(Call<ANMChildData> call, Throwable t) {
                    Toasty.error(LoginActivity.this, R.string.checkinternet,Toasty.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
            Toasty.error(LoginActivity.this, R.string.checkinternet,Toasty.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }*/
}
