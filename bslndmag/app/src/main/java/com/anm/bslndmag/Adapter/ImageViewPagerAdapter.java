package com.anm.bslndmag.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.anm.bslndmag.Fragments.ImageViewFragment;
import com.anm.bslndmag.Model.HomeAnnouncements;

import java.util.ArrayList;

public class ImageViewPagerAdapter extends FragmentStatePagerAdapter {

    boolean isread;
    HomeAnnouncements homeAnnouncements;
    public ImageViewPagerAdapter(@NonNull FragmentManager fm, HomeAnnouncements homeAnnouncements,boolean isread) {
        super(fm);
        this.homeAnnouncements=homeAnnouncements;
        this.isread=isread;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position < homeAnnouncements.getImages().size())
            return ImageViewFragment.newInstance(homeAnnouncements,position,isread);
        else
            return null;
    }

    @Override
    public int getCount() {
        return homeAnnouncements.getImages().size();
    }
}
