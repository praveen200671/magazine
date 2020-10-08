package com.anm.bslndmag.Model;

public class ANMDetailsRequest {
    private String user_id;

    public ANMDetailsRequest(String userid)
    {
        this.user_id=userid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

