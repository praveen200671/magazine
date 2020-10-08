package com.anm.bslndmag.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anm.bslndmag.Model.SubscriptionPlansData;
import com.anm.bslndmag.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class SubscriptionPlansAdapter  extends RecyclerView.Adapter<SubscriptionPlansAdapter.ViewHolder>{
        Context context;
        List<SubscriptionPlansData> listretailerdata;
        retailerdatalistener listener;
public SubscriptionPlansAdapter(Context context, ArrayList<SubscriptionPlansData> listretailerdata, retailerdatalistener listener) {
        this.context = context;
        this.listretailerdata = listretailerdata;
        this.listener=listener;
        }
public interface retailerdatalistener{
    void cardviewclicklistener(SubscriptionPlansData retailerdata);
}
    @NonNull
    @Override
    public SubscriptionPlansAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_item, viewGroup, false);
        return new SubscriptionPlansAdapter.ViewHolder(v);
    }
    public void notifychanges(ArrayList<SubscriptionPlansData> larrayList_announcements)
    {
        listretailerdata=larrayList_announcements;

        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull SubscriptionPlansAdapter.ViewHolder viewHolder, int i) {
        SubscriptionPlansData plansData=listretailerdata.get(i);
        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str1= new SpannableString(plansData.getName());
        str1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str1.length(), 0);
        str1.setSpan(new RelativeSizeSpan((float) 1.2), 0,str1.length(), 0);
        builder.append(str1);


        viewHolder.tv_planname.setText(builder, TextView.BufferType.SPANNABLE);
        viewHolder.tv_price.setText(plansData.getAmount()+"/"+plansData.getPlan_period()+" "+plansData.getPlan_type());//seller.getAddress()
        viewHolder.cv_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(plansData.getIs_taken()!=null) {
            if (plansData.getIs_taken().equalsIgnoreCase("0")) {
                viewHolder.ll_subscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.cardviewclicklistener(plansData);
                    }
                });
            } else {
                viewHolder.tv_subscribe.setBackgroundColor(context.getResources().getColor(R.color.green));
                viewHolder.tv_subscribe.setText("Subscribed");
                viewHolder.tv_subscribe.setTextColor(context.getResources().getColor(R.color.white));
            }
        }
        else
        {
            viewHolder.ll_subscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.cardviewclicklistener(plansData);
                }
            });
        }
        

    }

    @Override
    public int getItemCount() {
        if(listretailerdata!=null)
        {
            return listretailerdata.size();
        }
        else{
            return 0;
        }

    }

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tv_planname,tv_price,tv_subscribe;
    LinearLayout ll_subscribe;
    CardView cv_plan;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_planname=(TextView)itemView.findViewById(R.id.tv_planname);
        tv_price=(TextView)itemView.findViewById(R.id.tv_price);
        ll_subscribe=(LinearLayout)itemView.findViewById(R.id.ll_subscribe);
        tv_subscribe=(TextView) itemView.findViewById(R.id.tv_subscribe);

//        imgretailer=(ImageView)itemView.findViewById(R.id.txtreaileravtar);
//        cardviewretailerdata=(LinearLayout)itemView.findViewById(R.id.linearlayoutretailer);
        cv_plan=(CardView)itemView.findViewById(R.id.cv_plan);
    }

    public void bind(final SubscriptionPlansData retailerUserData, final retailerdatalistener listener, LinearLayout cardviewretailerdata, final List<SubscriptionPlansData> listretailerdata) {
        cardviewretailerdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cardviewclicklistener(retailerUserData);
            }
        });
    }
}
}
