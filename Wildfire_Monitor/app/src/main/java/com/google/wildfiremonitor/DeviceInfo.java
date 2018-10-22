package com.google.wildfiremonitor;

public class DeviceInfo {
    private String deviceID;
    private String devLocation;
    private String temperature;
    private String smoke;
    private String fire;
    private String status;

    public DeviceInfo(){

    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDevLocation() {
        return devLocation;
    }

    public void setDevLocation(String devLocation) {
        this.devLocation = devLocation;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getFire() {
        return fire;
    }

    public void setFire(String fire) {
        this.fire = fire;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
