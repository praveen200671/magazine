package com.anm.bslndmag.Model;

import java.util.ArrayList;

public class Locations {

    String territory_id;
    String territory_list;
    String parent_territory_name;
    ArrayList<ArrayList<Asha_details>> asha_details;

    public ArrayList<ArrayList<Asha_details>> getAsha_details() {
        return asha_details;
    }

    public void setAsha_details(ArrayList<ArrayList<Asha_details>> asha_details) {
        this.asha_details = asha_details;
    }

//    public Locations()
//    {
//        this.territory_id=territory_id;
//        this.territory_list=territory_list;
//        this.parent_territory_name=parent_territory_name;
////        this.asha_details=asha_details;
//    }

    public String getTerritory_id() {
        return territory_id;
    }

    public void setTerritory_id(String territory_id) {
        this.territory_id = territory_id;
    }

    public String getTerritory_list() {
        return territory_list;
    }

    public void setTerritory_list(String territory_list) {
        this.territory_list = territory_list;
    }

    public String getParent_territory_name() {
        return parent_territory_name;
    }

    public void setParent_territory_name(String parent_territory_name) {
        this.parent_territory_name = parent_territory_name;
    }
}
