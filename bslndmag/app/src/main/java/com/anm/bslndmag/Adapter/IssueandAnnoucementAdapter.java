package com.anm.bslndmag.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anm.bslndmag.Model.HomeAnnouncements;
import com.anm.bslndmag.Model.Vaccinations;
import com.anm.bslndmag.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class IssueandAnnoucementAdapter extends  RecyclerView.Adapter<IssueandAnnoucementAdapter.ViewHolder>{
    Context context;
    ArrayList<HomeAnnouncements> arrayList_announcements;;

    IssueandAnnoucementAdapter.vaccinationselectlistener listener;

    public IssueandAnnoucementAdapter(Context context, ArrayList<HomeAnnouncements> arrayList_announcements, IssueandAnnoucementAdapter.vaccinationselectlistener listener) {
        this.context = context;
        this.arrayList_announcements = arrayList_announcements;
        this.listener=listener;
        setHasStableIds(true);
    }
    public interface vaccinationselectlistener{
        void chkselectcategory(HomeAnnouncements cd,String isRead);
    }
    @NonNull
    @Override
    public IssueandAnnoucementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.childvaccineitem,viewGroup,false);
        Log.d("item-----","-----"+i);
        return new IssueandAnnoucementAdapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final IssueandAnnoucementAdapter.ViewHolder viewHolder, int i) {
        final HomeAnnouncements cd = arrayList_announcements.get(i);
        if(cd.getType()==null)
        {
            cd.setType("");
        }

        if(cd.getName()==null)
        {
            cd.setName("");
        }
        if(cd.getType().equalsIgnoreCase("custom")) {
//            viewHolder.btn_read.setText("View");
//            viewHolder.rl_buyandview.setVisibility(View.GONE);
//            viewHolder.btn_buy.setVisibility(View.GONE);
//            viewHolder.rl_buyandview.setVisibility(View.GONE);
            viewHolder.tv_header.setText(cd.getName());
        }
        else {
            viewHolder.rl_buyandview.setVisibility(View.VISIBLE);
            //viewHolder.tv_header.setText("Guru Kirpa");
            if(cd.getName().trim().length()>0)
            {
                viewHolder.tv_header.setText(cd.getName());
            }
            else
            {
                viewHolder.tv_header.setVisibility(View.GONE);
            }

            if(cd.getAmount().equalsIgnoreCase("0")||cd.getIs_free().equalsIgnoreCase("1")) {
                viewHolder.btn_buy.setText("Free");
            }
            if(cd.getIs_subscribe().equalsIgnoreCase("1")) {
                viewHolder.btn_buy.setText("Subscribed @Rs"+cd.getAmount());
                viewHolder.btn_buy.setBackgroundColor(((context.getResources()).getColor(R.color.green)));
            }
            else if(cd.getIs_subscribe().equalsIgnoreCase("0") && cd.getIs_free().equalsIgnoreCase("1"))
            {
                viewHolder.btn_buy.setText("Free");
                viewHolder.btn_buy.setBackgroundColor(((context.getResources()).getColor(R.color.green)));
            }
            else
            {
                viewHolder.btn_buy.setText("Purchase @Rs"+cd.getAmount());
            }
            viewHolder.tv_date.setText(cd.getMonth()+" "+cd.getYear());
        }
        String imgUrl = cd.getImages().get(0);
//        Log.d("url....",imgUrl);

        if(cd.getId()!=null)
        {
            if(cd.getIs_free().equalsIgnoreCase("1")||cd.getIs_subscribe().equalsIgnoreCase("1"))
            {

            }
            else {
                viewHolder.btn_buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.chkselectcategory(cd, "buy");
                        // viewHolder.bind(cd, "buy", listener);
                    }
                });
            }

            viewHolder.btn_read.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.chkselectcategory(cd, "Read");
                    }
                });
            }
        try {
//            if(i%3==0)
//            imgUrl="";
            if(imgUrl!=null) {
                if (imgUrl.length() > 0) {
                    viewHolder.imageViewCompat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.chkselectcategory( cd,"read");
                        }
                    });

                    imageDownload(context, imgUrl, viewHolder);
                }else
                {
                    viewHolder.imageViewCompat.setVisibility(View.GONE);
                }
            }else
            {
                viewHolder.imageViewCompat.setVisibility(View.GONE);
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
    }

    public  void imageDownload(Context ctx, String url, final ViewHolder viewHolder){

       /* Picasso.with(ctx).load(Uri.parse(url)).fit()
                .transform(new RoundedCornersTransformation(10, 0))
                //.placeholder(R.drawable.progress_animation)
                    .placeholder(R.drawable.loaderimagezoom)
//                    .error(R.drawable.ic_error)
                .into(viewHolder.imageViewCompat, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.homeprogress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });*/
//            Picasso.with(ctx).load(Uri.parse(url)).fit()
//                    .transform(new RoundedCornersTransformation(10, 0))
//                .placeholder(R.drawable.progress_animation)
////                    .placeholder(R.drawable.product_default)
////                    .error(R.drawable.ic_error)
//                    .into(viewHolder.imageViewCompat);
////        }
//        else {
////        http://igroms.ebizorders.com/assets/uploads/post_images//abcd.jpg
            if (url.lastIndexOf("/") >= 0) {
                String imagename = url.substring(url.lastIndexOf("/") + 1, url.length());
//        String imagename =url.replace("http://igroms.ebizorders.com/assets/uploads/post_images//","");
                File file = new File(getFileName(imagename));
                if (!file.exists()) {
                    Picasso.with(ctx).load(url).fit()
                            .transform(new RoundedCornersTransformation(10, 0))
//                .placeholder(R.drawable.progress_animation)
//                            .placeholder(R.drawable.product_default)
//                            .error(R.drawable.ic_error)
                            .into(viewHolder.imageViewCompat, new Callback() {
                                @Override
                                public void onSuccess() {
                                    viewHolder.homeprogress.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {

                                }
                            });
                    try {
                        Picasso.with(ctx)
                                .load(url)
                                .into(getTarget(getFileName(imagename)));
                    } catch (Exception ex) {
                        ex.getMessage();
                    }
                } else {
//            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            viewHolder.imgmain.setImageBitmap(myBitmap);
                    Picasso.with(ctx).load(new File(getFileName(imagename))).fit()
                            .transform(new RoundedCornersTransformation(10, 0))
//                .placeholder(R.drawable.progress_animation)
//                            .placeholder(R.drawable.product_default)
//                            .error(R.drawable.ic_error)
                            .into(viewHolder.imageViewCompat, new Callback() {
                                @Override
                                public void onSuccess() {
                                    viewHolder.homeprogress.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {

                                }
                            });
                }
            }
//        }
    }


        public  String getFileName(String fileName) {
            File file = new File(Environment.getExternalStorageDirectory().getPath(), "cosmicgrace");
            if (!file.exists()) {
                file.mkdirs();
            }
            String uriSting = (file.getAbsolutePath() + "/" + fileName);
            return uriSting;

        }
        //target to save
        private Target getTarget(final String imagename){
            Target target = new Target(){

                @Override
                public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            //  File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + imagename);
                            try {

                                File file = new File(imagename);
                                if (file.exists()) {
                                    // file.delete();
                                }else {
//                                file.createNewFile();
//                                FileOutputStream fileoutputstream = new FileOutputStream(file);
//                                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
//                                bitmap.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream);
//                                fileoutputstream.write(bytearrayoutputstream.toByteArray());
//                                fileoutputstream.close();

                                    file.createNewFile();
                                    FileOutputStream ostream = new FileOutputStream(file);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                                    ostream.flush();
                                    ostream.close();
                                }
//


                            } catch (IOException e) {
                                Log.e("IOException", e.getLocalizedMessage());
                            }
                        }
                    }).start();

                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };
            return target;
        }
