package com.hunter.naiie.model;

public class ServiceList {

    public String service_name;
    public String service_price;

    public ServiceList(String service_name, String service_price) {
        this.service_name = service_name;
        this.service_price = service_price;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_price() {
        return service_price;
    }

    public void setService_price(String service_price) {
        this.service_price = service_price;
    }
}
