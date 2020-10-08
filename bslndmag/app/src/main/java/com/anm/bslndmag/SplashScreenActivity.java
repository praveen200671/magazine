package com.anm.bslndmag;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.anm.bslndmag.Session.LoginSession;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SplashScreenActivity extends AppCompatActivity {

    ProgressBar progressBar3;
    RadioGroup rg_language;
    LoginSession ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar3=findViewById(R.id.progressBar3);
        rg_language=findViewById(R.id.rg_language);
        ls = new LoginSession(SplashScreenActivity.this);
         String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

         ls.setdeviceid(android_id);
        if(ls.getStr(getString(R.string.language)).length()>0) {
            progressBar3.setVisibility(View.VISIBLE);
            rg_language.setVisibility(View.GONE);
            Thread background = new Thread() {
                public void run() {
                    try {
                        sleep(2 * 1000);
                        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        //intent.putExtra("isvialogin",false);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                    }
                }
            };
            background.start();
        }
        else {

            rg_language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId==R.id.rb_english)
                    {
//                        ls.setStr(getString(R.string.language), getString(R.string.language_english));
                        ls.setStr(getString(R.string.language), "1");
                        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        //intent.putExtra("isvialogin",false);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  if(checkedId==R.id.rb_hindi)
                    {
//                        ls.setStr(getString(R.string.language), getString(R.string.language_hindi));
                        ls.setStr(getString(R.string.language), "2");
                        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        //intent.putExtra("isvialogin",false);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else  if(checkedId==R.id.rb_gurmukhi)
                    {
//                        ls.setStr(getString(R.string.language), getString(R.string.language_gurmukhi));
                        ls.setStr(getString(R.string.language), "3");
                        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        //intent.putExtra("isvialogin",false);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            });


        }
    }
//    public void Send_Request_splash_screen_data(){
//        TokenSession tk = new TokenSession(SplashScreenActivity.this);
//        String tokenid = tk.getTokenVal();
//        String header = tk.getheader();
//
//        Call<SplashscreenResponse> call= Api.getApi().Splash_Screen(header,tokenid);
//        call.enqueue(new Callback<SplashscreenResponse>() {
//            @Override
//            public void onResponse(Call<SplashscreenResponse> call, Response<SplashscreenResponse> response) {
//                SplashscreenResponse res=response.body();
//                Boolean is_display=false;
//                if (res != null) {
//                    if (res.getFlag() == 1) {
//                        SplashscreenResponse.SplashscreenData data=res.getData();
//                        if(data!=null)
//                        {
//                            if(data.getForceDownload()==1)
//                            {
//                                is_display=true;
//                            }
//                            else{
//                                is_display=false;
//                            }
//                        }
//                        else{
//                            is_display=false;
//                        }
//                    } else if (res.getFlag() == 19) {
//                        TokenSession tk = new TokenSession(SplashScreenActivity.this);
//                        tk.generate_new_token();
//                        is_display=false;
//                        Send_Request_splash_screen_data();
//                    } else if (res.getFlag() == 0) {
//                        Toasty.error(SplashScreenActivity.this, res.getMsg()).show();
//                        is_display=false;
//                    }
//                    else{
//                        Toasty.error(SplashScreenActivity.this, "Something Went Wrong. Please try again.").show();
//                        is_display=false;
//                    }
//                } else {
//                    Toasty.error(SplashScreenActivity.this, "Something Went Wrong. Please try again.").show();
//                    is_display=false;
//                }
//
//                if(is_display)
//                {
//                    dialog_update();
//                }
//                else{
//                    Intent intent = new Intent(SplashScreenActivity.this,HomeActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SplashscreenResponse> call, Throwable t) {
//                Toasty.error(SplashScreenActivity.this, "Something Went Wrong. Please Check Your Internet Connection.",Toasty.LENGTH_LONG).show();;
//
//                Intent intent = new Intent(SplashScreenActivity.this,HomeActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
    void dialog_update(){
        if(!open_dialog_for_update())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
            builder.setTitle("Update");
            builder.setMessage("You need to update your app.");
            builder.setCancelable(false);
            builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String appPackageName = getPackageName();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appPackageName)));
                    }
                }
            });
            builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        }
        else{
            Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
    private Boolean open_dialog_for_update() {
        VersionChecker versionChecker = new VersionChecker();
        try {
            String appVersionName = "0";
            appVersionName = BuildConfig.VERSION_NAME;
            String mLatestVersionName = "0";
            mLatestVersionName = versionChecker.execute().get();
            if (compareVersionNames(appVersionName, mLatestVersionName) < 0) {
                /*final Dialog dialog = new Dialog(this);
                ConstraintLayout featureLayout = (ConstraintLayout) View.inflate(this, R.layout.update_version_dialog, null);
                dialog.setContentView(featureLayout);
                dialog.getWindow().setLayout((6 * getResources().getDisplayMetrics().widthPixels) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                Button btnupdate = (Button) dialog.findViewById(R.id.btnupdatenow);
                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String appPackageName = getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.yokon.pc.yokon")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.yokon.pc.yokon")));
                        }
                    }
                });

                dialog.show();*/

                return false;
            } else {
                return true;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return true;
        }
    }
    public int compareVersionNames(String oldVersionName, String newVersionName) {
        int res = 0;

        String[] oldNumbers = oldVersionName.split("\\.");
        String[] newNumbers = newVersionName.split("\\.");

        // To avoid IndexOutOfBounds
        int maxIndex = Math.min(oldNumbers.length, newNumbers.length);

        for (int i = 0; i < maxIndex; i++) {
            int oldVersionPart = Integer.valueOf(oldNumbers[i]);
            int newVersionPart = Integer.valueOf(newNumbers[i]);

            if (oldVersionPart < newVersionPart) {
                res = -1;
                break;
            } else if (oldVersionPart > newVersionPart) {
                res = 1;
                break;
            }
        }

        // If versions are the same so far, but they have different length...
        if (res == 0 && oldNumbers.length != newNumbers.length) {
            res = (oldNumbers.length > newNumbers.length) ? 1 : -1;
        }

        return res;
    }
    @SuppressLint("StaticFieldLeak")
    public class VersionChecker extends AsyncTask<String, String, String> {
        private String newVersion;

        @Override
        protected String doInBackground(String... params) {

            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="+getPackageName())
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;
        }
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

}
