package com.example.assignment;

public class Staff {
    private String staffId;
    private String password;
    private String staffName;
    private String phongBan;
    private String email;
    private String phone;
    private String chucvu;

    public Staff() {

    }

    public Staff(String id, String name, String password, String phong_ban, String email, String phone, String chucvu) {
        this.staffName = name;
        this.password = password;
        this.phongBan = phong_ban;
        this.staffId = id;
        this.email = email;
        this.phone = phone;
        this.chucvu = chucvu;

    }

    public String getStaffName() {
        return staffName;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getchucvu() {
        return chucvu;
    }


    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }


}
