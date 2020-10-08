package com.anm.bslndmag;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.telephony.SmsMessage;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.Adapter.CustomPagerAdapter;
import com.anm.bslndmag.Adapter.IssueandAnnoucementAdapter;
import com.anm.bslndmag.Adapter.DueVaccinationAdapter;
import com.anm.bslndmag.Model.DueVaccinesList;
import com.anm.bslndmag.Model.HomeAnnouncements;
import com.anm.bslndmag.Model.HomeRequest;
import com.anm.bslndmag.Model.HomeResponse;
import com.anm.bslndmag.Model.HomeResponseData;
import com.anm.bslndmag.Model.MagazineResponse;
import com.anm.bslndmag.Model.SubscribePlanRequest;
import com.anm.bslndmag.Request.APIRequest;
import com.anm.bslndmag.Session.LoginSession;
import com.anm.bslndmag.Utils.Utilsclass;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static Uri outputFileUri;
    ConstraintLayout dynamicContent;
    SwipeRefreshLayout swipeRefresh;
    ProgressDialog dialog;
    RecyclerView recyclerViewProductData, recyclerViewcartProductData, recyclervieworderhistory;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CAMERA = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static int RESULT_LOAD_IMAGE = 1;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 800;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.

    File imguserFile;
    APIRequest apiRequest;
    String fname;
    private View wizardView;
    private TextView tv_nodatafound;
    private RecyclerView rl_weeklycases;
    private DueVaccinationAdapter adapterdueVaccines;
    private ArrayList<DueVaccinesList> arrayListduevaccinationname;
    private int index = 0;
    HashSet<String> childcategoryselecteditem = new HashSet<>();
    String allstatus = "";
    String parentcategoryselecteditem = "";
    public ArrayList<String> arrayselectedcategoryid = new ArrayList<>();
    int is_retailer = 0;
    int resultcode = 9899;
    int resultmaincategorycode = 9877;
    ImageView toolbar_icon;
    TextView toolbar_name;
    TextView toolbar_mobile;
    TextView toolbar_address;
    LoginSession ls;
    private long lastPressedTime;
    private static final int PERIOD = 2000;
    private boolean isvialogin = false;
    private static final int REQUEST_CALL_PHONE= 1;
    private static final int REQUEST_READ_SMS= 2;


    private static String[] PERMISSIONS_CALL_PHONE = {
            Manifest.permission.CALL_PHONE,
    };
    private BroadcastReceiver receiver;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
    LinearLayout ll_home,ll_issues,ll_articles,ll_settings;
    ImageView iv_home,iv_issues,iv_articles,iv_settings;
    TextView tv_home,tv_issues,tv_articles,tv_settings,tv_recentarticles,tv_recentissue;
    ViewPager vw_anouncements;
    RecyclerView rv_anouncements;
    RecyclerView rv_magazines;
    SearchView sv_searchitems;
    ArrayList<HomeAnnouncements> arrayList_announcements;
    ArrayList<HomeAnnouncements> arrayList_magazine;
    IssueandAnnoucementAdapter issueandAnnoucementAdapter;
    IssueandAnnoucementAdapter magazineAdapter;

    int NUM_PAGES=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        views();
        setClicklistener();

        arrayList_announcements =new ArrayList<>();
        arrayList_magazine =new ArrayList<>();