public void notifychanges(ArrayList<HomeAnnouncements> larrayList_announcements)
{
    arrayList_announcements=larrayList_announcements;

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
        Log.d("itemnumber..","-----"+arrayList_announcements.size());
        if(arrayList_announcements!=null)
        {
            return arrayList_announcements.size();
        }
        else {
            return 0;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_read;
        Button btn_buy;
        ImageView imageViewCompat;
        TextView tv_date,tv_header;
        RelativeLayout rl_buyandview;
        ProgressBar homeprogress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_read=(Button) itemView.findViewById(R.id.btn_read);
            btn_buy=(Button) itemView.findViewById(R.id.btn_buy);
            imageViewCompat=itemView.findViewById(R.id.iv_announcements);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_header=itemView.findViewById(R.id.tv_header);
            rl_buyandview=itemView.findViewById(R.id.rl_buyandview);
            homeprogress=itemView.findViewById(R.id.homeprogress);
        }

        public void bind(final HomeAnnouncements cd,final String isRead,  final IssueandAnnoucementAdapter.vaccinationselectlistener listener) {
            imageViewCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        listener.chkselectcategory(cd, isRead);

                }
            });

            btn_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        listener.chkselectcategory(cd, isRead);

                }
            });

            btn_read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        listener.chkselectcategory(cd, isRead);

                }
            });
        }


    }
}
