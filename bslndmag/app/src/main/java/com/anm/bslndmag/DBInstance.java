package com.anm.bslndmag;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.anm.bslndmag.Model.AddMotherRequest;
import com.anm.bslndmag.Model.Asha_details;
import com.anm.bslndmag.Model.DueVaccinesList;
import com.anm.bslndmag.Model.Locations;
import com.anm.bslndmag.Model.Offlinemotherchilddata;
import com.anm.bslndmag.Model.SearchedChildDetails;
import com.anm.bslndmag.Model.SearchedMotherItem;
import com.anm.bslndmag.Model.UpdateChildVaccineRequest;
import com.anm.bslndmag.Model.Vacccine;
import com.anm.bslndmag.Model.Vaccinations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class DBInstance extends SQLiteOpenHelper {
     static  DBInstance ourInstance;
    //      String DATABASENAME="ANM";
    public final String LOCATIONS="locations";
    private final String ASHAS_DETAILS="ashas_details";
    private final String VACCINATIONS="vaccinations";
    private final String DUEVACCINEChildren="duevaccinechildren";
    private final String DUEVACCINE="duevaccine";
    private final String ADDMOTHERTABLE="addmothertable";
    private final String UPDATECHILDTABLE="updatechildtable";
    private final String DUECHILDDETAILSLISTTABLE="childdetailslisttable";
    private final String OFFLINEMOTHERCHILDDATA="offlinemotherchilddata";
    private SQLiteDatabase db;
     static Context context;
  //  private String drop_table_location,drop_table_ashadetails,drop_table_vaccination,drop_table_DUEVACCINEChildren,drop_table_DUEVACCINE;
    public static DBInstance getInstance(Context context) {
        //        context=context;
        ourInstance = new DBInstance(context);
        return ourInstance;
    }
    private DBInstance(Context context) {
        super(context,"ANM",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table_location="create table "+LOCATIONS+" (territory_id text, territory_list text,parent_territory_name text,status text)";
        String create_table_ashadetails="create table "+ASHAS_DETAILS+"  (id text, asha_name text,territory_id text,mobile_number text, status text)";
        String create_table_vaccination="create table "+VACCINATIONS+"   (vcc_id text,vcc_code text,name text,age_group text,status text)";


        String create_table_duevaccinechildren="create table "+DUEVACCINEChildren+" (child_id text,mthr_id text,mother_name text," +
                "child_contact text,child_name text,child_dob text," +
               "child_status text,is_vacinated_before text,added_time text,last_updation_time text," +
                "mthrs_name text,mthrs_mbl_no text)";
        String create_table_duechilddetailslist="create table "+DUECHILDDETAILSLISTTABLE+" (child_id text,mthr_id text,mother_name text," +
                "child_contact text,child_unq_id text,child_name text,child_dob text," +
               "child_status text,is_vacinated_before text,added_time text,last_updation_time text," +
                "done_vaccines text,today_due_vaccines text,future_due_vaccines text)";

        String create_table_duevaccine="create table "+DUEVACCINE+"   (child_id text,vaccine_name text,isdone text)";

        String create_table_updatechild="create table "+UPDATECHILDTABLE+
                "   (uniqueid text,child_id text,vaccine_date text,selected text,vaccination_name text,captured_date text,onlinesubmitstatus text)";
        String create_table_Addmother="create table "+ADDMOTHERTABLE+"   (uniqueid text,mobile text,mother_name text,mother_age text" +
                ",area text,area_name text,anm_name text,anm_contact text" +
                ",asha_name text,asha_contact text,child_name text,child_dob text,vaccinations text,vaccinationscodes text,vaccine_date text,captured_date text,onlinesubmitstatus text)";
        String create_table_OFFLINEMOTHERCHILDDATA="create table "+OFFLINEMOTHERCHILDDATA+"   (child_id text,child_name text,child_contact text" +
                ",mother_name text,child_dob text,child_status text,child_unq_id text" +
                ",mthrs_db_id text,mthrs_mbl_no text,done_vaccines text,today_due_vaccines text,future_due_vaccines text)";


//         drop_table_location="drop table "+LOCATIONS;//+" (territory_id text, territory_list text,parent_territory_name text,status text)";
//         drop_table_ashadetails="drop table "+ASHAS_DETAILS;//+"  (id text, asha_name text,territory_id text,mobile_number text, status text)";
//         drop_table_vaccination="drop table "+VACCINATIONS;//+"   (name text,vcc_code text,status text)";
//         drop_table_DUEVACCINEChildren="drop table "+DUEVACCINEChildren;//+"   (name text,vcc_code text,status text)";
//         drop_table_DUEVACCINE="drop table "+DUEVACCINE;//+"   (name text,vcc_code text,status text)";

//        db.execSQL(drop_table_location);
//        db.execSQL(drop_table_ashadetails);
//        db.execSQL(drop_table_vaccination);
        db.execSQL(create_table_location);
        db.execSQL(create_table_ashadetails);
        db.execSQL(create_table_vaccination);
        db.execSQL(create_table_duevaccinechildren);
        db.execSQL(create_table_duechilddetailslist);
        db.execSQL(create_table_duevaccine);
        db.execSQL(create_table_Addmother);
        db.execSQL(create_table_updatechild);
        db.execSQL(create_table_OFFLINEMOTHERCHILDDATA);

    }
    public void insert_Vaccinations(ArrayList<Vacccine> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("delete from "+VACCINATIONS);
            ContentValues values = new ContentValues();
//            for (Vaccinations vaccinations : list) {
            for (Vacccine vacccine : list) {
                values.put("vcc_id", vacccine.getId());
                values.put("vcc_code", vacccine.getVaccine_code());
                values.put("name", vacccine.getVaccination_name());
                values.put("age_group", vacccine.getAge_group());
                values.put("status", vacccine.getStatus());
//                vaccinations.put("vcc_code", asha_details.getAsha_name());
                db.insert(VACCINATIONS, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    public void insertAllAshaData(ArrayList<ArrayList<Asha_details>> arrayListArrayListasha)
    {
        for(ArrayList<Asha_details> arrayList:arrayListArrayListasha)
        {
            insert_Ashas(arrayList);
        }
    }
    public void insert_Ashas(ArrayList<Asha_details> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Asha_details asha_details : list) {
                values.put("id", asha_details.getId());
                values.put("asha_name", asha_details.getAsha_name());
                values.put("mobile_number", asha_details.getMobile_number());
                values.put("status", asha_details.getStatus());
                db.insert(ASHAS_DETAILS, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    public void insert_DueVaccineMotherChildList(ArrayList<DueVaccinesList> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.isOpen())
        {
           // db.execSQL(drop_table_DUEVACCINEChildren);
        }
        db.beginTransaction();
        try {
            Log.e("onresume--","insert due vaccine data "+list.size());
            ContentValues values = new ContentValues();
            for (DueVaccinesList duevaccine : list) {



                values.put("child_id", duevaccine.getChild_id());
                values.put("mthr_id", duevaccine.getMthr_id());
                values.put("mother_name", duevaccine.getMother_name());
                values.put("child_contact", duevaccine.getChild_contact());
                values.put("child_name", duevaccine.getChild_name());

                values.put("child_dob", duevaccine.getChild_dob());
                values.put("child_status", duevaccine.getChild_status());
                values.put("is_vacinated_before", duevaccine.getIs_vacinated_before());
                values.put("added_time", duevaccine.getAdded_time());
                values.put("last_updation_time", duevaccine.getLast_updation_time());
                values.put("mthrs_name", duevaccine.getMthrs_name());
                values.put("mthrs_mbl_no", duevaccine.getMthrs_mbl_no());

                db.insert(DUEVACCINEChildren, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        Log.e("onresume--","inserted due vaccine data "+list.size());
        insert_DueVaccine(list);

    }
    public void insert_SearchedChildrenDetails(SearchedChildDetails data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
               values.put("child_id", data.getChild_id());
                values.put("mthr_id", data.getMthr_id());
                values.put("mother_name", data.getMother_name());
                values.put("child_contact", data.getChild_contact());
                values.put("child_unq_id", data.getChild_unq_id());
                values.put("child_name", data.getChild_name());
                values.put("child_dob", data.getChild_dob());
                values.put("child_status", data.getChild_status());
                values.put("is_vacinated_before", data.getIs_vacinated_before());
                values.put("added_time", data.getAdded_time());
                values.put("last_updation_time", data.getLast_updation_time());
                db.insert(DUEVACCINEChildren, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        insert_SearchedChildDoneVacccine(data.getFuture_due_vaccines(),data.getChild_id(),"DONE");
        insert_SearchedChildDoneVacccine(data.getFuture_due_vaccines(),data.getChild_id(),"FUTUREDUE");
    }

    public void insert_OfflineMotherChilddata(ArrayList<Offlinemotherchilddata> data) {
        if(data!=null) {
            SQLiteDatabase db = this.getWritableDatabase();

            db.beginTransaction();
            try {
                for(Offlinemotherchilddata offlinemotherchilddata:data) {
                    ContentValues values = new ContentValues();

                    values.put("child_id", offlinemotherchilddata.getChild_id());
                    values.put("child_name", offlinemotherchilddata.getChild_name());
                    values.put("child_contact", offlinemotherchilddata.getChild_contact());
                    values.put("mother_name", offlinemotherchilddata.getMother_name());
                    values.put("child_dob", offlinemotherchilddata.getChild_dob());
                    values.put("child_status", offlinemotherchilddata.getChild_status());
                    values.put("child_unq_id", offlinemotherchilddata.getChild_unq_id());
                    values.put("mthrs_db_id", offlinemotherchilddata.getMthrs_db_id());
                    values.put("mthrs_mbl_no", offlinemotherchilddata.getMthrs_mbl_no());
                    values.put("done_vaccines", offlinemotherchilddata.getDone_vaccines());
                    values.put("today_due_vaccines", offlinemotherchilddata.getToday_due_vaccines());
                    values.put("future_due_vaccines", offlinemotherchilddata.getFuture_due_vaccines());
                    db.insert(OFFLINEMOTHERCHILDDATA, null, values);

                }
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }
    public void insert_SearchedChildDoneVacccine(ArrayList<String> list,String childid,String vaccinestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (String duevaccinerecord : list) {

                    values.put("child_id", childid);
                    values.put("vaccine_name", duevaccinerecord);
                    values.put("isdone", vaccinestatus);
                    db.insert(DUEVACCINE, null, values);

            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    public void insert_SearchedChildFutureVacccine(ArrayList<String> list,String childid,String vaccinestatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (String duevaccinerecord : list) {
                values.put("child_id", childid);
                values.put("vaccine_name", duevaccinerecord);
                values.put("isdone", vaccinestatus);
                db.insert(DUEVACCINE, null, values);

            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    public void insert_DueVaccine(ArrayList<DueVaccinesList> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (DueVaccinesList duevaccinerecord : list) {
                for (String duevaccine : duevaccinerecord.getDue_vaccinations()) {
                    values.put("child_id", duevaccinerecord.getChild_id());
                    values.put("vaccine_name", duevaccine);
                    values.put("isdone", "N");
                    db.insert(DUEVACCINE, null, values);
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    public void insert_Location(ArrayList<Locations> list) {
        ArrayList<ArrayList<Asha_details>> arrayListArrayListasha =new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Locations locations : list) {
                values.put("territory_id", locations.getTerritory_id());
                values.put("territory_list", locations.getTerritory_list());
                values.put("parent_territory_name", locations.getParent_territory_name());
                ArrayList<ArrayList<Asha_details>> arrayListArrayList=locations.getAsha_details();
                arrayListArrayListasha.add(arrayListArrayList.get(0));
                db.insert(LOCATIONS, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        insertAllAshaData(arrayListArrayListasha);
    }
    public ArrayList<String> getAshasNamelist() {
//        ArrayList<Asha_details> ashaDetailsArrayList =new ArrayList<>();
        ArrayList<String> ashaDetailsArrayList =new ArrayList<>();
        ashaDetailsArrayList.add("--Select--");
//        Asha_details asha_details;
        String query = "Select  * FROM " + ASHAS_DETAILS+ " where status ='1' ";//and territory_id='"+place+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            // String asha_id= cursor.getString(0);
            String asha_name = cursor.getString(1);
            // String asha_territory_id = cursor.getString(2);
            //  String asha_mobile = cursor.getString(3);
//            String asha_status = cursor.getString(4);

//            asha_details=new Asha_details();
//            asha_details.setId(asha_id);
//            asha_details.setAsha_name(asha_name);
//            asha_details.setTerritory_id(asha_territory_id);
//            asha_details.setMobile_number(asha_mobile);
            //vaccinations.setAsha_name(asha_name);
            ashaDetailsArrayList.add(asha_name);
        }
        cursor.close();
        db.close();
        return ashaDetailsArrayList;
    }
    public ArrayList<Vaccinations> getVaccinationsNameList() {
        ArrayList<Vaccinations> vaccinationsArrayList =new ArrayList<>();
//        vaccinationsArrayList.add("--Select--");
        Vaccinations vaccinations;
//        vaccinations.setVcc_name("--Select--");
//        vaccinationsArrayList.add(vaccinations);
        String query = "Select  * FROM " + VACCINATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {

            vaccinations=new Vaccinations();
          vaccinations.setVcc_name(cursor.getString(cursor.getColumnIndex("name")));
          vaccinations.setVcc_code(cursor.getString(cursor.getColumnIndex("vcc_code")));
           // String vcc_code = cursor.getString(1);
//            String parent_territory_name = cursor.getString(2);
//            vaccinations=new Vaccinations();
//            vaccinations.setVcc_name(vcc_name);
//            vaccinations.setVcc_code(vcc_code);
            vaccinationsArrayList.add(vaccinations);
        }
        cursor.close();
        db.close();
        return vaccinationsArrayList;
    }
    public ArrayList<String> getLOCATIONSNamelist() {
        ArrayList<String> locationslist =new ArrayList<>();
        locationslist.add("--Select--");
        Locations locations;
        String query = "Select  * FROM " + LOCATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
           // int territory_id = cursor.getInt(0);
            String territory_list = cursor.getString(1);
//            String parent_territory_name = cursor.getString(2);
//            locations=new Locations();
            locationslist.add(territory_list);
        }
        cursor.close();
        db.close();
        return locationslist;
    }
    //====================================================================================================================================================================
    public Asha_details getAshaDetails(String ashaname) {
        Asha_details asha_details =new Asha_details();
//        Asha_details asha_details;
        String query = "Select  * FROM " + ASHAS_DETAILS+ " where status =1 and asha_name ='"+ashaname+"' ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String asha_id= cursor.getString(0);
            String asha_name = cursor.getString(1);
            String asha_territory_id = cursor.getString(2);
            String asha_mobile = cursor.getString(3);
//            String asha_status = cursor.getString(4);

            asha_details=new Asha_details();
            asha_details.setId(asha_id);
            asha_details.setAsha_name(asha_name);
            asha_details.setTerritory_id(asha_territory_id);
            asha_details.setMobile_number(asha_mobile);
            //vaccinations.setAsha_name(asha_name);
           // ashaDetailsArrayList.add(asha_details);
        }
        cursor.close();
        db.close();
        return asha_details;
    }
    public Locations getLOCATION(String location) {
//        ArrayList<String> locationslist =new ArrayList<>();
//        locationslist.add("--Select--");
        Locations locations =new Locations();
        String query = "Select  * FROM " + LOCATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            // int territory_id = cursor.getInt(0);
            locations.setTerritory_list(cursor.getString(1));
         locations.setParent_territory_name( cursor.getString(2));
//            locations=new Locations();
//            locationslist.add(territory_list);
        }
        cursor.close();
        db.close();
        return locations;
    }
    //========================================================
    public ArrayList<Asha_details> getAshasdetailslist() {
        ArrayList<Asha_details> ashaDetailsArrayList =new ArrayList<>();
        Asha_details asha_details;
        String query = "Select  * FROM " + ASHAS_DETAILS+ " where status =1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String asha_id= cursor.getString(0);
            String asha_name = cursor.getString(1);
            String asha_territory_id = cursor.getString(2);
            String asha_mobile = cursor.getString(3);
//            String asha_status = cursor.getString(4);

            asha_details=new Asha_details();
            asha_details.setId(asha_id);
            asha_details.setAsha_name(asha_name);
            asha_details.setTerritory_id(asha_territory_id);
            asha_details.setMobile_number(asha_mobile);
            //vaccinations.setAsha_name(asha_name);
            ashaDetailsArrayList.add(asha_details);
        }
        cursor.close();
        db.close();
        return ashaDetailsArrayList;
    }
    public ArrayList<Vaccinations> getVaccinations() {
        ArrayList<Vaccinations> vaccinationsArrayList =new ArrayList<>();
        Vaccinations vaccinations;
        String query = "Select  * FROM " + VACCINATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String vcc_name = cursor.getString(0);
            String vcc_code = cursor.getString(1);
//            String parent_territory_name = cursor.getString(2);
            vaccinations=new Vaccinations();
            vaccinations.setVcc_name(vcc_name);
            vaccinations.setVcc_code(vcc_code);
            vaccinationsArrayList.add(vaccinations);
        }
        cursor.close();
        db.close();
        return vaccinationsArrayList;
    }
    public Hashtable<String,String> getVaccinationshashtable() {
        Hashtable<String,String> vaccinationsArrayList =new Hashtable<>();
       // Vaccinations vaccinations;
        String query = "Select  * FROM " + VACCINATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {

            String vcc_name = cursor.getString(2);
            String vcc_code = cursor.getString(1);

            vaccinationsArrayList.put(vcc_name,vcc_code);

        }
        cursor.close();
        db.close();
        return vaccinationsArrayList;
    }

    public ArrayList<DueVaccinesList> getDueVaccinations() {
        ArrayList<DueVaccinesList> vaccinationsArrayList =new ArrayList<>();

        DueVaccinesList vaccinations;
        String query = "Select  * FROM " + DUEVACCINEChildren;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
//            String vcc_name = cursor.getString(0);
//            String vcc_code = cursor.getString(1);
//            String parent_territory_name = cursor.getString(2);
            vaccinations=new DueVaccinesList();
            vaccinations.setChild_id(cursor.getString(cursor.getColumnIndex("child_id")));
            vaccinations.setMthr_id(cursor.getString(cursor.getColumnIndex("mthr_id")));
            vaccinations.setMother_name(cursor.getString(cursor.getColumnIndex("mother_name")));
            vaccinations.setChild_contact(cursor.getString(cursor.getColumnIndex("child_contact")));
            vaccinations.setChild_name(cursor.getString(cursor.getColumnIndex("child_name")));
            vaccinations.setChild_dob(cursor.getString(cursor.getColumnIndex("child_dob")));
            vaccinations.setChild_status(cursor.getString(cursor.getColumnIndex("child_status")));
            vaccinations.setIs_vacinated_before(cursor.getString(cursor.getColumnIndex("is_vacinated_before")));
            vaccinations.setAdded_time(cursor.getString(cursor.getColumnIndex("added_time")));
            vaccinations.setLast_updation_time(cursor.getString(cursor.getColumnIndex("last_updation_time")));
            vaccinations.setMthrs_name(cursor.getString(cursor.getColumnIndex("mthrs_name")));
            vaccinations.setMthrs_mbl_no(cursor.getString(cursor.getColumnIndex("mthrs_mbl_no")));
            vaccinationsArrayList.add(vaccinations);
        }
        cursor.close();
        db.close();
        Log.e("onresume--"," getmethoed...fetched data-- "+vaccinationsArrayList.size());
//        vaccinations=new DueVaccinesList();
//        vaccinations.setMother_name("---addmother---");
//        vaccinationsArrayList.add(vaccinations);
        return vaccinationsArrayList;
    }

    public ArrayList<SearchedMotherItem> getOfflineMotherChildData(String mobileno, String mothername,String childname) {
        ArrayList<SearchedMotherItem> vaccinationsArrayList =new ArrayList<>();
        SearchedMotherItem vaccinations;
        String[] arg=new String[2];
        arg[0]=mobileno;
        arg[1]=mobileno;
        String query = "Select  * FROM " + OFFLINEMOTHERCHILDDATA +" where mthrs_mbl_no like '"+mobileno+"' and mother_name like '"+mothername+"'  and child_name like '"+childname+"';" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            vaccinations=new SearchedMotherItem();
            vaccinations.setChild_id(cursor.getString(cursor.getColumnIndex("child_id")));
            vaccinations.setChild_name(cursor.getString(cursor.getColumnIndex("child_name")));
            vaccinations.setChild_mobile(cursor.getString(cursor.getColumnIndex("child_contact")));
            vaccinations.setMthrs_name(cursor.getString(cursor.getColumnIndex("mother_name")));
//            vaccinations.set(cursor.getString(cursor.getColumnIndex("child_dob")));
//            vaccinations.setChild_status(cursor.getString(cursor.getColumnIndex("child_status")));
//            vaccinations.setchil(cursor.getString(cursor.getColumnIndex("child_unq_id")));
            vaccinations.setMthrs_db_id(cursor.getString(cursor.getColumnIndex("mthrs_db_id")));
            vaccinations.setMthrs_mbl_no(cursor.getString(cursor.getColumnIndex("mthrs_mbl_no")));
//            vaccinations.setDone_vaccines(cursor.getString(cursor.getColumnIndex("done_vaccines")));
//            vaccinations.setToday_due_vaccines(cursor.getString(cursor.getColumnIndex("today_due_vaccines")));
//            vaccinations.setFuture_due_vaccines(cursor.getString(cursor.getColumnIndex("future_due_vaccines")));
            vaccinationsArrayList.add(vaccinations);
        }
        cursor.close();
        db.close();
        Log.e("onresume--"," getmethoed...fetched data-- "+vaccinationsArrayList.size());
        return vaccinationsArrayList;
    }

    public ArrayList<SearchedMotherItem> getOfflineMotherChildDatausingmobile(String mobileno) {
        ArrayList<SearchedMotherItem> vaccinationsArrayList =new ArrayList<>();
        SearchedMotherItem vaccinations;
        String[] arg=new String[2];
        arg[0]=mobileno;
        arg[1]=mobileno;
        String query = "Select  * FROM " + OFFLINEMOTHERCHILDDATA +" where child_contact like '%"+mobileno+"%' or mother_name like '%"+mobileno+"%';" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            vaccinations=new SearchedMotherItem();
            vaccinations.setChild_id(cursor.getString(cursor.getColumnIndex("child_id")));
            vaccinations.setChild_name(cursor.getString(cursor.getColumnIndex("child_name")));
            vaccinations.setChild_mobile(cursor.getString(cursor.getColumnIndex("child_contact")));
            vaccinations.setMthrs_name(cursor.getString(cursor.getColumnIndex("mother_name")));
//            vaccinations.set(cursor.getString(cursor.getColumnIndex("child_dob")));
//            vaccinations.setChild_status(cursor.getString(cursor.getColumnIndex("child_status")));
//            vaccinations.setchil(cursor.getString(cursor.getColumnIndex("child_unq_id")));
            vaccinations.setMthrs_db_id(cursor.getString(cursor.getColumnIndex("mthrs_db_id")));
            vaccinations.setMthrs_mbl_no(cursor.getString(cursor.getColumnIndex("mthrs_mbl_no")));
//            vaccinations.setDone_vaccines(cursor.getString(cursor.getColumnIndex("done_vaccines")));
//            vaccinations.setToday_due_vaccines(cursor.getString(cursor.getColumnIndex("today_due_vaccines")));
//            vaccinations.setFuture_due_vaccines(cursor.getString(cursor.getColumnIndex("future_due_vaccines")));
            vaccinationsArrayList.add(vaccinations);
        }
        cursor.close();
        db.close();
        Log.e("onresume--"," getmethoed...fetched data-- "+vaccinationsArrayList.size());
        return vaccinationsArrayList;
    }
    public ArrayList<Locations> getLOCATIONS() {
        ArrayList<Locations> locationslist =new ArrayList<>();
       Locations locations;
        String query = "Select  * FROM " + LOCATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int territory_id = cursor.getInt(0);
            String territory_list = cursor.getString(1);
            String parent_territory_name = cursor.getString(2);
            locations=new Locations();
           locationslist.add(locations);
        }
        cursor.close();
        db.close();
        return locationslist;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
    public void removefromDuelist(String child_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String truncateduechildvaccinationtable=" delete from "+UPDATECHILDTABLE +" where child_id="+child_id;
//        String truncateduechildvaccinationtable=" delete from "+UPDATECHILDTABLE +" where child_id="+child_id;
        String truncateDUECHILDDETAILSLISTTABLE=" delete from "+DUECHILDDETAILSLISTTABLE+" where child_id="+child_id;;
        db.beginTransaction();
//        db.execSQL(truncatelocationtable);
//        db.execSQL(truncateashatable);
//        db.execSQL(truncatevaccinationtable);
        String[] arr=new String[1];
        arr[0]=child_id;
//        db.delete(UPDATECHILDTABLE,"child_id=?",arr);
        db.execSQL(truncateduechildvaccinationtable);
        db.execSQL(truncateDUECHILDDETAILSLISTTABLE);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void deleteDueMotherChildDatabyChild_id(String child_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String truncateduechildvaccinationtable=" delete from "+DUEVACCINEChildren +" where child_id="+child_id;
        String truncateDUECHILDDETAILSLISTTABLE=" delete from "+DUECHILDDETAILSLISTTABLE +" where child_id="+child_id;
        db.execSQL(truncateduechildvaccinationtable);
        db.execSQL(truncateDUECHILDDETAILSLISTTABLE);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        Log.e("onresume--","table cleared.refresh.");
    }

    public void deleteDueMotherVaccinationDatabyMother_id(String mthr_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String truncateduechildvaccinationtable=" delete from "+DUEVACCINEChildren +" where mthr_id="+mthr_id;
        db.execSQL(truncateduechildvaccinationtable);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        Log.e("onresume--","table cleared.refresh.");
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String truncatelocationtable="delete from "+LOCATIONS;
        String truncateashatable=" delete from "+ASHAS_DETAILS;
        String truncatevaccinationtable=" delete from "+VACCINATIONS;
        String truncateduechildvaccinationtable=" delete from "+DUEVACCINEChildren;
        String truncateDUECHILDDETAILSLISTTABLE=" delete from "+DUECHILDDETAILSLISTTABLE;
//
        db.execSQL(truncatelocationtable);
        db.execSQL(truncateashatable);
        db.execSQL(truncatevaccinationtable);
        db.execSQL(truncateduechildvaccinationtable);
        db.execSQL(truncateDUECHILDDETAILSLISTTABLE);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        Log.e("onresume--","table cleared.login.");
    }
    public void clearOfflineTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
//        String truncatelocationtable="delete from "+LOCATIONS;
//        String truncateashatable=" delete from "+ASHAS_DETAILS;
//        String truncatevaccinationtable=" delete from "+VACCINATIONS;
//        String truncateduechildvaccinationtable=" delete from "+DUEVACCINEChildren;
        String truncateDUECHILDDETAILSLISTTABLE=" delete from "+OFFLINEMOTHERCHILDDATA;
//
//        db.execSQL(truncatelocationtable);
//        db.execSQL(truncateashatable);
//        db.execSQL(truncatevaccinationtable);
//        db.execSQL(truncateduechildvaccinationtable);
        db.execSQL(truncateDUECHILDDETAILSLISTTABLE);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        Log.e("onresume--","table cleared.login.");
    }
    public void clearVaccinationTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        String truncateduechildvaccinationtable=" delete from "+DUEVACCINEChildren;
        String truncateDUECHILDDETAILSLISTTABLE=" delete from "+DUECHILDDETAILSLISTTABLE;
//        db.execSQL(truncatelocationtable);
      //  db.execSQL(truncateashatable);
        db.execSQL(truncateDUECHILDDETAILSLISTTABLE);
        db.execSQL(truncateduechildvaccinationtable);
//        Log.e("deleted--1",""+ db.delete(DUEVACCINEChildren,"1",null));
//        Log.e("deleted--",""+db.delete(DUECHILDDETAILSLISTTABLE,"1",null));

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        Log.e("onresume--","table cleared.refresh.");
    }

    public boolean check_ChildUpdatedStatus(String child_id) {

        String query_inupdatedchildandchilddetails=" select child_id from "+UPDATECHILDTABLE +" where child_id="+child_id;
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag=false;
        Cursor cursor = db.rawQuery(query_inupdatedchildandchilddetails, null);
        while (cursor.moveToNext()) {
            String updatedchild_id = cursor.getString(0);
                if(updatedchild_id.equalsIgnoreCase(child_id))
                {
                    flag=true;
                }
        }

        db.close();
        return flag;
    }
    public ArrayList<AddMotherRequest> get_AddedMother_Data(boolean isdialerdata) {

        ArrayList<AddMotherRequest> addMotherRequestArrayList=new ArrayList<>();
        AddMotherRequest vaccinations;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select  * FROM " + ADDMOTHERTABLE;
        Cursor cursor=null;
        if(isdialerdata) {
//         query = "Select  * FROM " + ADDMOTHERTABLE +"WHERE onlinesubmitstatus"+" = ?" ;
            String whereclause = "onlinesubmitstatus= ?";
            String arg[] = {"N"};

             cursor = db.query(ADDMOTHERTABLE, null,whereclause,arg,null,null,null );
        }
        else
        {
             cursor = db.rawQuery(query,null);
        }
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            vaccinations=new AddMotherRequest();

            vaccinations.setUniqueid(cursor.getString(cursor.getColumnIndex("uniqueid")));
//            vaccinations.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
//            vaccinations.setMother_name(cursor.getString(cursor.getColumnIndex("mother_name")));
//            vaccinations.setMother_age(cursor.getString(cursor.getColumnIndex("mother_age")));
//            vaccinations.setArea(cursor.getString(cursor.getColumnIndex("area")));
//            vaccinations.setArea_name(cursor.getString(cursor.getColumnIndex("area_name")));
//            vaccinations.setAnm_name(cursor.getString(cursor.getColumnIndex("anm_name")));
//            vaccinations.setAnm_contact(cursor.getString(cursor.getColumnIndex("anm_contact")));
//            vaccinations.setAsha_name(cursor.getString(cursor.getColumnIndex("asha_name")));
//            vaccinations.setAsha_contact(cursor.getString(cursor.getColumnIndex("asha_contact")));
//            vaccinations.setChild_name(cursor.getString(cursor.getColumnIndex("child_name")));
//            vaccinations.setChild_dob(cursor.getString(cursor.getColumnIndex("child_dob")));
//            vaccinations.setCaptured_date(cursor.getString(cursor.getColumnIndex("captured_date")));
//            vaccinations.setOnlinesubmitstatus(cursor.getString(cursor.getColumnIndex("onlinesubmitstatus")));
//            String[] arr=cursor.getString(cursor.getColumnIndex("vaccinations")).split(",");
//            String[] arrvcccode=cursor.getString(cursor.getColumnIndex("vaccinationscodes")).split(",");
//            vaccinations.setVaccinations(arr);
//            vaccinations.setVaccinationscode(arrvcccode);
//            vaccinations.setVaccine_date(cursor.getString(cursor.getColumnIndex("vaccine_date")));
//            vaccinations.setOfflineid(cursor.getString(cursor.getColumnIndex("uniqueid")));
//            if(cursor.getString(cursor.getColumnIndex("force_new_child_entry")).equalsIgnoreCase("true"))
//                vaccinations.setForce_new_child_entry(true);
//            else
//                vaccinations.setForce_new_child_entry(false);
            addMotherRequestArrayList.add(vaccinations);
        }
        cursor.close();
        db.close();
        return addMotherRequestArrayList;
    }

    public ArrayList<AddMotherRequest> get_AddedMother_DatausingMobileMotherChildName(String mobileno,String mothername,String childname) {

        ArrayList<AddMotherRequest> addMotherRequestArrayList=new ArrayList<>();
        AddMotherRequest vaccinations;
        String query = "Select  * FROM " + ADDMOTHERTABLE +" where mobile like '"+mobileno+"' and mother_name like '"+mothername+"'  and child_name like '"+childname+"';" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            vaccinations=new AddMotherRequest();

//            vaccinations.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
//            vaccinations.setMother_name(cursor.getString(cursor.getColumnIndex("mother_name")));
//            vaccinations.setMother_age(cursor.getString(cursor.getColumnIndex("mother_age")));
//            vaccinations.setArea(cursor.getString(cursor.getColumnIndex("area")));
//            vaccinations.setArea_name(cursor.getString(cursor.getColumnIndex("area_name")));
//            vaccinations.setAnm_name(cursor.getString(cursor.getColumnIndex("anm_name")));
//            vaccinations.setAnm_contact(cursor.getString(cursor.getColumnIndex("anm_contact")));
//            vaccinations.setAsha_name(cursor.getString(cursor.getColumnIndex("asha_name")));
//            vaccinations.setAsha_contact(cursor.getString(cursor.getColumnIndex("asha_contact")));
//            vaccinations.setChild_name(cursor.getString(cursor.getColumnIndex("child_name")));
//            vaccinations.setChild_dob(cursor.getString(cursor.getColumnIndex("child_dob")));
//            vaccinations.setCaptured_date(cursor.getString(cursor.getColumnIndex("captured_date")));
//            String[] arr=cursor.getString(cursor.getColumnIndex("vaccinations")).split(",");
//            vaccinations.setVaccinations(arr);
//            vaccinations.setVaccine_date(cursor.getString(cursor.getColumnIndex("vaccine_date")));
////            if(cursor.getString(cursor.getColumnIndex("force_new_child_entry")).equalsIgnoreCase("true"))
////                vaccinations.setForce_new_child_entry(true);
////            else
//            vaccinations.setForce_new_child_entry(false);
            addMotherRequestArrayList.add(vaccinations);
        }
        cursor.close();
        db.close();
        return addMotherRequestArrayList;
    }
    public long insert_AddedMother_Data(AddMotherRequest data) {
        long count=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("uniqueid", data.getUniqueid());
//            values.put("mobile", data.getMobile());
//            values.put("mother_name", data.getMother_name());
//            values.put("mother_age", data.getMother_age());
//            values.put("area", data.getArea());
//            values.put("area_name", data.getArea_name());
//            values.put("anm_name", data.getAnm_name());
//            values.put("anm_contact", data.getAnm_contact());
//            values.put("asha_name", data.getAsha_name());
//            values.put("asha_contact", data.getAsha_contact());
//            values.put("child_name", data.getChild_name());
//            values.put("child_dob", data.getChild_dob());
//            values.put("vaccinations", data.getVaccinationsstr());
//            values.put("vaccinationscodes", data.getVaccinationscodes());
//            values.put("vaccine_date", data.getVaccine_date());
//            values.put("captured_date", data.getCaptured_date());
            values.put("onlinesubmitstatus", "N");

           count= db.insert(ADDMOTHERTABLE, null, values);
           db.setTransactionSuccessful();
            return count;
        } finally {
            db.endTransaction();
           // return -1;
        }
    }
    public long delete_AddedMother_Data(AddMotherRequest data) {
        long count=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String[]  args=new String[7];


        try {

            count= db.delete(ADDMOTHERTABLE, "mobile=? and mother_name=? and mother_age=? and anm_contact=? and asha_contact=? and child_name=? and child_dob=?", args);
            db.setTransactionSuccessful();
            return count;
        } finally {
            db.endTransaction();
            // return -1;
        }

    }
    public long insert_DueChildDetailsList(SearchedChildDetails data) {
        long count=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("child_id", data.getChild_id());
            values.put("mthr_id", data.getMthr_id());
            values.put("mother_name", data.getMother_name());
            values.put("child_contact", data.getChild_contact());
            values.put("child_unq_id", data.getChild_unq_id());
            values.put("child_name", data.getChild_name());
            values.put("child_dob", data.getChild_dob());
            values.put("child_status", data.getChild_status());
            values.put("is_vacinated_before", data.getIs_vacinated_before());
            values.put("added_time", data.getAdded_time());
            values.put("last_updation_time", data.getLast_updation_time());
            values.put("done_vaccines", toStringArrayList(data.getDone_vaccines()));
            values.put("today_due_vaccines", toStringArrayList(data.getToday_due_vaccines()));
            values.put("future_due_vaccines", toStringArrayList(data.getFuture_due_vaccines()));
            count= db.insert(DUECHILDDETAILSLISTTABLE, null, values);
           db.setTransactionSuccessful();
            return count;
        } finally {
            db.endTransaction();
           // return -1;
        }

    }
    public ArrayList<SearchedChildDetails> get_DueChildDetailsList() {
        ArrayList<SearchedChildDetails> searchedChildDetailsArrayList=new ArrayList<>();
       // ArrayList<AddMotherRequest> addMotherRequestArrayList=new ArrayList<>();
        SearchedChildDetails vaccinations;
        String query = "Select  * FROM " + DUECHILDDETAILSLISTTABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            vaccinations = new SearchedChildDetails();
            vaccinations.setChild_id(cursor.getString(cursor.getColumnIndex("child_id")));
            vaccinations.setMthr_id(cursor.getString(cursor.getColumnIndex("mthr_id")));
            vaccinations.setMother_name(cursor.getString(cursor.getColumnIndex("mother_name")));
            vaccinations.setChild_contact(cursor.getString(cursor.getColumnIndex("child_contact")));
            vaccinations.setChild_unq_id(cursor.getString(cursor.getColumnIndex("child_unq_id")));
            vaccinations.setChild_name(cursor.getString(cursor.getColumnIndex("child_name")));
            vaccinations.setChild_dob(cursor.getString(cursor.getColumnIndex("child_dob")));
            vaccinations.setChild_status(cursor.getString(cursor.getColumnIndex("child_status")));
            vaccinations.setIs_vacinated_before(cursor.getString(cursor.getColumnIndex("is_vacinated_before")));
            vaccinations.setAdded_time(cursor.getString(cursor.getColumnIndex("added_time")));
            vaccinations.setLast_updation_time(cursor.getString(cursor.getColumnIndex("last_updation_time")));
            vaccinations.setDone_vaccines(null);//cursor.getString(cursor.getColumnIndex("done_vaccines")));
            vaccinations.setToday_due_vaccines(null);//cursor.getString(cursor.getColumnIndex("today_due_vaccines")));
            vaccinations.setFuture_due_vaccines(null);//cursor.getString(cursor.getColumnIndex("future_due_vaccines")));
            searchedChildDetailsArrayList.add(vaccinations);
            cursor.close();
            db.close();
            return searchedChildDetailsArrayList;
        }
        return searchedChildDetailsArrayList;
    }
    public SearchedChildDetails get_DueChildDetailsByIDandContact(String child_id,String child_contact) {
        SearchedChildDetails  vaccinations = new SearchedChildDetails();
        String[] arg =new String[2];
        arg[0]=child_id;
        arg[1]=child_contact;
        String query = "Select  * FROM " + DUECHILDDETAILSLISTTABLE+" where child_id=? and child_contact=? limit 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, arg);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            vaccinations.setChild_id(cursor.getString(cursor.getColumnIndex("child_id")));
            vaccinations.setMthr_id(cursor.getString(cursor.getColumnIndex("mthr_id")));
            vaccinations.setMother_name(cursor.getString(cursor.getColumnIndex("mother_name")));
            vaccinations.setChild_contact(cursor.getString(cursor.getColumnIndex("child_contact")));
            vaccinations.setChild_unq_id(cursor.getString(cursor.getColumnIndex("child_unq_id")));
            vaccinations.setChild_name(cursor.getString(cursor.getColumnIndex("child_name")));
            vaccinations.setChild_dob(cursor.getString(cursor.getColumnIndex("child_dob")));
            vaccinations.setChild_status(cursor.getString(cursor.getColumnIndex("child_status")));
            vaccinations.setIs_vacinated_before(cursor.getString(cursor.getColumnIndex("is_vacinated_before")));
            vaccinations.setAdded_time(cursor.getString(cursor.getColumnIndex("added_time")));
            vaccinations.setLast_updation_time(cursor.getString(cursor.getColumnIndex("last_updation_time")));
            vaccinations.setDone_vaccines(getArrayfromCommaSeparatedString(cursor.getString(cursor.getColumnIndex("done_vaccines"))));//);
            vaccinations.setToday_due_vaccines(getArrayfromCommaSeparatedString(cursor.getString(cursor.getColumnIndex("today_due_vaccines"))));//cursor.getString(cursor.getColumnIndex("today_due_vaccines")));
            vaccinations.setFuture_due_vaccines(getArrayfromCommaSeparatedString(cursor.getString(cursor.getColumnIndex("future_due_vaccines"))));//cursor.getString(cursor.getColumnIndex("future_due_vaccines")));
            cursor.close();
            db.close();
            return vaccinations;
        }
        return vaccinations;
    }
    public SearchedChildDetails get_OfflineChildDetailsByIDandContact(String child_id,String child_contact) {
        SearchedChildDetails  vaccinations = new SearchedChildDetails();
        String[] arg =new String[2];
        arg[0]=child_id;
        arg[1]=child_contact;
        String query = "Select  * FROM " + OFFLINEMOTHERCHILDDATA+" where child_id=? and child_contact=? limit 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, arg);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {


            vaccinations.setChild_id(cursor.getString(cursor.getColumnIndex("child_id")));
            vaccinations.setMthr_id(cursor.getString(cursor.getColumnIndex("mthrs_db_id")));
            vaccinations.setMother_name(cursor.getString(cursor.getColumnIndex("mother_name")));
            vaccinations.setChild_contact(cursor.getString(cursor.getColumnIndex("child_contact")));
            vaccinations.setChild_unq_id(cursor.getString(cursor.getColumnIndex("child_unq_id")));
            vaccinations.setChild_name(cursor.getString(cursor.getColumnIndex("child_name")));
            vaccinations.setChild_dob(cursor.getString(cursor.getColumnIndex("child_dob")));
            vaccinations.setChild_status(cursor.getString(cursor.getColumnIndex("child_status")));
//            vaccinations.setIs_vacinated_before(cursor.getString(cursor.getColumnIndex("is_vacinated_before")));
//            vaccinations.setAdded_time(cursor.getString(cursor.getColumnIndex("added_time")));
//            vaccinations.setLast_updation_time(cursor.getString(cursor.getColumnIndex("last_updation_time")));
            vaccinations.setDone_vaccines(getArrayfromCommaSeparatedString(cursor.getString(cursor.getColumnIndex("done_vaccines"))));//);
            vaccinations.setToday_due_vaccines(getArrayfromCommaSeparatedString(cursor.getString(cursor.getColumnIndex("today_due_vaccines"))));//cursor.getString(cursor.getColumnIndex("today_due_vaccines")));
            vaccinations.setFuture_due_vaccines(getArrayfromCommaSeparatedString(cursor.getString(cursor.getColumnIndex("future_due_vaccines"))));//cursor.getString(cursor.getColumnIndex("future_due_vaccines")));
            cursor.close();
            db.close();
            return vaccinations;
        }
        return vaccinations;
    }

    public ArrayList<SearchedChildDetails> get_DueChildDetailsByMother_ID(String mother_id) {
        ArrayList<SearchedChildDetails> arrayList=new ArrayList<>();
        SearchedChildDetails  vaccinations = new SearchedChildDetails();
        String[] arg =new String[1];
//        arg[0]=child_id;
        arg[0]=mother_id;
        String query = "Select  * FROM " + DUECHILDDETAILSLISTTABLE+" where mthr_id=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, arg);
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            vaccinations.setChild_id(cursor.getString(cursor.getColumnIndex("child_id")));
            vaccinations.setMthr_id(cursor.getString(cursor.getColumnIndex("mthr_id")));
            vaccinations.setMother_name(cursor.getString(cursor.getColumnIndex("mother_name")));
            vaccinations.setChild_contact(cursor.getString(cursor.getColumnIndex("child_contact")));
            vaccinations.setChild_unq_id(cursor.getString(cursor.getColumnIndex("child_unq_id")));
            vaccinations.setChild_name(cursor.getString(cursor.getColumnIndex("child_name")));
            vaccinations.setChild_dob(cursor.getString(cursor.getColumnIndex("child_dob")));
            vaccinations.setChild_status(cursor.getString(cursor.getColumnIndex("child_status")));
            vaccinations.setIs_vacinated_before(cursor.getString(cursor.getColumnIndex("is_vacinated_before")));
            vaccinations.setAdded_time(cursor.getString(cursor.getColumnIndex("added_time")));
            vaccinations.setLast_updation_time(cursor.getString(cursor.getColumnIndex("last_updation_time")));
            vaccinations.setDone_vaccines(getArrayfromCommaSeparatedString(cursor.getString(cursor.getColumnIndex("done_vaccines"))));//);
            vaccinations.setToday_due_vaccines(getArrayfromCommaSeparatedString(cursor.getString(cursor.getColumnIndex("today_due_vaccines"))));//cursor.getString(cursor.getColumnIndex("today_due_vaccines")));
            vaccinations.setFuture_due_vaccines(getArrayfromCommaSeparatedString(cursor.getString(cursor.getColumnIndex("future_due_vaccines"))));//cursor.getString(cursor.getColumnIndex("future_due_vaccines")));
            cursor.close();
            db.close();
            arrayList.add(vaccinations);
        }
        return arrayList;
    }
    private ArrayList<String> getArrayfromCommaSeparatedString(String vaccines) {
        if(vaccines!=null) {
            if(vaccines.trim().length()>0) {
                String[] list = vaccines.split(",", -1);
                return new ArrayList(Arrays.asList(list));
            }
            else
            {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }
    public ArrayList<UpdateChildVaccineRequest> get_UpdatedChild_Data(boolean isdialerdata) {
        ArrayList<UpdateChildVaccineRequest> addMotherRequestArrayList=new ArrayList<>();
        UpdateChildVaccineRequest vaccinations;
        String query = "select  * FROM " +UPDATECHILDTABLE ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=null;
        if(isdialerdata) {
//         query = "Select  * FROM " + ADDMOTHERTABLE +"WHERE onlinesubmitstatus"+" = ?" ;
            String whereclause = "onlinesubmitstatus= ?";
            String arg[] = {"N"};
//            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.query(UPDATECHILDTABLE, null,whereclause,arg,null,null,null );
        }
        else {
            cursor = db.rawQuery(query, null);
        }
//        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            vaccinations=new UpdateChildVaccineRequest();
            vaccinations.setUniqueid(cursor.getString(cursor.getColumnIndex("uniqueid")));
            vaccinations.setChild_id(cursor.getString(cursor.getColumnIndex("child_id")));
            vaccinations.setVaccine_date(cursor.getString(cursor.getColumnIndex("vaccine_date")));
            vaccinations.setSelect(cursor.getString(cursor.getColumnIndex("selected")));
            vaccinations.setCaptured_date(cursor.getString(cursor.getColumnIndex("captured_date")));
            vaccinations.setOnlinesubmitstatus(cursor.getString(cursor.getColumnIndex("onlinesubmitstatus")));
            String[] arr=cursor.getString(cursor.getColumnIndex("vaccination_name")).split(",");
            vaccinations.setVaccination_name(arr);
            vaccinations.setOfflineid(cursor.getString(cursor.getColumnIndex("uniqueid")));
            addMotherRequestArrayList.add(vaccinations);
        }
        cursor.close();
        db.close();
        return addMotherRequestArrayList;
    }
    public long insert_UpdateChildData(UpdateChildVaccineRequest updateChildVaccineRequest) {
        long count=-1;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("uniqueid", updateChildVaccineRequest.getUniqueid());
            values.put("child_id", updateChildVaccineRequest.getChild_id());
            values.put("vaccine_date", updateChildVaccineRequest.getVaccine_date());
            values.put("selected", updateChildVaccineRequest.getSelect());
            values.put("vaccination_name", updateChildVaccineRequest.getVaccination_namestr());
            values.put("captured_date", updateChildVaccineRequest.getCaptured_date());
            values.put("onlinesubmitstatus", "N");
          count=  db.insert(UPDATECHILDTABLE, null, values);
//            DUECHILDDETAILSLISTTABLE
          db.setTransactionSuccessful();
          return count;
            } finally {
                db.endTransaction();
               // return -1;
            }
    }
    public static String toString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax)
                return b.append(']').toString();
            b.append(",");
        }
    }
    public static String toStringArrayList(ArrayList<String> a) {

        if (a == null)
            return "null";

        int iMax = a.size() - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        b.append("");
        for (int i = 0; ; i++) {
            b.append(a.get(i));
            if (i == iMax)
                return b.append("").toString();
            b.append(",");
        }
    }


    public void ChangeAddmotherOnlineSubmitStatus(String uniqueid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("onlinesubmitstatus", "Y");
            db.update(ADDMOTHERTABLE, values,"uniqueid=?", new String[]{uniqueid});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


    public void ChangeUpdatechildOnlineSubmitStatus(String uniqueid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("onlinesubmitstatus", "Y");
            db.update(UPDATECHILDTABLE, values,"uniqueid=?", new String[]{uniqueid});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
