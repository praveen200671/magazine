package com.anm.bslndmag;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

;import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.Model.HomeAnnouncements;
import com.anm.bslndmag.Session.LoginSession;

//import com.igroms.executives.R;

public class Privacypolicy_Tnc extends AppCompatActivity {

    private WebView webView;
    private String istermsandconditions;
    LoginSession ls;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacypolicy__tnc);
        ls=new LoginSession(this);
         istermsandconditions=getIntent().getExtras().getString("istermsandconditions").toString();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);
        if(istermsandconditions.equalsIgnoreCase("true")) {
            setTitle("Terms & Conditions");
            url=Api.termsandcondition;
            webView.loadUrl(url);
        }
        else if (istermsandconditions.equalsIgnoreCase("plan")) {
            setTitle("Plans");
            String url=getIntent().getExtras().getString("url").toString();
            webView.loadUrl(url);
//            webView.loadUrl("http:\/\/bslndmag.ebizorders.com\/pay?amount=720&type=plan&user_id=1&data_id=1");

            webView.addJavascriptInterface(new Object()
            {
                @JavascriptInterface
                public void performClick()
                {
                    Log.d("planpurchased::", "planpurchased.....");
                    Intent intent=new Intent(Privacypolicy_Tnc.this,SubscriptionPlans.class);
                    setResult(888,intent);
                    finish();
                }
            }, "ok");
        }

        else if (istermsandconditions.equalsIgnoreCase("magazine")) {
            setTitle("Subscribe Magazine");
            String url=getIntent().getExtras().getString("url").toString();
            HomeAnnouncements cd =(HomeAnnouncements) getIntent().getExtras().get("homeannouncements");
            webView.loadUrl(url);
//            webView.loadUrl("http:\/\/bslndmag.ebizorders.com\/pay?amount=720&type=plan&user_id=1&data_id=1");

            webView.addJavascriptInterface(new Object()
            {
                @JavascriptInterface
                public void performClick()
                {
                    Log.d("planpurchased::", "planpurchased.....");
                    Intent intent=new Intent(Privacypolicy_Tnc.this,SubscriptionPlans.class);
                    intent.putExtra("homeannouncements",cd);
                    setResult(1099,intent);
                    finish();
                }
            }, "ok");
        }
        else {
            setTitle("Privacy & Policy");
            url=Api.privacypolicy;
            webView.loadUrl(url);
        }
    }


//    @SuppressLint("JavascriptInterface")
//    public void open(View view){
//        String url = field.getText().toString();
//        browser.getSettings().setLoadsImagesAutomatically(true);
//        browser.getSettings().setJavaScriptEnabled(true);
//        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        browser.loadUrl(url);
//        browser.addJavascriptInterface(new Object()
//        {
//            @JavascriptInterface
//            public void performClick()
//            {
//                Log.d("LOGIN::", "Clicked");
//                Toast.makeText(MainActivity.this, "Login clicked", Toast.LENGTH_LONG).show();
//            }
//        }, "login");
//
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    public class WebViewClientImpl extends WebViewClient {

        private Activity activity = null;

        public WebViewClientImpl(Activity activity) {
            this.activity = activity;
        }
        private ProgressDialog mProgress;
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mProgress == null) {
                mProgress = new ProgressDialog(Privacypolicy_Tnc.this);
                mProgress.show();
                mProgress.setMessage("Loading....");
            }
        }



        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {

//            if (istermsandconditions.equalsIgnoreCase("true")) {
//                if (url.indexOf("terms-and-conditions") > -1 ) return false;
////                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
////                activity.startActivity(intent);
//                return true;
//            }
//            else  {
//                if ( url.indexOf("privacy-policy") > -1) return false;
                webView.loadUrl(url);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                activity.startActivity(intent);
                return true;
//            }
        }
        @Override
        public void onPageFinished(WebView view, String url) {
          //  view.loadUrl("javascript:AppConfig.showBookmarkPrompt=false");
            try {
                if (mProgress != null)
                mProgress.dismiss();
                mProgress = null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }


}
