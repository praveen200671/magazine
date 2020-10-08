package com.anm.bslndmag;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.Adapter.VaccinationAdapter;
import com.anm.bslndmag.Model.AddMotherRequest;
import com.anm.bslndmag.Model.AddmotherResponse;
import com.anm.bslndmag.Model.Offlinemotherchilddata;
import com.anm.bslndmag.Model.Vaccinations;
import com.anm.bslndmag.Session.CartSession;
import com.anm.bslndmag.Session.LoginSession;
import com.anm.bslndmag.Utils.Utilsclass;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Settings extends AppCompatActivity {
    EditText et_mothermobile, et_mothername, et_motherage, et_childname;
    TextView tv_childdob, tv_injectioneddate;
    RecyclerView rv_injectionednames;
    Spinner sp_ashaname, sp_placename;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button btn_submit;
    LoginSession ls;
    ArrayList<String> injectedinjectionslist = new ArrayList<>();
    ArrayList<String> arrayListlocationsname;
    ArrayList<String> arrayListashasname;
    ArrayList<Vaccinations> arrayListvaccinationname;
    ArrayAdapter<String> adapterasha;
    ArrayAdapter<String> adapterlocation;
    ArrayList<Vaccinations> selectedvaccinearraylist;
    VaccinationAdapter adapterVaccines;
    DBInstance dbInstance;
    private static final int REQUEST_CALL_PHONE= 1;
    private static final int REQUEST_READ_SMS= 2;
    TextView tv_login,tv_logout,tv_addFnF,tv_termsandconditions,tv_privacypolicy,tv_changelanguage,tv_versionno,tv_about,tv_subscriptionplans,tv_rateus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       try {
           setContentView(R.layout.activity_add_mother);

           ls = new LoginSession(this);

           tv_login=findViewById(R.id.tv_login);
           tv_logout=findViewById(R.id.tv_logout);
           tv_addFnF=findViewById(R.id.tv_addfnf);
           tv_changelanguage=findViewById(R.id.tv_changelanguage);
           tv_termsandconditions=findViewById(R.id.tv_termsandconditions);
           tv_privacypolicy=findViewById(R.id.tv_privacypolicy);
           tv_versionno=findViewById(R.id.tv_versionno);
           tv_about=findViewById(R.id.tv_about);
           tv_subscriptionplans=findViewById(R.id.tv_subscriptionplans);
           tv_rateus=findViewById(R.id.tv_rateus);
           tv_changelanguage.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   ls.setStr(getString(R.string.language),"");
                   Intent intent=new Intent(Settings.this,SplashScreenActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                   finish();
               }
           });

           tv_logout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   ls.logout();
                   Toasty.success(Settings.this,"Logged Out successfully").show();
                   ls.set_isneedRefresh("y");
                   if(ls.get_auth_token().length()>0)
                   {
                       tv_login.setVisibility(View.GONE);
                       tv_logout.setVisibility(View.VISIBLE);
                   }
                   else
                   {
                       tv_login.setVisibility(View.VISIBLE);
                       tv_logout.setVisibility(View.GONE);
                   }
               }
           });
           tv_termsandconditions.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=   new Intent(Settings.this, Privacypolicy_Tnc.class);
                   intent.putExtra("istermsandconditions","true");
                   startActivity(intent);
               }
           });

           tv_privacypolicy.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=   new Intent(Settings.this, Privacypolicy_Tnc.class);
                   intent.putExtra("istermsandconditions","false");
                   startActivity(intent);
               }
           });

           tv_about.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  Toasty.success(Settings.this,"Coming soon").show();
               }
           });

           tv_subscriptionplans.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   openPlans();

               }
           });
           tv_rateus.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

               }
           });
           tv_login.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                Intent intent=new Intent(Settings.this,LoginActivity.class);
                intent.putExtra("purpose","read");
                startActivity(intent);
               }
           });



       }
       catch (Exception e) {
       }
       }

    private void openPlans() {
    Intent intent=new Intent(this,SubscriptionPlans.class);
    startActivity(intent);

    }




    private void UpdateAddmotherOnlineSubmitStatus(String uniqueid) {

      //  dbInstance.ChangeAddmotherOnlineSubmitStatus( uniqueid);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(ls.get_auth_token().length()>0)
        {
            tv_login.setVisibility(View.GONE);
            tv_logout.setVisibility(View.VISIBLE);
        }
        else
        {
            tv_login.setVisibility(View.VISIBLE);
            tv_logout.setVisibility(View.GONE);
        }

    }

    public  Drawable setVectorForPreLollipop(int resourceId) {
        Drawable icon;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            icon = VectorDrawableCompat.create(this.getResources(), resourceId, this.getTheme());
        } else {
            icon = this.getResources().getDrawable(resourceId, this.getTheme());
        }

        return icon;
    }

    private void setvaccinationDateField() {
        tv_injectioneddate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openDatePicker(tv_injectioneddate);
                }
            }
        });
        tv_injectioneddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(tv_injectioneddate);
            }
        });
    }

    private void setChildDobField() {
        tv_childdob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    openDatePicker(tv_childdob);
                }
            }
        });
        tv_childdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = "";
                openDatePicker(tv_childdob);
            }
        });
    }

    private void setAdapterDatas() {
        arrayListlocationsname = dbInstance.getLOCATIONSNamelist();

        arrayListvaccinationname = dbInstance.getVaccinationsNameList();
        if( arrayListlocationsname==null)
        {
            arrayListlocationsname=new ArrayList<>();
        }
        if( arrayListvaccinationname==null)
        {
            arrayListvaccinationname=new ArrayList<>();
        }
        adapterlocation = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, arrayListlocationsname);
        adapterlocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_placename.setAdapter(adapterlocation);


        arrayListashasname = dbInstance.getAshasNamelist();
        adapterasha = new ArrayAdapter<String>(
                Settings.this, android.R.layout.simple_spinner_item, arrayListashasname);

        adapterasha.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_ashaname.setAdapter(adapterasha);

        adapterVaccines = new VaccinationAdapter(this, arrayListvaccinationname, selectedvaccinearraylist, new VaccinationAdapter.vaccinationselectlistener() {

            @Override
            public void chkselectcategory(Vaccinations cd, boolean chb_vaccination) {
//                checkVaccineinselectedArray(cd);
              try {
                  if (!chb_vaccination) {
                      add_or_removeunselected(selectedvaccinearraylist,cd,"D");
                  } else {
                      add_or_removeunselected(selectedvaccinearraylist,cd,"A");
                  }
              }catch (Exception ex)
              {
                 // Toasty.error(Settings.this, ex.getMessage()).show();
              }
            }
        });
        rv_injectionednames.setAdapter(adapterVaccines);
        rv_injectionednames.setLayoutManager(
                new LinearLayoutManager(Settings.this));

    }

    private void add_or_removeunselected(ArrayList<Vaccinations> selectedvaccinearraylist, Vaccinations cd, String d) {

        boolean objectfound=false;
        int i=0;
        for(Vaccinations vaccinations:selectedvaccinearraylist) {
            if (vaccinations.getVcc_name() != null) {
                if (cd.getVcc_name() != null) {
                    if (vaccinations.getVcc_name().equalsIgnoreCase(cd.getVcc_name())) {
                        objectfound = true;
                        break;
                    }
                }
            }
            i++;
        }
            if(objectfound) {
                if (d.equalsIgnoreCase("D")) {
                    selectedvaccinearraylist.remove(i);
                }
            }
            else {
                selectedvaccinearraylist.add(cd);
            }
            this.selectedvaccinearraylist=selectedvaccinearraylist;
    }

    private void checkVaccineinselectedArray(Vaccinations cd) {
        boolean isfoundinselectedarray = false;
        Vaccinations remove;
        int index = -1;
        for (Vaccinations vaccinations : selectedvaccinearraylist) {
            if (cd.getVcc_name().equalsIgnoreCase(vaccinations.getVcc_name())) {
                remove = vaccinations;
                index++;
                break;
            }
        }

        selectedvaccinearraylist.remove(index);

    }

