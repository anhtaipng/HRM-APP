package com.example.assignment;

public class Attendance {
    private String idStaff, checkin, checkout;

    public Attendance(){}
    public Attendance(String idStaff, String checkin, String checkout) {
        this.idStaff = idStaff;
        this.checkin = checkin;
        this.checkout = checkout;
    }


    public String getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(String idStaff) {
        this.idStaff = idStaff;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }
}

