package com.example.javamobilprogramlama.apis.historical;

public class HistoricalModel {
    private String PointInTime;
    private String InterbankRate;
    public String getPointInTime(){
        return this.PointInTime;
    }
    public String getInterbankRate(){
        return this.InterbankRate;
    }

    public HistoricalModel(String pointInTime, String interbankRate) {
        this.PointInTime = pointInTime;
        this.InterbankRate = interbankRate;
    }
}
