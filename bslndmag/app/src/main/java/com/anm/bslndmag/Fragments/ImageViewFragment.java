package com.anm.bslndmag.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.anm.bslndmag.API.Api;
import com.anm.bslndmag.Adapter.IssueandAnnoucementAdapter;
import com.anm.bslndmag.HomeActivity;
import com.anm.bslndmag.Model.HomeAnnouncements;
import com.anm.bslndmag.Model.MagazineResponse;
import com.anm.bslndmag.Model.SubscribePlanRequest;
import com.anm.bslndmag.Privacypolicy_Tnc;
import com.anm.bslndmag.R;
import com.anm.bslndmag.ReadMagazine;
import com.anm.bslndmag.Session.LoginSession;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Response;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.app.Activity.RESULT_OK;

public class ImageViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private static final String FRAG_RESOURCE_ID = "resource_id";
    private static final String FLAG_TOTALRECORD = "totalcount";
    private static final String FLAG_isread = "isread";
    private static final String FLAG_POSITION = "position";
    private String  imgUrl;
    int position,total_count;
    private ImageView imageView;
    HomeAnnouncements homeAnnouncements;
    private Button btn_buy;
    LoginSession ls;
    boolean isread;

    public ImageViewFragment() {
        // Required empty public constructor
    }
    public static ImageViewFragment newInstance(HomeAnnouncements cd, int position,boolean isread) {
        ImageViewFragment fragment = new ImageViewFragment();
        Bundle args = new Bundle();
        args.putString(FRAG_RESOURCE_ID, cd.getImages().get(position));
        args.putInt(FLAG_TOTALRECORD, cd.getImages().size());
        args.putBoolean(FLAG_isread, isread);
        args.putInt(FLAG_POSITION, position);
        args.putSerializable("CD", cd);

        fragment.setArguments(args);
        return fragment;
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        scaleGestureDetector.onTouchEvent(motionEvent);
//        return true;
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         HomeAnnouncements cd=null;
        ls=new LoginSession(getActivity());
        if (getArguments() != null) {
            imgUrl = getArguments().getString(FRAG_RESOURCE_ID);
            position = getArguments().getInt(FLAG_POSITION);
            total_count = getArguments().getInt(FLAG_TOTALRECORD);
            isread = getArguments().getBoolean(FLAG_isread);
             cd=(HomeAnnouncements)getArguments().getSerializable("CD");
        }
        View view = inflater.inflate(R.layout.item_page, container, false);
         imageView = (ImageView) view.findViewById(R.id.ivImage);
         imageView.setOnTouchListener(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View view, MotionEvent motionEvent) {

//                 RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                 imageView.setLayoutParams(parms);
                 PhotoViewAttacher pAttacher;
//                 imageView.setFitsSystemWindows(true);
                 pAttacher = new PhotoViewAttacher(imageView);
                 pAttacher.setMinimumScale(Matrix.MSCALE_X);
//        pAttacher.setMinimumScale(Matrix.MSCALE_Y);
                 pAttacher.setAllowParentInterceptOnEdge(true);
                 pAttacher.update();
//                 pAttacher.set
                 return false;
             }
         });

//        PhotoViewAttacher pAttacher;
//        pAttacher = new PhotoViewAttacher(imageView);
//        pAttacher.update();
        btn_buy = (Button) view.findViewById(R.id.btn_buy);
//         imageView.setOnTouchListener(new View.OnTouchListener() {
//             @Override
//             public boolean onTouch(View view, MotionEvent motionEvent) {
//                 scaleGestureDetector.onTouchEvent(motionEvent);
//                 return true;
//             }
//         });
        if(position==total_count-1)
        {
            btn_buy.setText("Purchase @Rs"+cd.getAmount());
            if(cd.getIs_subscribe().equalsIgnoreCase("1"))

            btn_buy.setVisibility(View.GONE);
        }
        else
        {
            btn_buy.setVisibility(View.GONE);
        }
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeAnnouncements cd=(HomeAnnouncements)getArguments().getSerializable("CD");
                send_MagazineDetails_Request(cd,isread);
            }
        });
