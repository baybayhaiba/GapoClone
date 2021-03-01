package com.example.gapoclone.Model;

public class Trend {
    private String description;
    private String urlImageTrend;

    //Order gom tinh toan trong firebase !


    public Trend(String description, String urlImageTrend) {
        this.description = description;
        this.urlImageTrend = urlImageTrend;
    }

    public Trend() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImageTrend() {
        return urlImageTrend;
    }

    public void setUrlImageTrend(String urlImageTrend) {
        this.urlImageTrend = urlImageTrend;
    }

    @Override
    public String toString() {
        return "Trend{" +
                "description='" + description + '\'' +
                ", urlImageTrend='" + urlImageTrend + '\'' +
                '}';
    }
}
