package com.anm.bslndmag.API;

import com.anm.bslndmag.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
//       public final static String env="P";
    public final static String env="U";

    public static final String produrl="http://bslndmag.ebizorders.com/"; //production url
    public static final String uaturl="http://bslndmag.ebizorders.com/"; //UAT url
    public static final String termsandcondition="http://bslndmag.ebizorders.com/"+ "term-and-conditions"; //UAT url
    public static final String privacypolicy="http://bslndmag.ebizorders.com/"+ "privacy-policy"; //UAT url
    public static final String offlinestoreuaturl="http://35.154.186.145/"; //UAT url

    public static PostService  postService=null;
    public static PostService getApi()
    {
        if(postService==null)
        {
            String url;
                if(env.equalsIgnoreCase("P"))
                    url=produrl;
                else
                    url= uaturl;
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                httpClient.addInterceptor(logging);
            }

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit=new Retrofit.Builder().baseUrl(url)
//                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient.build())
                .build();
            postService=retrofit.create(PostService.class);
        }
        return postService;
    }

    public static PostService getApioffline()
    {
        postService=null;
        if(postService==null)
        {
            String url;
            if(env.equalsIgnoreCase("P"))
                url=produrl;
            else
                url= offlinestoreuaturl;
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                httpClient.addInterceptor(logging);
            }

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit=new Retrofit.Builder().baseUrl(url)
//                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient.build())
                    .build();
            postService=retrofit.create(PostService.class);
        }
        return postService;
    }
}
