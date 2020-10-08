package com.anm.bslndmag.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeAnnouncements implements Serializable {

        String url;
        String id;
        String issue_no;
        String month;
        String year;
        String is_free;
        String amount;
        String is_approved;
        String no_of_pages;
        String approved_by;
        ArrayList<String> images;
        String name;
        String type;
        String description;
        String preview_pages;
        String is_subscribe;
        ArrayList<SubscriptionPlansData> plans;


    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<SubscriptionPlansData> getPlans() {
        return plans;
    }

    public void setPlans(ArrayList<SubscriptionPlansData> plans) {
        this.plans = plans;
    }

    public String getPreview_pages() {
        return preview_pages;
    }

    public void setPreview_pages(String preview_pages) {
        this.preview_pages = preview_pages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssue_no() {
        return issue_no;
    }

    public void setIssue_no(String issue_no) {
        this.issue_no = issue_no;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(String is_approved) {
        this.is_approved = is_approved;
    }

    public String getNo_of_pages() {
        return no_of_pages;
    }

    public void setNo_of_pages(String no_of_pages) {
        this.no_of_pages = no_of_pages;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(String approved_by) {
        this.approved_by = approved_by;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
