package ru.mtuci.smart_aquarium.network;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Temperature {

    @SerializedName("dt")
    String datatime;
    @SerializedName("temp")
    float temperature;

    public float getTemperature() {
        return temperature;
    }

    public Date getDatatime() {
        return new Date(Long.parseLong(datatime) * 1000);
    }
}
