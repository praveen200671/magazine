package com.anm.bslndmag;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
//import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.anm.bslndmag.Adapter.IssueandAnnoucementAdapter;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import uk.co.senab.photoview.PhotoViewAttacher;

//import static com.google.android.material.internal.DescendantOffsetUtils.matrix;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AnnouncementDetails extends AppCompatActivity implements View.OnTouchListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    ImageView iv_announcement;
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private ProgressBar homeprogress;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_announcement_details);
//        homeprogress=findViewById(R.id.homeprogress);
        mVisible = true;
       // getActionBar().hide();
        Bundle bundle=getIntent().getExtras();
        iv_announcement=findViewById(R.id.iv_announcement);
        iv_announcement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                iv_announcement.setLayoutParams(parms);
                PhotoViewAttacher pAttacher;
//                iv_announcement.setFitsSystemWindows(true);
                pAttacher = new PhotoViewAttacher(iv_announcement);
                pAttacher.setMinimumScale(Matrix.MSCALE_X);
//        pAttacher.setMinimumScale(Matrix.MSCALE_Y);
                pAttacher.setAllowParentInterceptOnEdge(true);
                pAttacher.update();
                return false;
            }
        });
//        iv_announcement.setImage(ImageSource.resource(R.drawable.splash_screen));
        //scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        String imgUrl = bundle.getString("imageurl");
//        Log.d("url....",imgUrl);

        try {
//            if(i%3==0)
//            imgUrl="";
            if(imgUrl!=null) {
                if (imgUrl.length() > 0) {
                    imageDownload(this, imgUrl, iv_announcement);
                }else
                {

                }
            }else
            {

            }
        }catch (Exception e)
        {
            e.getMessage();
        }

    }
    public  void imageDownload(Context ctx, String url, ImageView imageView){

        Picasso.with(ctx).load(Uri.parse(url)).fit()
                .transform(new RoundedCornersTransformation(10, 10))
//               // .placeholder(R.drawable.progress_animation)
//
                    .placeholder(R.drawable.loaderimagezoom)
                    .error(R.drawable.ic_error)
                .into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                //homeprogress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


    }


    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
//        ImageView view = (ImageView) v;
//        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        float scale;
//
//        dumpEvent(event);
        // Handle touch events here...

//        switch (event.getAction() & MotionEvent.ACTION_MASK)
//        {
//            case MotionEvent.ACTION_DOWN:   // first finger down only
//                savedMatrix.set(matrix);
//                start.set(event.getX(), event.getY());
//                Log.d(TAG, "mode=DRAG"); // write to LogCat
//                mode = DRAG;
//                break;
//
//            case MotionEvent.ACTION_UP: // first finger lifted
//
//            case MotionEvent.ACTION_POINTER_UP: // second finger lifted
//
//                mode = NONE;
//                Log.d(TAG, "mode=NONE");
//                break;
//
//            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
//
//                oldDist = spacing(event);
//                Log.d(TAG, "oldDist=" + oldDist);
//                if (oldDist > 5f) {
//                    savedMatrix.set(matrix);
//                    midPoint(mid, event);
//                    mode = ZOOM;
//                    Log.d(TAG, "mode=ZOOM");
//                }
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//
//                if (mode == DRAG)
//                {
//                    matrix.set(savedMatrix);
//                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
//                }
//                else if (mode == ZOOM)
//                {
//                    // pinch zooming
//                    float newDist = spacing(event);
//                    Log.d(TAG, "newDist=" + newDist);
//                    if (newDist > 5f)
//                    {
//                        matrix.set(savedMatrix);
//                        scale = newDist / oldDist; // setting the scaling of the
//                        // matrix...if scale > 1 means
//                        // zoom in...if scale < 1 means
//                        // zoom out
//                        matrix.postScale(scale, scale, mid.x, mid.y);
//                    }
//                }
//                break;
//        }

//        view.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event)
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }
}
