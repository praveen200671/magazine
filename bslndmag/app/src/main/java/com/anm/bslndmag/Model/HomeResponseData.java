package com.anm.bslndmag.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HomeResponseData {
    @SerializedName("announcement")
    @Expose
    ArrayList<HomeAnnouncements> announcement;
    @SerializedName("magazine")
    @Expose
    ArrayList<HomeAnnouncements> magazine;
//    HomeArticles article;

    public ArrayList<HomeAnnouncements> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(ArrayList<HomeAnnouncements> announcement) {
        this.announcement = announcement;
    }

    public ArrayList<HomeAnnouncements> getMagazine() {
        return magazine;
    }

    public void setMagazine(ArrayList<HomeAnnouncements> magazine) {
        this.magazine = magazine;
    }
//
//    public HomeArticles getArticle() {
//        return article;
//    }
//
//    public void setArticle(HomeArticles article) {
//        this.article = article;
//    }
}
