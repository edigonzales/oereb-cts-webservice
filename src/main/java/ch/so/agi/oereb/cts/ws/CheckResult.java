package ch.so.agi.oereb.cts.ws;

import java.time.LocalDateTime;

public class CheckResult {
    private String adescription;
    private int statuscode;
    private String message;
    private String classname;
    private boolean success;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private double processingtimesecs;
    public String getAdescription() {
        return adescription;
    }
    public void setAdescription(String adescription) {
        this.adescription = adescription;
    }
    public int getStatuscode() {
        return statuscode;
    }
    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getClassname() {
        return classname;
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public LocalDateTime getStarttime() {
        return starttime;
    }
    public void setStarttime(LocalDateTime starttime) {
        this.starttime = starttime;
    }
    public LocalDateTime getEndtime() {
        return endtime;
    }
    public void setEndtime(LocalDateTime endtime) {
        this.endtime = endtime;
    }
    public double getProcessingtimesecs() {
        return processingtimesecs;
    }
    public void setProcessingtimesecs(double processingtimesecs) {
        this.processingtimesecs = processingtimesecs;
    }
}
