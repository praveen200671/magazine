package com.anm.bslndmag.Model;

public class Vacccine {

    String id;
    String vaccine_code;
    String vaccination_name;
    String age_group;
    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVaccine_code() {
        return vaccine_code;
    }

    public void setVaccine_code(String vaccine_code) {
        this.vaccine_code = vaccine_code;
    }

    public String getVaccination_name() {
        return vaccination_name;
    }

    public void setVaccination_name(String vaccination_name) {
        this.vaccination_name = vaccination_name;
    }

    public String getAge_group() {
        return age_group;
    }

    public void setAge_group(String age_group) {
        this.age_group = age_group;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
