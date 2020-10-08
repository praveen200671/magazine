package com.anm.bslndmag;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anm.bslndmag.Adapter.VaccinationAdapter;
import com.anm.bslndmag.Model.Vaccinations;
import com.anm.bslndmag.Session.LoginSession;
import com.chaos.view.PinView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

//import com.supplier.app.R;
//import com.supplier.app.R;
//import com.technitoz.www.app.Adapter.ProductCategoryDataAdapter;
//import com.technitoz.www.app.Model.Category_child;
//import com.technitoz.www.app.Session.LoginSession;

public class DialogActivityVaccines extends Activity {
    private ArrayList<Vaccinations> vaccinations = new ArrayList<>();
    VaccinationAdapter vaccinationAdapter;
    private RecyclerView recyclerViewfiltercategory;
    Button btn_submit;
    Button btn_close;
    ;
     TextView btn_forgotpin;

    Button btnfilterproduct;
    LoginSession ls;
    String parentcategoryselecteditem = "";
    int resultmaincategorycode = 9877;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_maincategory);
//        RelativeLayout rl_dialogactivity = (RelativeLayout) findViewById(R.id.dialogactivity);
        btn_forgotpin = findViewById(R.id.btn_forgotpin);
        btn_forgotpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ls.logout();
                ls.setSecurityPin("");
                Intent intent = new Intent(DialogActivityVaccines.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finishAffinity();

            }
        });
        final PinView otpTextView = findViewById(R.id.otp_view);
        final PinView confirmotp_view = findViewById(R.id.confirmotp_view);
        final TextView tv_confirmpin = findViewById(R.id.tv_confirmpin);
        btn_close = (Button) findViewById(R.id.btn_close);
//        rl_dialogactivity.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                finish();
//                return false;
//            }
//        });
        Intent intent = getIntent();
        boolean isvialogin = (boolean) intent.getBooleanExtra(this.getString(R.string.isloggedin), false);
        if (isvialogin) {
            tv_confirmpin.setVisibility(View.GONE);
            confirmotp_view.setVisibility(View.GONE);
            btn_forgotpin.setVisibility(View.VISIBLE);
            btn_close.setVisibility(View.GONE);


        }
        else
        {
            btn_close.setVisibility(View.VISIBLE);
            btn_forgotpin.setVisibility(View.GONE);
        }
        ls = new LoginSession(this);

        btn_close.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                            finish();
                                         }
                                     });
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = otpTextView.getText().toString();
                String confirmpassword = confirmotp_view.getText().toString();
                if (password.matches("")) {
                    Toasty.error(DialogActivityVaccines.this, R.string.enterpasscode,Toasty.LENGTH_LONG).show();;
                    return;
                } else if (password.length() != 4) {
                    Toasty.error(DialogActivityVaccines.this, R.string.entercorrectpasscode,Toasty.LENGTH_LONG).show();;
                    return;
                } else if (!(confirmpassword.equalsIgnoreCase(password)) && confirmotp_view.getVisibility() == View.VISIBLE) {
                    Toasty.error(DialogActivityVaccines.this, R.string.entercorrectpasscode,Toasty.LENGTH_LONG).show();;
                    return;
                }
                if (confirmotp_view.getVisibility() != View.VISIBLE) {
                    if (password.equalsIgnoreCase(ls.getSecurityPin())) {
                        finish();
                    } else {
                        Toasty.error(DialogActivityVaccines.this, R.string.entercorrectpasscode,Toasty.LENGTH_LONG).show();;
                        return;
                    }
                } else {
                    ls.setSecurityPin(password);
                    finish();
                }

            }
        });


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();

            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                // finish();
            }
            if (v instanceof RecyclerView) {
                //  finish();
            } else {

            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK)  //Override Keyback to do nothing in this case.
        {
            if( btn_forgotpin.getVisibility()==View.GONE)
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);  //-->All others key will work as usual
    }

    @Override
    public void onBackPressed() {
        if( btn_forgotpin.getVisibility()==View.GONE)
        super.onBackPressed();

    }
}