//        imageView.setImageResource(id);
//        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        try {
//            if(i%3==0)
//            imgUrl="";
            if(imgUrl!=null) {
                if (imgUrl.length() > 0) {
                    imageDownload(getContext(), imgUrl, imageView);
                }else
                {

                }
            }
        }catch (Exception e)
        {
            e.getMessage();
        }
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("resumecalled","resumecalled");
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parms.setMargins(10,10,10,10);
        imageView.setFitsSystemWindows(true);
        imageView.refreshDrawableState();
        imageView.setLayoutParams(parms);
    }

    private void send_MagazineDetails_Request(final HomeAnnouncements cd, final boolean isread) {

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        try {
//hardcode...
            final SubscribePlanRequest loginRequest =new SubscribePlanRequest("2",//ls.getStr(getString(R.string.language)),
                    cd.getId(),ls.getdeviceid(),
                    "android",
                    "magazine"
            );
            String token=ls.get_auth_token();
            if(!token.toLowerCase().startsWith("bearer"))
            {
                token=""+token;
            }
            Call<MagazineResponse> logincall = Api.getApi().Magazine(loginRequest,token);
            dialog.setCancelable(false);
            dialog.show();
            logincall.enqueue(new retrofit2.Callback<MagazineResponse>() {
                @Override
                public void onResponse(Call<MagazineResponse> call, Response<MagazineResponse> response) {
                    MagazineResponse loginres = response.body();
                    if(response.code()!=200)
                    {
                        if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                        {
                            Toasty.error(getActivity(), R.string.somethingwentwrong_english, Toasty.LENGTH_LONG).show();
                        }
                        else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                        {
                            Toasty.error(getActivity(), R.string.somethingwentwrong_hindi, Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(getActivity(), R.string.somethingwentwrong_gurmukhi, Toasty.LENGTH_LONG).show();
                        }
                    }else {
                        ;//message();//body();
                        boolean responsestatus = loginres.isStatus();//message();//body();
                        String responsemsg = loginres.getMessage();//body();
                        if (responsestatus == false) {
                            Toasty.error(getActivity(), responsemsg, Toasty.LENGTH_LONG).show();
                            dialog.dismiss();
                            return;
                        } else {
//                            Toasty.success(SubscriptionPlans.this, loginres.getMessage(), Toasty.LENGTH_LONG).show();
                            HomeAnnouncements loginResponseData = loginres.getData();
                            if(loginres.getStatusCode()==200) {



//                                if(isread) {
//                                    Intent returnIntent = new Intent();
//                                    getActivity().setResult(1299,returnIntent);
//                                    getActivity().finish();
//                                }
//                                else {
//                                    Intent returnIntent = new Intent();
//                                    getActivity().setResult(1199,returnIntent);
                                    getActivity().finish();
                                    ls.set_isneedRefresh("Y");
////                                    startActivity(intent);
//                                }
                            }
                           else if(loginResponseData.getUrl()!=null) {
                                ls.set_isneedRefresh("Y");
                                openWebViewtoSubscribe(loginResponseData.getUrl(),cd);
                            }
                            else
                            {
                                Toasty.error(getActivity(), responsemsg, Toasty.LENGTH_LONG).show();
                            }

                        }
                    }


                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<MagazineResponse> call, Throwable t) {
                    if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
                    {
                        Toasty.error(getActivity(), (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
                    }
                    else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
                    {
                        Toasty.error(getActivity(), (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toasty.error(getActivity(), (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });
        }catch (Exception ex)
        {
            ex.getMessage();
            if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("1"))
            {
                Toasty.error(getActivity(), (R.string.checkinternet_english), Toasty.LENGTH_LONG).show();
            }
            else if(ls.getStr(getString(R.string.language)).equalsIgnoreCase("2"))
            {
                Toasty.error(getActivity(), (R.string.checkinternet_hindi), Toasty.LENGTH_LONG).show();
            }
            else
            {
                Toasty.error(getActivity(), (R.string.checkinternet_gurmukhi), Toasty.LENGTH_LONG).show();
            }
//        dialog.dismiss();
        }

    }

    private void openWebViewtoSubscribe(String url,HomeAnnouncements cd) {
        Intent intent=new Intent(getActivity(), Privacypolicy_Tnc.class);
        intent.putExtra("istermsandconditions","magazine");
        intent.putExtra("url",url);
        intent.putExtra("homeannouncements",cd);
        startActivityForResult(intent,1099);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1099) {
                ls.set_isneedRefresh("y");
//                HomeAnnouncements cd = (HomeAnnouncements) data.getExtras().get("homeannouncements");
               // send_MagazineDetails_Request(cd, true);
                getActivity().finish();
            }

    }

    public  void imageDownload(Context ctx, String url, final ImageView imageView){

        Picasso.with(ctx).load(Uri.parse(url)).fit()
                .transform(new RoundedCornersTransformation(10, 0))
                //.placeholder(R.drawable.progress_animation)
                .placeholder(R.drawable.loaderimagezoom)
//                    .error(R.drawable.ic_error)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
//            Picasso.with(ctx).load(Uri.parse(url)).fit()
//                    .transform(new RoundedCornersTransformation(10, 0))
//                .placeholder(R.drawable.progress_animation)
////                    .placeholder(R.drawable.product_default)
////                    .error(R.drawable.ic_error)
//                    .into(viewHolder.imageViewCompat);
////        }
//        else {
////        http://igroms.ebizorders.com/assets/uploads/post_images//abcd.jpg
//            if (url.lastIndexOf("/") >= 0) {
//                String imagename = url.substring(url.lastIndexOf("/") + 1, url.length());
////        String imagename =url.replace("http://igroms.ebizorders.com/assets/uploads/post_images//","");
//                File file = new File(getFileName(imagename));
//                if (!file.exists()) {
//                    Picasso.with(ctx).load(url).fit()
//                            .transform(new RoundedCornersTransformation(10, 0))
////                .placeholder(R.drawable.progress_animation)
////                            .placeholder(R.drawable.product_default)
////                            .error(R.drawable.ic_error)
//                            .into(viewHolder.imgmain);
//                    try {
//                        Picasso.with(ctx)
//                                .load(url)
//                                .into(getTarget(getFileName(imagename)));
//                    } catch (Exception ex) {
//
//                    }
//                } else {
////            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
////            viewHolder.imgmain.setImageBitmap(myBitmap);
//                    Picasso.with(ctx).load(new File(getFileName(imagename))).fit()
//                            .transform(new RoundedCornersTransformation(10, 0))
////                .placeholder(R.drawable.progress_animation)
////                            .placeholder(R.drawable.product_default)
////                            .error(R.drawable.ic_error)
//                            .into(viewHolder.imgmain);
//                }
//            }
//        }
    }


//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
//            imageView.setScaleX(mScaleFactor);
//            imageView.setScaleY(mScaleFactor);
//            return true;
//        }
//    }
}
