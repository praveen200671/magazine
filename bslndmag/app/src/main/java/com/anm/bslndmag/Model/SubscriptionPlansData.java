package com.anm.bslndmag.Model;

import java.io.Serializable;

public class SubscriptionPlansData implements Serializable {

    private String id;
    private String name;
    private String amount;
    private String plan_type;
    private String plan_period;
    private String is_taken;
    private String expire_days;

    public String getIs_taken() {
        return is_taken;
    }

    public void setIs_taken(String is_taken) {
        this.is_taken = is_taken;
    }

    public String getExpire_days() {
        return expire_days;
    }

    public void setExpire_days(String expire_days) {
        this.expire_days = expire_days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public String getPlan_period() {
        return plan_period;
    }

    public void setPlan_period(String plan_period) {
        this.plan_period = plan_period;
    }
}
