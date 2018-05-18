package ru.mtuci.smart_aquarium.network;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AquariumService {

    //@Headers("Content-Type: json")
    @GET("/event")
    Call<List<Event>> listEvents();

    @GET("/temperature")
    Call<List<Temperature>> listTemperatures();

    @GET("/actual")
    Call<Status> status();

    @GET("/")
    Call<ResponseBody> settings();

    @POST("/settings")
    Call<ResponseBody> postSettings(@Body RequestBody body);
}
