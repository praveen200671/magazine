package com.anm.bslndmag.Adapter;
import android.app.Activity;
import android.content.Context;
//import android.support.v4.view.PagerAdapter;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
//import androidx.viewpager.widget.ViewPager;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.anm.bslndmag.Model.HomeAnnouncements;
import com.anm.bslndmag.Model.ModelObject;
import com.anm.bslndmag.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_SETTLING;

public class CustomPagerAdapter extends PagerAdapter  {

    private Context activity;

    ArrayList<HomeAnnouncements>  arrayList_announcements;
    CustomPagerAdapter.vaccinationselectlistener vaccinationselectlistener;

    public CustomPagerAdapter(Context activity, ArrayList<HomeAnnouncements> arrayList_announcements,CustomPagerAdapter.vaccinationselectlistener vaccinationselectlistener){

        this.activity = activity;
        this.arrayList_announcements = arrayList_announcements;
        this.vaccinationselectlistener=vaccinationselectlistener;

    }

    public interface vaccinationselectlistener{
        void onclick(String url);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = ((Activity)activity).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.imageview_item, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.iv_announcements);


        ProgressBar homeprogress = (ProgressBar) viewItem.findViewById(R.id.homeprogress);
        //imageView.setImageResource(homeAnnouncements.get(position).getImages().get(position));
        String imgUrl = arrayList_announcements.get(position).getImages().get(0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaccinationselectlistener.onclick(imgUrl);
            }
        });

        Log.d("url....",imgUrl);

        try {
//            if(i%3==0)
//            imgUrl="";
            if(imgUrl!=null) {
                if (imgUrl.length() > 0) {
                    //imageView.bind(imgUrl, listener);
                    imageDownload(activity, imgUrl, imageView,homeprogress);
                }
            }
        }catch (Exception e)
        {
            e.getMessage();
        }

        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }
    public  void imageDownload(Context ctx, String url, final ImageView imageViewCompat, ProgressBar homeprogress ){

        Picasso.with(ctx).load(Uri.parse(url)).fit()
                .transform(new RoundedCornersTransformation(10, 0))
                //.placeholder(R.drawable.progress_animation)
                .placeholder(R.drawable.loaderimagezoom)
//                    .error(R.drawable.ic_error)
                .into(imageViewCompat, new Callback() {
                    @Override
                    public void onSuccess() {
                        homeprogress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayList_announcements.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }


}

