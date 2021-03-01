package com.example.gapoclone.Model;

public class CustomTimestamp {

    private String Title;
    private long Time;

    public CustomTimestamp(String title, long time) {
        Title = title;
        Time = time;
    }

    @Override
    public String toString() {
        return "CustomTimestamp{" +
                "Title='" + Title + '\'' +
                ", Time=" + Time +
                '}';
    }

    public String getTitle() {return Title;}
    public void setTitle(String title) {this.Title = title;}
    public long getTime() {return Time;}
    public void setTime(long time) {this.Time = time;}
}

