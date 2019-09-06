package emerge.project.mrsolution_admin_micro.services.api;




import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;



import emerge.project.mrsolution_admin_micro.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by Himanshu on 8/24/2017.
 */

public class ApiClient {

    public static final String baseUrl = BuildConfig.API_BASE_URL;


    private static Retrofit retrofit = null;




    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(provideOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

/*
    private static OkHttpClient provideOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        return client;
    }*/


   private static OkHttpClient provideOkHttpClient() {

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(20, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(20, TimeUnit.SECONDS);

        return okhttpClientBuilder.build();
    }


}
