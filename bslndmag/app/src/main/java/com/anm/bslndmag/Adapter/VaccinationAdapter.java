package com.anm.bslndmag.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anm.bslndmag.Model.Locations;
import com.anm.bslndmag.Model.Vaccinations;
import com.anm.bslndmag.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class VaccinationAdapter extends  RecyclerView.Adapter<VaccinationAdapter.ViewHolder>{
    Context context;
    ArrayList<Vaccinations> listvaccination;
    ArrayList<Vaccinations> selectedvaccines;
    VaccinationAdapter.vaccinationselectlistener listener;
    String listid;
    public VaccinationAdapter(Context context, ArrayList<Vaccinations> listvaccination,ArrayList<Vaccinations> selectedvaccines,  VaccinationAdapter.vaccinationselectlistener listener) {
        this.context = context;
        this.listvaccination = listvaccination;
        this.selectedvaccines = selectedvaccines;
        this.listener=listener;
        this.listid=listid;
        setHasStableIds(true);
    }

    public interface vaccinationselectlistener{
        void chkselectcategory(Vaccinations cd, boolean chb_vaccination);
    }

    @NonNull
    @Override
    public VaccinationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.layout_product_category_item,viewGroup,false);
        return new VaccinationAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VaccinationAdapter.ViewHolder viewHolder, int i) {
        final Vaccinations cd = listvaccination.get(i);
        viewHolder.tv_vaccinename.setText(cd.getVcc_name());
       // SetSelectedVaccine(viewHolder.chb_vaccination, cd, selectedvaccines);
        viewHolder.chb_vaccination.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                try {
                    if (isChecked) {
                        listener.chkselectcategory(cd, isChecked);
                    } else {
                        listener.chkselectcategory(cd, isChecked);
                    }
                }catch (Exception ex)
                {
                    //Toasty.error(context,ex.getMessage()).show();
                }
            }
        });
    }
    public void SetSelectedVaccine(CheckBox chk_vaccine,Vaccinations vaccinations,ArrayList<Vaccinations> listselectedvaccines)
    {
        for(Vaccinations selectedvaccine:listselectedvaccines)
        {
            if(vaccinations.getVcc_name()==selectedvaccine.getVcc_name()) {
                chk_vaccine.setChecked(true);
                break;
            } else
            {
            //    chk_vaccine.setChecked(false);
            }
        }




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
        LinearLayout ll_category;
        TextView tv_vaccinename;
        CheckBox chb_vaccination;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_category=(LinearLayout) itemView.findViewById(R.id.ll_category);
            tv_vaccinename=(TextView) itemView.findViewById(R.id.tv_categoryname);
            chb_vaccination=(CheckBox) itemView.findViewById(R.id.chb_vaccination);

        }

//        public void bind(final Vaccinations cd, final CheckBox chb_vaccination, final VaccinationAdapter.vaccinationselectlistener listener) {
//            ll_category.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   // listener.chkselectcategory(cd,chb_vaccination);
//                }
//            });
//        }


    }
}
