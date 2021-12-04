package com.example.assignment;

public class AttendData {
    private String month;
    private int dayOff;

    public AttendData() {
    }

    public AttendData(String month, int dayOff) {
        this.month = month;
        this.dayOff = dayOff;
    }

    public String getMonth() {
        return month;
    }

    public int getDayOff() {
        return dayOff;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDayOff(int dayOff) {
        this.dayOff = dayOff;
    }
}
