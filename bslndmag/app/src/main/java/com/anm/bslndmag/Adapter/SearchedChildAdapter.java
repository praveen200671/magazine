package com.anm.bslndmag.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.anm.bslndmag.Model.SearchedMotherItem;
import com.anm.bslndmag.R;

import java.util.ArrayList;

public class SearchedChildAdapter extends  RecyclerView.Adapter<com.anm.bslndmag.Adapter.SearchedChildAdapter.ViewHolder>{
    Context context;
    ArrayList<SearchedMotherItem> listvaccination;

    com.anm.bslndmag.Adapter.SearchedChildAdapter.duevaccinationselectlistener listener;
    String listid;
    public SearchedChildAdapter(Context context, ArrayList<SearchedMotherItem> listvaccination, com.anm.bslndmag.Adapter.SearchedChildAdapter.duevaccinationselectlistener listener) {
        this.context = context;
        this.listvaccination = listvaccination;
        this.listener=listener;
        this.listid=listid;
        setHasStableIds(true);
    }

    public interface duevaccinationselectlistener{
        void onclick(SearchedMotherItem cd);
       // void onsearchclicklister(SearchedMotherItem cd);
    }

    @NonNull
    @Override
    public com.anm.bslndmag.Adapter.SearchedChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.weekly_duevaccineitem, viewGroup, false);
        return new com.anm.bslndmag.Adapter.SearchedChildAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final com.anm.bslndmag.Adapter.SearchedChildAdapter.ViewHolder viewHolder, int i) {

        final SearchedMotherItem cd = listvaccination.get(i);
        viewHolder.tv_mothername.setText(cd.getMthrs_name());
        viewHolder.tv_childname.setText(cd.getChild_name());
        viewHolder.tv_mobileno.setText(cd.getChild_mobile());
        if(cd.getMthrs_name().equalsIgnoreCase("---addmother---"))
        {
            viewHolder.ll_searchmobileno.setVisibility(View.VISIBLE);
            viewHolder.ll_category.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.ll_searchmobileno.setVisibility(View.GONE);
            viewHolder.ll_category.setVisibility(View.VISIBLE);
        }

//        viewHolder.ll_searchmobileno.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                listener.onsearchclicklister(cd);
//            }
//        });

//            viewHolder.tv_injection.setText(( TextUtils.join (", ", cd.getDue_vaccinations())));
        // SetSelectedVaccine(viewHolder.chb_vaccination, cd, selectedvaccines);
        viewHolder.tv_injection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.onclick(cd);
            }
        });

    }

    public void notifyadater(ArrayList<SearchedMotherItem> list)
    {
        listvaccination =list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
    @Override
    public int getItemCount() {
        if(listvaccination!=null)
        {
            return listvaccination.size();
        }
        else {
            return 0;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_mothername,tv_childname,tv_mobileno,tv_injection;
        LinearLayout ll_searchmobileno,ll_category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_searchmobileno=(LinearLayout) itemView.findViewById(R.id.ll_searchmobileno);
            ll_category=(LinearLayout) itemView.findViewById(R.id.ll_category);
            tv_mothername=(TextView) itemView.findViewById(R.id.tv_mothername);
            tv_childname=(TextView) itemView.findViewById(R.id.tv_childname);
            tv_mobileno=(TextView) itemView.findViewById(R.id.tv_mobileno);
            tv_injection=(TextView) itemView.findViewById(R.id.tv_injection);
//                chb_vaccination=(CheckBox) itemView.findViewById(R.id.chb_vaccination);

        }
    }
}






