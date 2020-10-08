package com.anm.bslndmag.Model;

import java.util.ArrayList;

public class SearchMotherChildData {
    ArrayList<SearchedMotherItem> mothers;
    ArrayList<SearchedChildItem> children;

    public ArrayList<SearchedMotherItem> getMothers() {
        return mothers;
    }

    public void setMothers(ArrayList<SearchedMotherItem> mothers) {
        this.mothers = mothers;
    }

    public ArrayList<SearchedChildItem> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<SearchedChildItem> children) {
        this.children = children;
    }
}
