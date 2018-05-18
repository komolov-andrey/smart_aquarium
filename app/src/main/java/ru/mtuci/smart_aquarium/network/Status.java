package ru.mtuci.smart_aquarium.network;

public class Status {
    String food;
    String light;
    String pump;
    float temperature;

    public boolean isFoodOn() {
        return food.equals("on");
    }

    public boolean isLightOn() {
        return light.equals("on");
    }

    public boolean isPumpOn() {
        return pump.equals("on");
    }

    public float getTemperature() {
        return temperature;
    }
}