//    public static boolean CheckDates(String startDate, String endDate) {
//
//        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
//
//        boolean b = false;
//
//        try {
//            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
//                b = true;  // If start date is before end date.
//            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
//                b = true;  // If two dates are equal.
//            } else {
//                b = false; // If start date is after the end date.
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return b;
//    }
    public void openDatePicker(final TextView editText) {
        // Get Current Date
        String date = "";
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        if (!date.matches("")) {
            String input_date = date;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date d = sdf.parse(input_date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                mMonth = Integer.valueOf(checkDigit(cal.get(Calendar.MONTH) + 1));
                mDay = Integer.valueOf(checkDigit(cal.get(Calendar.DATE)));
                mYear = Integer.valueOf(checkDigit(cal.get(Calendar.YEAR)));

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        try {
            //launch datepicker modal
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            DecimalFormat mFormat = new DecimalFormat("00");
                            mFormat.setRoundingMode(RoundingMode.DOWN);

                            //TextView txtonwarddate=(TextView)dynamicContent.findViewById(R.id.txtdialogdeliverydate);
                            editText.setText(year + "-" + mFormat.format(Double.valueOf((monthOfYear + 1))) + "-" + mFormat.format(Double.valueOf(dayOfMonth)));
                            //PUT YOUR LOGING HERE
                            //UNCOMMENT THIS LINE TO CALL TIMEPICKER
                            //openTimePicker();
                        }
                    }, mYear, (mMonth), mDay);
            long now = Calendar.getInstance().getTimeInMillis() - 1000;
            Calendar maxCal = Calendar.getInstance();

//        datePickerDialog.getDatePicker().setMinDate(now);
            datePickerDialog.getDatePicker().setMaxDate(now);
//        datePickerDialog.getDatePicker().updateDate();

            datePickerDialog.show();
        }catch (Exception e)
        {
           // Toasty.error(this,"777777"+e.getMessage());
        }
    }

    private void requestPermission() {

        int permissioncall = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissioncall != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
        }

    }
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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                               // requestPermission();
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
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPreferredLanguage();
        //onPostResume();
    }

    private void setPreferredLanguage() {
        if(ls.getStr(getString(R.string.language)).equals(getString(R.string.language_english)))
        {
            tv_login.setText(getString(R.string.login_english));
            tv_logout.setText(getString(R.string.logout_english));
            tv_addFnF.setText(getString(R.string.addfnf_english));
            tv_changelanguage.setText(getString(R.string.changelanguage_english));
            tv_termsandconditions.setText(getString(R.string.termsandconditions_english));
            tv_privacypolicy.setText(getString(R.string.privacypolicy_english));
            tv_versionno.setText(getString(R.string.version_english));
            tv_about.setText(getString(R.string.aboutus_english));
            tv_subscriptionplans.setText(getString(R.string.subscriptionplans_english));
            tv_rateus.setText(getString(R.string.rateus_english));
        }
        else  if(ls.getStr(getString(R.string.language)).equals(getString(R.string.language_hindi)))
        {
            tv_login.setText(getString(R.string.login_hindi));
            tv_logout.setText(getString(R.string.logout_hindi));
            tv_addFnF.setText(getString(R.string.addfnf_hindi));
            tv_changelanguage.setText(getString(R.string.changelanguage_hindi));
            tv_termsandconditions.setText(getString(R.string.termsandconditions_hindi));
            tv_versionno.setText(getString(R.string.versionno_hindi));
            tv_privacypolicy.setText(getString(R.string.privacypolicy_hindi));
            tv_about.setText(getString(R.string.aboutus_hindi));
            tv_subscriptionplans.setText(getString(R.string.subscriptionplans_hindi));
            tv_rateus.setText(getString(R.string.rateus_hindi));
        }
        else  if(ls.getStr(getString(R.string.language)).equals(getString(R.string.language_gurmukhi)))
        {
            tv_login.setText(getString(R.string.login_gurmukhi));
            tv_logout.setText(getString(R.string.logout_gurmukhi));
            tv_addFnF.setText(getString(R.string.addfnf_gurmukhi));
            tv_changelanguage.setText(getString(R.string.changelanguage_gurmukhi));
            tv_termsandconditions.setText(getString(R.string.termsandconditions_gurmukhi));
            tv_versionno.setText(getString(R.string.versionno_gurmukhi));
            tv_privacypolicy.setText(getString(R.string.privacypolicy_gurmukhi));
            tv_about.setText(getString(R.string.aboutus_gurmukhi));
            tv_subscriptionplans.setText(getString(R.string.subscriptionplans_gurmukhi));
            tv_rateus.setText(getString(R.string.rateus_gurmukhi));
        }
    }

}
