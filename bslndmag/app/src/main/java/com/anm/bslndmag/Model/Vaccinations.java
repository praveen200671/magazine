package com.anm.bslndmag.Model;

public class Vaccinations {
    String vcc_name;
    String vcc_code;
    String child_id;
    String vaccinationtype;

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getVaccinationtype() {
        return vaccinationtype;
    }

    public void setVaccinationtype(String vaccinationtype) {
        this.vaccinationtype = vaccinationtype;
    }

    public String getVcc_name() {
        return vcc_name;
    }

    public void setVcc_name(String vcc_name) {
        this.vcc_name = vcc_name;
    }

    public String getVcc_code() {
        return vcc_code;
    }

    public void setVcc_code(String vcc_code) {
        this.vcc_code = vcc_code;
    }
}