//        AddLocalissue();
//        AddLocalissue2();



        issueandAnnoucementAdapter =new IssueandAnnoucementAdapter(this, arrayList_announcements, new IssueandAnnoucementAdapter.vaccinationselectlistener() {
            @Override
            public void chkselectcategory(HomeAnnouncements  cd,String isRead) {
                Intent intent=new Intent(HomeActivity.this,AnnouncementDetails.class);
                intent.putExtra("imageurl",cd.getImages().get(0));
                startActivity(intent);

            }
        });

        magazineAdapter=new IssueandAnnoucementAdapter(this, arrayList_magazine, new IssueandAnnoucementAdapter.vaccinationselectlistener() {
            @Override
            public void chkselectcategory(HomeAnnouncements  cd,String isRead) {
//
                if(isRead.equalsIgnoreCase("buy")) {
                    Log.e("buttonclicked","buy");


                    if(ls.get_auth_token().length()==0)
                    {

                        Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                        intent.putExtra("purpose", "buy" );
                        intent.putExtra("obj",cd);
                        startActivityForResult(intent,688);
                    }
                    else if(ls.get_auth_token().length()>0 && cd.getIs_free().equalsIgnoreCase("0")) {
//                        send_subscribe_Request(false,cd);
//                            send_MagazineDetails_Request(cd,"buy");
                        Intent intent=new Intent(HomeActivity.this,ReadMagazine.class);
                        intent.putExtra("magazine",cd);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent=new Intent(HomeActivity.this,ReadMagazine.class);
                        intent.putExtra("magazine",cd);
                        startActivity(intent);
                    }
                }
                else  {
                    Log.e("buttonclicked","Read");
                    if(ls.get_auth_token().length()==0)
                    {
                        Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                        intent.putExtra("purpose","read");
                        intent.putExtra("obj",cd);
                        startActivityForResult(intent,788);
                    }
                    else if(ls.get_auth_token().length()>0 && cd.getIs_free().equalsIgnoreCase("1"))
                    {
                        send_MagazineDetails_Request(cd,"read");
                    }
                    else  if(ls.get_auth_token().length()>0 && cd.getIs_free().equalsIgnoreCase("0"))
                    {
                        if(cd.getIs_subscribe().equalsIgnoreCase("1"))
                        send_MagazineDetails_Request(cd,"read");
                        else {
                            Intent intent = new Intent(HomeActivity.this, ReadMagazine.class);
                            intent.putExtra("magazine", cd);
                            startActivity(intent);
                        }
                    }
                }

            }
        });


        LinearLayoutManager  horizontalLayout
                = new LinearLayoutManager(
                HomeActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        rv_anouncements.setLayoutManager(horizontalLayout);
        rv_anouncements.setItemAnimator(new DefaultItemAnimator());
        rv_anouncements.setAdapter(issueandAnnoucementAdapter);
        issueandAnnoucementAdapter.notifychanges(arrayList_announcements);


//         AdapterViewFlipper adapterViewFlipper=new
        LinearLayoutManager  horizontal
                = new LinearLayoutManager(
                HomeActivity.this,
                LinearLayoutManager.HORIZONTAL,
                false);
        rv_magazines.setLayoutManager(horizontal);
        rv_magazines.setItemAnimator(new DefaultItemAnimator());
        rv_magazines.setAdapter(magazineAdapter);
        magazineAdapter.notifychanges(arrayList_magazine);
        try {
            int permissionsms = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Send_homepage_Request();

       // vw_anouncements.notify();
////        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        myadapter = new SlideAdapter(this);
//        viewPager.setAdapter(myadapter);


    }

    private void send_MagazineDetails_Request(final HomeAnnouncements cd, final String isread) {

        final ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
        try {
//hardcode...
        final SubscribePlanRequest loginRequest =new SubscribePlanRequest("2",//ls.getStr(getString(R.string.language)),
                cd.getId(),ls.getdeviceid(),
                "android",
                "magazine"
        );
        String token=ls.get_auth_token();
        if(!token.toLowerCase().startsWith("bearer"))
        {
            token=""+token;
        }
        Call<MagazineResponse> logincall = Api.getApi().Magazine(loginRequest,token);
        dialog.setCancelable(false);
        dialog.show();
        logincall.enqueue(new Callback<MagazineResponse>() {
            @Override
            public void onResponse(Call<MagazineResponse> call, Response<MagazineResponse> response) {
                MagazineResponse loginres = response.body();
                if(response.code()!=200)
                {
                    if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                    {
                        Toasty.error(HomeActivity.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                    }
                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                    {
                        Toasty.error(HomeActivity.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toasty.error(HomeActivity.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                    }
                }else {
                    ;//message();//body();
                    boolean responsestatus = loginres.isStatus();//message();//body();
                    String responsemsg = loginres.getMessage();//body();
                    if (responsestatus == false) {
                        Toasty.error(HomeActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                        dialog.dismiss();
                        return;
                    } else {
//                            Toasty.success(SubscriptionPlans.this, loginres.getMessage(), Toasty.LENGTH_LONG).show();
                        HomeAnnouncements loginResponseData = loginres.getData();
                        if(loginResponseData.getUrl()!=null) {
                            openWebViewtoSubscribe(loginResponseData.getUrl(),loginResponseData);
                        }
                        else
                        {
                            Intent intent=new Intent(HomeActivity.this,ReadMagazine.class);
                               intent.putExtra("magazine",loginResponseData);
                                startActivity(intent);
                        }

                    }
                }


                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<MagazineResponse> call, Throwable t) {
                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                {
                    Toasty.error(HomeActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                }
                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                {
                    Toasty.error(HomeActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                }
                else
                {
                    Toasty.error(HomeActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
    }catch (Exception ex)
    {
        ex.getMessage();
        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
        {
            Toasty.error(HomeActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
        }
        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
        {
            Toasty.error(HomeActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
        }
        else
        {
            Toasty.error(HomeActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
        }
//        dialog.dismiss();
    }

    }

    private void openWebViewtoSubscribe(String url,HomeAnnouncements cd) {
        Intent intent=new Intent(HomeActivity.this,Privacypolicy_Tnc.class);
        intent.putExtra("istermsandconditions","magazine");
        intent.putExtra("url",url);
        intent.putExtra("homeannouncements",cd);
        startActivityForResult(intent,9999);
    }


    private void send_subscribe_Request(boolean isRead,HomeAnnouncements homeAnnouncements) {
            {
//                dsfsdfsd
                final ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
                try {
                    final HomeRequest loginRequest =new HomeRequest(ls.getdeviceid(),
//                        ls.getStr(getString(R.string.language)),
                            "2",
                            "android");

                    String header=ls.get_auth_token();
                    Call<HomeResponse> logincall = Api.getApi().getHomeData(header,loginRequest);
                    //dialog.setMessage(getString(R.string.pleasewaitlogin));
                    dialog.setCancelable(false);
                    dialog.show();
                    logincall.enqueue(new Callback<HomeResponse>() {
                        @Override
                        public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                            HomeResponse loginres = response.body();
                            if(response.code()!=200)
                            {
                                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                                {
                                    Toasty.error(HomeActivity.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                                }
                                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                                {
                                    Toasty.error(HomeActivity.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toasty.error(HomeActivity.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                                }
                            }else {
                                boolean responsestatus = loginres.isStatus();//message();//body();
                                String responsemsg = loginres.getMessage();//body();
                                if (responsestatus == false) {
                                    if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                                    {
                                        Toasty.error(HomeActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                                    }
                                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                                    {
                                        Toasty.error(HomeActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Toasty.error(HomeActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                                    }
                                    dialog.dismiss();
                                } else {
                                    HomeResponseData loginResponseData = loginres.getData();
                                    ArrayList<HomeAnnouncements> homeAnnouncements = loginResponseData.getAnnouncement();
                                    ArrayList<HomeAnnouncements> homemagazines = loginResponseData.getMagazine();
                                    arrayList_announcements=homeAnnouncements;
                                    arrayList_announcements=removemagazine(homeAnnouncements);
                                    issueandAnnoucementAdapter.notifychanges(arrayList_announcements);
                                    arrayList_magazine=homemagazines;
                                    vw_anouncements.setAdapter(new CustomPagerAdapter(HomeActivity.this, arrayList_announcements, new CustomPagerAdapter.vaccinationselectlistener() {
                                        @Override
                                        public void onclick(String url) {
                                            Intent intent=new Intent(HomeActivity.this,AnnouncementDetails.class);
                                            intent.putExtra("imageurl",url);
                                            startActivity(intent);
                                        }
                                    }));
                                    magazineAdapter.notifychanges(arrayList_magazine);


//                                homeAnnouncements=loginResponseData.getAnnouncement();
                                    //HomeAnnouncements homeAnnouncements=loginResponseData.getAnnouncement();
                                    Starttime();
                                }
                            }
                            dialog.dismiss();
                        }
                        @Override
                        public void onFailure(Call<HomeResponse> call, Throwable t) {
                            if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                            {
                                Toasty.error(HomeActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                            }
                            else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                            {
                                Toasty.error(HomeActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toasty.error(HomeActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        }
                    });
                }catch (Exception ex)
                {
                    ex.getMessage();
                    if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                    {
                        Toasty.error(HomeActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                    }
                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                    {
                        Toasty.error(HomeActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toasty.error(HomeActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            }
        }


        private void Starttime() {
        NUM_PAGES=arrayList_announcements.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES-1) {
                    currentPage = 0;
                }
                vw_anouncements.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    private void AddLocalissue() {

       /* HomeAnnouncements addMotherRequest=new HomeAnnouncements();
        addMotherRequest.setDate("12-Sep-2020");
        addMotherRequest.setHeader("Reader Digest India");
        addMotherRequest.setPrice("Rs 50/-");
        arrayList_announcements.add(addMotherRequest);*/
//        addMotherRequest.setImageurl((R.drawable.magazineissue));
    }
    private void AddLocalissue2() {

       /* HomeAnnouncements addMotherRequest=new HomeAnnouncements();
        addMotherRequest.setDate("12-Aug-2020");
        addMotherRequest.setHeader("Writer Digest US");
        addMotherRequest.setPrice("Rs 40/-");
        arrayList_announcements.add(addMotherRequest);*/
//        addMotherRequest.setImageurl((R.drawable.magazineissue));
    }

    private void views() {
        ll_home=findViewById(R.id.ll_home);
        ll_issues=findViewById(R.id.ll_issues);
        ll_articles=findViewById(R.id.ll_articles);
        ll_settings=findViewById(R.id.ll_settings);
        iv_home=findViewById(R.id.iv_home);
        sv_searchitems=findViewById(R.id.sv_searchitems);
        vw_anouncements=findViewById(R.id.vw_anouncements);
        rv_anouncements=findViewById(R.id.rv_anouncements);
        rv_magazines=findViewById(R.id.rv_magazines);
        iv_issues=findViewById(R.id.iv_issues);
        iv_articles=findViewById(R.id.iv_article);
        iv_settings=findViewById(R.id.iv_settings);
        tv_home=findViewById(R.id.tv_home);

        tv_issues=findViewById(R.id.tv_issues);
        tv_articles=findViewById(R.id.tv_article);
//        tv_recentissue=findViewById(R.id.tv_recentissue);
//        tv_recentarticles=findViewById(R.id.tv_recentarticles);
        tv_settings=findViewById(R.id.tv_settings);
        ls = new LoginSession(this);
    }

    private void setClicklistener() {
        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(1);
                Toasty.success(HomeActivity.this,"Coming soon").show();
            }
        });

        ll_issues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(2);
                Toasty.success(HomeActivity.this,"Coming soon").show();
            }
        });
        ll_articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(3);
                Toasty.success(HomeActivity.this,"Coming soon").show();
            }
        });
        ll_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(4);
                Intent intent = new Intent(HomeActivity.this, Settings.class);
                startActivity(intent);

            }
        });
    }

    private void changeColor(int i) {
        if(i==1)
        {
            iv_home.setImageResource(R.drawable.ic_home_blue);
            iv_issues.setImageResource(R.drawable.ic_issue);
            iv_articles.setImageResource(R.drawable.ic_article);
            iv_settings.setImageResource(R.drawable.ic_settings_black);
            tv_home.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_issues.setTextColor(getResources().getColor(R.color.gray));
            tv_articles.setTextColor(getResources().getColor(R.color.gray));
            tv_settings.setTextColor(getResources().getColor(R.color.gray));
        }
        else  if(i==2)
        {
            iv_home.setImageResource(R.drawable.ic_home);
            iv_issues.setImageResource(R.drawable.ic_issue_blue);
            iv_articles.setImageResource(R.drawable.ic_article);
            iv_settings.setImageResource(R.drawable.ic_settings_black);
            tv_home.setTextColor(getResources().getColor(R.color.gray));
            tv_issues.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_articles.setTextColor(getResources().getColor(R.color.gray));
            tv_settings.setTextColor(getResources().getColor(R.color.gray));
        }
        else  if(i==3)
        {
            iv_home.setImageResource(R.drawable.ic_home);
            iv_issues.setImageResource(R.drawable.ic_issue);
            iv_articles.setImageResource(R.drawable.ic_article_blue);
            iv_settings.setImageResource(R.drawable.ic_settings_black);
            tv_home.setTextColor(getResources().getColor(R.color.gray));
            tv_issues.setTextColor(getResources().getColor(R.color.gray));
            tv_articles.setTextColor(getResources().getColor(R.color.colorPrimary));
            tv_settings.setTextColor(getResources().getColor(R.color.gray));
        }
        else  if(i==4)
        {
            iv_home.setImageResource(R.drawable.ic_home);
            iv_issues.setImageResource(R.drawable.ic_issue);
            iv_articles.setImageResource(R.drawable.ic_article);
            iv_settings.setImageResource(R.drawable.ic_settings_blue);
            tv_home.setTextColor(getResources().getColor(R.color.gray));
            tv_issues.setTextColor(getResources().getColor(R.color.gray));
            tv_articles.setTextColor(getResources().getColor(R.color.gray));
            tv_settings.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.home, menu);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
//            home_layout();

            // Handle the camera action
        } else if (id == R.id.nav_sync) {
//            Intent intent = new Intent(HomeActivity.this, Activity_Sync.class);
//            startActivity(intent);
        }
//        else if (id == R.id.nav_dial_sync) {
//            Intent intent = new Intent(HomeActivity.this, Activity_Sync_dialer.class);
//            startActivity(intent);
//
//            // Handle the camera action
//        }
        else if (id == R.id.nav_addmother) {
//            if(!shouldaskPermission()) {
                Intent intent = new Intent(HomeActivity.this, Settings.class);
                startActivity(intent);
//            }
//            else
//            {
//                verifypermission();
//            }

            // Handle the camera action
        } else if (id == R.id.nav_setpin) {
            Intent intent = new Intent(HomeActivity.this, DialogActivityVaccines.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Logout_Session();
        }
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver!=null)
            try {
                unregisterReceiver(receiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }



    public void Send_homepage_Request()
    {
        {
            final ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
            try {
                final HomeRequest loginRequest =new HomeRequest(ls.getdeviceid(),
//                        ls.getStr(getString(R.string.language)),
"2",
                        "android");

                String header=ls.get_auth_token();
                Call<HomeResponse> logincall = Api.getApi().getHomeData(header,loginRequest);
                //dialog.setMessage(getString(R.string.pleasewaitlogin));
                dialog.setCancelable(false);
                dialog.show();
                logincall.enqueue(new Callback<HomeResponse>() {
                    @Override
                    public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                        HomeResponse loginres = response.body();
                        if(response.code()!=200)
                        {
                            if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                            {
                                Toasty.error(HomeActivity.this, R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                            }
                            else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                            {
                                Toasty.error(HomeActivity.this, R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toasty.error(HomeActivity.this, R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                            }
                        }else {
                            boolean responsestatus = loginres.isStatus();//message();//body();
                            String responsemsg = loginres.getMessage();//body();
                            if (responsestatus == false) {
                                 if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                                {
                                    Toasty.error(HomeActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                                }
                                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                                {
                                    Toasty.error(HomeActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toasty.error(HomeActivity.this, responsemsg, Toasty.LENGTH_LONG).show();
                                }
                                dialog.dismiss();
                            } else {
                                HomeResponseData loginResponseData = loginres.getData();
                                ArrayList<HomeAnnouncements> homeAnnouncements = loginResponseData.getAnnouncement();
                                ArrayList<HomeAnnouncements> homemagazines = loginResponseData.getMagazine();
                                arrayList_announcements=homeAnnouncements;
                                arrayList_announcements=removemagazine(homeAnnouncements);
                                issueandAnnoucementAdapter.notifychanges(arrayList_announcements);
                                arrayList_magazine=homemagazines;
                                vw_anouncements.setAdapter(new CustomPagerAdapter(HomeActivity.this, arrayList_announcements, new CustomPagerAdapter.vaccinationselectlistener() {
                                    @Override
                                    public void onclick(String url) {
                                        Intent intent=new Intent(HomeActivity.this,AnnouncementDetails.class);
                                            intent.putExtra("imageurl",url);
                                            startActivity(intent);
                                    }
                                }));
                                magazineAdapter.notifychanges(arrayList_magazine);


//                                homeAnnouncements=loginResponseData.getAnnouncement();
                                //HomeAnnouncements homeAnnouncements=loginResponseData.getAnnouncement();
                                Starttime();
                            }
                        }
                        dialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<HomeResponse> call, Throwable t) {
                        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                        {
                            Toasty.error(HomeActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                        }
                        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                        {
                            Toasty.error(HomeActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(HomeActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
            }catch (Exception ex)
            {
                ex.getMessage();
                if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                {
                    Toasty.error(HomeActivity.this, (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                }
                else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                {
                    Toasty.error(HomeActivity.this, (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                }
                else
                {
                    Toasty.error(HomeActivity.this, (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        }
    }

    private ArrayList<HomeAnnouncements> removemagazine(ArrayList<HomeAnnouncements> homeAnnouncements) {

        ArrayList<HomeAnnouncements> homeAnnouncements1 = new ArrayList<>();
        for(HomeAnnouncements homeAnnouncement:homeAnnouncements)
            {
                if(homeAnnouncement.getType().equalsIgnoreCase("custom")) {
                    homeAnnouncements1.add(homeAnnouncement);
                }
        }
        return homeAnnouncements1;
    }

    public void readsms()
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                {
                    final Bundle bundle = intent.getExtras();
                    try {
                        if (bundle != null) {
                            final Object[] pdusObj = (Object[]) bundle.get("pdus");

                            for (int i = 0; i < pdusObj.length; i++) {

                                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                                String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                                Toasty.success(HomeActivity.this,"message recieved").show();
                                String senderNum = phoneNumber;
                                String message = currentMessage.getDisplayMessageBody();

                                Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                                try
                                {
//                                    if (senderNum .equals("VM-DRDODA") || senderNum .equals("VK-DRDODA") || senderNum .equals("AM-DRDODA")
//                                            || senderNum .equals("AD-DRDODA") || senderNum .equals("MD-DRDODA") || senderNum .equals("DM-DRDODA")
//                                            || senderNum .equals("IM-DRDODA") ||senderNum .equals("DD-DRDODA")||senderNum .equals("HP-DRDODA"))
//                                    {
                                        Utilsclass.recivedSms(message,context);
//                                    }
                                }
                                catch(Exception e){

                                }
                            }
                        }

                    } catch (Exception e) {
                        Log.e("SmsReceiver", "Exception smsReceiver" +e);
                    }
                }
            }
        };
        registerReceiver(receiver, intentFilter);

    }

    private void open_tnc() {

//        Intent intent=   new Intent(HomeActivity.this, Privacypolicy_Tnc.class);
//        intent.putExtra("istermsandconditions","true");
//        startActivity(intent);

    }

    private void openprvcypolicy() {

//        Intent intent=   new Intent(HomeActivity.this, Privacypolicy_Tnc.class);
//        intent.putExtra("istermsandconditions","false");
//        startActivity(intent);

    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

//    private void requestPermission() {
//
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.CAMERA},
//                REQUEST_CAMERA);
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CAMERA:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
//                    // main logic
//                } else {
//                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                                != PackageManager.PERMISSION_GRANTED) {
//                            showMessageOKCancel("You need to allow access permissions",
//                                    new DialogInterface.OnClickListener() {
//
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermission();
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                }
//                break;
//        }
//    }

//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(HomeActivity.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }

    public Intent getPickImageIntent(Context context) {

        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "amfb" + File.separator);
        root.mkdir();
        fname = "img_" + System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
                outputFileUri = Uri.fromFile(sdImageMainDirectory);
                //shareImage(Uri.fromFile(new File(Path)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
        }

        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<>();

        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra("return-data", true);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        intentList = addIntentsToList(context, intentList, pickIntent);
        intentList = addIntentsToList(context, intentList, takePhotoIntent);

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    "Pick Photo");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
        return list;
    }

    public static Boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN: {
                    if (event.getDownTime() - lastPressedTime < PERIOD) {
                        System.exit(0);
                        finishAffinity();

                    } else {
                        Toast.makeText(getApplicationContext(), "Press again to exit.",
                                Toast.LENGTH_SHORT).show();
                        lastPressedTime = event.getEventTime();
                    }
                }
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            setPreferredLanguage();
            String refresh = ls.get_isneedRefresh();
            if (refresh.equalsIgnoreCase("y"))
            {
                ls.set_isneedRefresh("N");
                Send_homepage_Request();
            }
//            DBInstance dbInstance=DBInstance.getInstance(HomeActivity.this);
//            ArrayList<DueVaccinesList> dueVaccinesListArrayListlocal=dbInstance.getDueVaccinations();
//            if(dueVaccinesListArrayListlocal!=null)
//            {
//                for(DueVaccinesList dueVaccineobj:dueVaccinesListArrayListlocal)
//                {
//                    ArrayList<SearchedChildDetails> searchedChildDetailslist=dbInstance.get_DueChildDetailsByMother_ID(dueVaccineobj.getMthr_id());
//                    if(searchedChildDetailslist.size()==0)
//                    {
//                        dbInstance.deleteDueMotherVaccinationDatabyMother_id(dueVaccineobj.getMthr_id());
//                    }
//                }
//            }
//            try {
//                //Thread.sleep(00);
//            }catch (Exception e){
//
//            }

//            LoginSession ls = new LoginSession(HomeActivity.this);
//            arrayListduevaccinationname = dbInstance.getDueVaccinations();
//            DueVaccinesList dueVaccinesList = new DueVaccinesList();
//            dueVaccinesList.setMother_name("---addmother---");
//            arrayListduevaccinationname.add(dueVaccinesList);
//            if (arrayListduevaccinationname.size() > 1) {
//                tv_nodatafound.setVisibility(View.GONE);
//                rl_weeklycases.setVisibility(View.VISIBLE);
//            } else {
//                tv_nodatafound.setVisibility(View.VISIBLE);
//                rl_weeklycases.setVisibility(View.VISIBLE);
//            }
//            adapterdueVaccines.notifyadater(arrayListduevaccinationname);
        } catch (Exception ex) {
//            Log.e("exception--11", ex.getMessage());
        }


    }

    private void setPreferredLanguage() {
        if(ls.getStr(getString(R.string.language)).equals(getString(R.string.language_english)))
        {
            tv_home.setText(getString(R.string.str_home_english));
            tv_issues.setText(getString(R.string.str_issues_english));
            tv_articles.setText(getString(R.string.str_article_english));
            tv_settings.setText(getString(R.string.str_settings_english));
        }
        else  if(ls.getStr(getString(R.string.language)).equals(getString(R.string.language_hindi)))
        {
            tv_home.setText(getString(R.string.str_home_hindi));
            tv_issues.setText(getString(R.string.str_issues_hindi));
            tv_articles.setText(getString(R.string.str_article_hindi));
            tv_settings.setText(getString(R.string.str_settings_hindi));
        }
        else  if(ls.getStr(getString(R.string.language)).equals(getString(R.string.language_gurmukhi)))
        {
            tv_home.setText(getString(R.string.str_home_gurmukhi));
            tv_issues.setText(getString(R.string.str_issues_gurmukhi));
            tv_articles.setText(getString(R.string.str_article_gurmukhi));
            tv_settings.setText(getString(R.string.str_settings_gurmukhi));
        }
    }

    /* public void Send_getchilddetailsbyid_Request(final ArrayList<DueVaccinesList> arrayList) {
         final ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
         try {
             DueVaccinesList dueVaccinesList=arrayList.get(index);
             SearchChildRequest searchChildRequest=new SearchChildRequest();
             searchChildRequest.setChild_id(dueVaccinesList.getChild_id());
             searchChildRequest.setMobile(dueVaccinesList.getChild_contact());

             String userid = ls.get_user_id();
             String token = ls.get_auth_token();
             Call<SearchChildResponse> logincall = Api.getApi().getChilddetailsbyID(token, userid,searchChildRequest);

             dialog.setMessage(getString(R.string.pleasewaitlogin));
             dialog.setCancelable(false);
             dialog.show();
             logincall.enqueue(new Callback<SearchChildResponse>() {
                 @Override
                 public void onResponse(Call<SearchChildResponse> call, Response<SearchChildResponse> response) {
                     SearchChildResponse responseData = response.body();
                     int responsecode = response.code();//message();//body();
                     String responsemsg = response.message();
                     if (responsecode == 401) {
                         Toasty.error(HomeActivity.this, responsemsg,Toasty.LENGTH_LONG).show();;
                         ls.logout();
                         dialog.dismiss();
                         Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                         finishAffinity();
                         startActivity(intent);
                         return;
                     }
                     else if (responsecode == 204) {
                         Toasty.error(HomeActivity.this, responsemsg,Toasty.LENGTH_LONG).show();;
                         dialog.dismiss();
                         //finish();
                         return;
                     }
                     if (responseData != null) {
                         if (responseData.getStatus() == 200) {
                             SearchedChildDetails searchedChildDetails = responseData.getData();
                             DBInstance dbInstance=DBInstance.getInstance(HomeActivity.this);
                             dbInstance.insert_DueChildDetailsList(searchedChildDetails);
                             index++;
                             dialog.dismiss();
                             Send_getchilddetailsbyid_Request(arrayList);
 //                            DBInstance dbInstance=DBInstance.getInstance(Updatechild.this);
 //                            dbInstance.insert_SearchedChildDetails(searchedChildDetails);
 //                            SearchedChildDetails arrayListduevaccinationname =new ArrayList<>();
 //                            arrayListduevaccinationname=  DBInstance.getInstance(Updatechild.this).getDueVaccinations();
 //                            if(arrayListduevaccinationname.size()>0){
 //                                tv_nodatafound.setVisibility(View.GONE);
 //                                rl_weeklycases.setVisibility(View.VISIBLE);}
 //                            else
 //                            {
 //                                tv_nodatafound.setVisibility(View.VISIBLE);
 //                                rl_weeklycases.setVisibility(View.GONE);
 //                            }



 //                            adapterdueVaccines.notifyadater(arrayListduevaccinationname);
 //                            adapterdueVaccines.notifyDataSetChanged();

                            // setDataoform(searchedChildDetails);
                         } else {
                             Toasty.error(HomeActivity.this, R.string.somethingwentwrong,Toasty.LENGTH_LONG).show();;
                             finish();
                         }
                         dialog.dismiss();

                     }
                 }

                 @Override
                 public void onFailure(Call<SearchChildResponse> call, Throwable t) {
                     if(t.getMessage().contains(" BEGIN_OBJECT but was BOOLEAN"))
                     {
                         Toasty.error(HomeActivity.this, R.string.norecordfound,Toasty.LENGTH_LONG).show();;
                         finish();
                     }
                     else

                         Toasty.error(HomeActivity.this, R.string.checkinternet,Toasty.LENGTH_LONG).show();;
                     dialog.dismiss();
                     finish();
                 }
             });
         } catch (Exception ex) {
             ex.getMessage();
             Toasty.error(HomeActivity.this, R.string.checkinternet,Toasty.LENGTH_LONG).show();;
             dialog.dismiss();
             finish();
         }

     }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 688) {
                String id=data.getExtras().getString("id");
                String isfree=data.getExtras().getString("isfree");
                if (ls.get_auth_token().length() > 0) {
//                   Toasty.error()
                }
                else if (ls.get_auth_token().length() > 0 && id.length()>0) {

                }
            }
            else if (requestCode == 9999) {
                HomeAnnouncements cd=(HomeAnnouncements)data.getExtras().get("homeannouncements");
               send_MagazineDetails_Request(cd, "read");
                           }



    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(HomeActivity.this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void home_layout() {
        setTitle(getString(R.string.app_name));
        dynamicContent = (ConstraintLayout) findViewById(R.id.mainconstraintlayout);
        dynamicContent.removeAllViews();
        wizardView = getLayoutInflater()
                .inflate(R.layout.layout_home, dynamicContent, false);
        dynamicContent.addView(wizardView);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);

            }
        });

    }

    private void showDuelist() {
        arrayListduevaccinationname = new ArrayList<>();
        arrayListduevaccinationname = DBInstance.getInstance(this).getDueVaccinations();

        DueVaccinesList dueVaccinesList = new DueVaccinesList();
        dueVaccinesList.setMother_name("--addmother---");
        arrayListduevaccinationname.add(dueVaccinesList);
        if (arrayListduevaccinationname.size() == 1) {
            tv_nodatafound.setVisibility(View.VISIBLE);
            rl_weeklycases.setVisibility(View.VISIBLE);
        } else {
            tv_nodatafound.setVisibility(View.GONE);
            rl_weeklycases.setVisibility(View.VISIBLE);
        }
        if (adapterdueVaccines == null) {
            adapterdueVaccines = new DueVaccinationAdapter(HomeActivity.this, arrayListduevaccinationname, new DueVaccinationAdapter.duevaccinationselectlistener() {
                @Override
                public void onclick(DueVaccinesList cd) {
                    if (checkchild_updatedstatus(cd.getChild_id())) {
                        Toasty.error(HomeActivity.this, R.string.dataalreadyupdated, Toasty.LENGTH_LONG).show();
                        return;
                    }
//                    if(!shouldaskPermission()) {
//                        Intent intent = new Intent(HomeActivity.this, Updatechild.class);
//                        intent.putExtra("fromsearch",false);
//                        intent.putExtra("childid", cd.getChild_id());
//                        intent.putExtra("child_contact", cd.getChild_contact());
//                        startActivity(intent);
//                    }
//                    else
//                    {
//                        verifypermission();
//                    }

                }

                @Override
                public void onsearchclicklister(DueVaccinesList cd) {
//                Toasty.success(HomeActivity.this, "Coming Soon...").show();
                    ShowSearchmobile(cd);
                }
            });
            rl_weeklycases.setAdapter(adapterdueVaccines);

            rl_weeklycases.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        }

    }

    private boolean checkchild_updatedstatus(String child_id) {
        DBInstance dbInstance = DBInstance.getInstance(HomeActivity.this);
        return dbInstance.check_ChildUpdatedStatus(child_id);
    }

    private void showdialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    private void dismissdialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    private void Logout_Session() {
        // LoginSession ls = new LoginSession(HomeActivity.this);
        ls.logout();
        finishAffinity();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


    }


    public void ShowSearchmobile(DueVaccinesList cd) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_customdialog);
        dialog.setTitle("मोबाइल नंबर या माँ के नाम का उपयोग करके माँ की खोज करें");

        // set the custom dialog components - text, image and button
        final EditText et_searchmobileno = (EditText) dialog.findViewById(R.id.et_searchmobileno);
//    text.setText("Android custom dialog example!");
//    ImageView image = (ImageView) dialog.findViewById(R.id.image);
        // image.setImageResource(R.drawable.ic_launcher);

        final Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
//    yourDialog.show();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }




    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void show_success_msg(String msg) {
        Toasty.success(HomeActivity.this, msg, Toasty.LENGTH_LONG).show();
    }

    public void show_error_msg(String msg) {
        Toasty.error(HomeActivity.this, msg, Toasty.LENGTH_LONG).show();
        ;
    }

    public ArrayList<DueVaccinesList> generateDuevaccinelist() {

        ArrayList<DueVaccinesList> arrayList = new ArrayList<>();
        DueVaccinesList vaccinations = new DueVaccinesList();
//        vaccinations.setMthrs_db_id("1823");
//        vaccinations.setMthrs_name("Mata3");
////        vaccinations.setMthrs_last_name();
//        vaccinations.setMthrs_unq_no("3246");
//        vaccinations.setMthrs_mbl_no("9810789821");
////        vaccinations.setMthrs_optn_mbl_no("mthrs_optn_mbl_no");
//
//
////        vaccinations.setMthrs_passwrd("mthrs_passwrd");
////        vaccinations.setAge("age");
//        vaccinations.setArea("UPHC Mahendra");
//        vaccinations.setArea_code("Taagore garden");
//        vaccinations.setAnm_name("Urmilla");
//
//
//        vaccinations.setAnm_contact("7023315804");
//        vaccinations.setAsha_name("Renu");
//        vaccinations.setAsha_contact("8433281735");
//        vaccinations.setMthr_status("1");
//        vaccinations.setChild_id("1957");
//        vaccinations.setMthr_id("1823");
//        vaccinations.setMother_name("Mata3");
//
//        vaccinations.setChild_contact("9810789821");
//        vaccinations.setChild_unq_id("UNIQUE45275");
//        vaccinations.setChild_name("Baccha123");
//        vaccinations.setChild_dob("2019-12-05");
//        vaccinations.setChild_status("yes");
//        vaccinations.setIs_vacinated_before("yes");
        ArrayList<String> due_vaccinations = new ArrayList<>();
//        due_vaccinations.add("BCG");
//        due_vaccinations.add("OPV_O");
//        due_vaccinations.add("Hep_B");
        vaccinations.setDue_vaccinations(due_vaccinations);
        arrayList.add(vaccinations);
        return arrayList;
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean shouldaskPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
//                LoginSession ls = new LoginSession(this);
//                int count = ls.getInt("permissioncount");
//                if (count >= 2) {
//                    return false;
//                }
                return true;
            }
            return false;
        }return false;
    }
    private void verifypermission() {

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_CALL_PHONE,
                    REQUEST_CALL_PHONE
            );

        }

    }
    private void requestPermission() {

        int permissioncall = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissioncall != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
        }
        int permissionsms = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        if (permissionsms != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    REQUEST_READ_SMS);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                   // requestPermission();
                    // main logic
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        int permissionsms = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

                        if (permissionsms != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.READ_SMS},
                                    REQUEST_READ_SMS);
                        }
                    }
                }

                else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            //showMessageOKCancel("You need to allow access permissions",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                //requestPermission();
//                                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                int permissionsms = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

                                if (permissionsms != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(this,
                                            new String[]{Manifest.permission.READ_SMS},
                                            REQUEST_READ_SMS);
                                }
                            }
//                                        }
//                                    }//);
                        }
                    }
                }
            case REQUEST_READ_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //requestPermission();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
//                            showMessageOKCancel("You need to allow access permissions",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                               // requestPermission();
                                            }
//                                        }
//                                    });
                        }
                    }
                }
        }
    }

}
