package com.example.javamobilprogramlama.apis.historical;

public class HistoricalResponse {
    private String CurrentInterbankRate;
    private String CurrentInverseInterbankRate;
    private String Average;
    private HistoricalModel[] HistoricalPoints;
    public String getCurrentInterbankRate(){
        return this.CurrentInterbankRate;
    }
    public String getCurrentInverseInterbankRate(){
        return this.CurrentInverseInterbankRate;
    }
    public String getAverage(){
        return this.Average;
    }
    public HistoricalModel[] getHistoricalPoints(){
        return this.HistoricalPoints;
    }
    public void setCurrentInterbankRate(String currentInterbankRate){
        this.CurrentInterbankRate = currentInterbankRate;
    }
    public void setCurrentInverseInterbankRate(String currentInverseInterbankRate){
        this.CurrentInverseInterbankRate = currentInverseInterbankRate;
    }
    public void setAverage(String average){
        this.Average = average;
    }
    public void setHistoricalPoints(HistoricalModel[] historicalPoints){
        this.HistoricalPoints = historicalPoints;
    }
    public HistoricalResponse(HistoricalModel[] historicalPoints, String currentInterbankRate, String currentInverseInterbankRate, String  average){
        this.CurrentInterbankRate = currentInterbankRate;
        this.CurrentInverseInterbankRate = currentInverseInterbankRate;
        this.Average = average;
        this.HistoricalPoints = historicalPoints;
    }
}
