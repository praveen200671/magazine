package com.anm.bslndmag;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.anm.bslndmag.Adapter.ImageViewPagerAdapter;
import com.anm.bslndmag.Model.HomeAnnouncements;
import com.anm.bslndmag.Utils.ZoomOutPageTransformer;

public class ReadMagazine extends AppCompatActivity {

    ImageViewPagerAdapter viewPagerAdapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__searched_mother_child);
        pager=findViewById(R.id.pager);
        HomeAnnouncements cd=(HomeAnnouncements)getIntent().getExtras().getSerializable("magazine");

        viewPagerAdapter=new ImageViewPagerAdapter(getSupportFragmentManager(),cd,true);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setAdapter(viewPagerAdapter);



        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if(position < (viewPagerAdapter.getCount()-1)  && position < (colors.length-1)){
//                    pager.setBackgroundColor(
//                            (Integer)argbEvaluator.evaluate(
//                                    positionOffset,
//                                    colors[position],
//                                    colors[position+1]));
//                }
//                else {
//                    pager.setBackgroundColor(colors[colors.length - 1]);
//                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //sendRequest_getMagazineDetails();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1099)
            finish();
    }
}
