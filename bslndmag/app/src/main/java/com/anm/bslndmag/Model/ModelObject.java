package com.anm.bslndmag.Model;

import com.anm.bslndmag.R;

public class ModelObject {
//
//    RED(R.stri, R.layout.view_red),
//    BLUE(R.string.blue, R.layout.view_blue),
//    GREEN(R.string.green, R.layout.view_green);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}