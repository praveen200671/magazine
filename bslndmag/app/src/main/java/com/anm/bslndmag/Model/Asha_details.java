package com.anm.bslndmag.Model;

public class Asha_details {

    String id;
    String asha_name;
    String territory_id;
    String mobile_number;
    String status;

   /* public Asha_details(String id,
            String asha_name,
            String territory_id,
            String mobile_number,
            String status){
      this.asha_name=asha_name;
      this.territory_id=territory_id;
      this.mobile_number=mobile_number;
      this.status=status;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsha_name() {
        return asha_name;
    }

    public void setAsha_name(String asha_name) {
        this.asha_name = asha_name;
    }

    public String getTerritory_id() {
        return territory_id;
    }

    public void setTerritory_id(String territory_id) {
        this.territory_id = territory_id;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
