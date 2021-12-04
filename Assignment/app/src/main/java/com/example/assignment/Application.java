package com.example.assignment;

public class Application {
    private String idApplication, idSupervisor, idStaffLogin, dateStart, dateEnd, reason, note, state, sumDay;

    public Application() {
    }

    public Application(String idApplication, String idSupervisor, String idStaffLogin, String dateStart, String dateEnd, String reason, String note,
                       String state, String sumDay) {
        this.idApplication = idApplication;
        this.idSupervisor = idSupervisor;
        this.idStaffLogin = idStaffLogin;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.reason = reason;
        this.note = note;
        this.state = state;
        this.sumDay = sumDay;
    }

    public String getSumDay() {
        return sumDay;
    }

    public void setSumDay(String sumDay) {
        this.sumDay = sumDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIdStaffLogin() {
        return idStaffLogin;
    }

    public void setIdStaffLogin(String idStaffLogin) {
        this.idStaffLogin = idStaffLogin;
    }

    public String getIdApplication() {
        return idApplication;
    }

    public void setIdApplication(String idApplication) {
        this.idApplication = idApplication;
    }

    public String getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(String idSupervisor) {
        this.idSupervisor = idSupervisor;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
