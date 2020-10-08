package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ANMMotherData {
   String mthrs_db_id;
   String mthrs_unq_no;
   String mthrs_mbl_no;

    public String getMthrs_db_id() {
        return mthrs_db_id;
    }

    public void setMthrs_db_id(String mthrs_db_id) {
        this.mthrs_db_id = mthrs_db_id;
    }

    public String getMthrs_unq_no() {
        return mthrs_unq_no;
    }

    public void setMthrs_unq_no(String mthrs_unq_no) {
        this.mthrs_unq_no = mthrs_unq_no;
    }

    public String getMthrs_mbl_no() {
        return mthrs_mbl_no;
    }

    public void setMthrs_mbl_no(String mthrs_mbl_no) {
        this.mthrs_mbl_no = mthrs_mbl_no;
    }
}
