package ru.mtuci.smart_aquarium.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AquariumServiceFactory {

    private static final OkHttpClient CLIENT;


    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);
        //httpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        //httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        //httpClient.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT = httpClient.build();
    }

    public static AquariumService create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://89.169.35.184")
                .addConverterFactory(GsonConverterFactory.create())
                .client(CLIENT)
                .build();

        return retrofit.create(AquariumService.class);
    }
}
