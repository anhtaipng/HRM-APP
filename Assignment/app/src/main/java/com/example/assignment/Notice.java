package com.example.assignment;

public class Notice {
    private String idNotice;
    private String idStaffCreate;
    private String tennguoitao;
    private String bangtin;
    private String chude;
    private String noidung;
    private String ngaygiotao;

    public Notice(){};

    public Notice(String idNotice, String idStaffCreate, String tennguoitao, String bangtin, String chude, String noidung, String ngaygiotao){
        this.idNotice = idNotice;
        this.idStaffCreate = idStaffCreate;
        this.tennguoitao = tennguoitao;
        this.bangtin = bangtin;
        this.chude = chude;
        this.noidung = noidung;
        this.ngaygiotao = ngaygiotao;
    }

    public String getNgaygiotao() {
        return ngaygiotao;
    }

    public void setNgaygiotao(String ngaygiotao) {
        this.ngaygiotao = ngaygiotao;
    }

    public String getIdNotice() {
        return idNotice;
    }

    public String getIdStaffCreate() {
        return idStaffCreate;
    }

    public String getBangtin() {
        return bangtin;
    }

    public String getChude() {
        return chude;
    }

    public String getNoidung() {
        return noidung;
    }

    public String getTennguoitao() {
        return tennguoitao;
    }

    public void setIdNotice(String idNotice) {
        this.idNotice = idNotice;
    }

    public void setBangtin(String bangtin) {
        this.bangtin = bangtin;
    }

    public void setChude(String chude) {
        this.chude = chude;
    }

    public void setIdStaffCreate(String idStaffCreate) {
        this.idStaffCreate = idStaffCreate;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public void setTennguoitao(String tennguoitao) {
        this.tennguoitao = tennguoitao;
    }
}
