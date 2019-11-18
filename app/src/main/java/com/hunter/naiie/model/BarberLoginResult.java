package com.hunter.naiie.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BarberLoginResult implements Serializable {

    private String name;
    private String onm;
    private String mono;
    private String em;
    private String pserv;
    private String rdate;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnm() {
        return onm;
    }

    public void setOnm(String onm) {
        this.onm = onm;
    }

    public String getMono() {
        return mono;
    }

    public void setMono(String mono) {
        this.mono = mono;
    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public String getPserv() {
        return pserv;
    }

    public void setPserv(String pserv) {
        this.pserv = pserv;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
