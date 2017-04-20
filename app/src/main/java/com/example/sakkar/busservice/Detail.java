package com.example.sakkar.busservice;

/**
 * Created by Sakkar on 4/20/2017.
 */

public class Detail {
    String bus_name,route;
    float ticket;

    public Detail() {
    }

    public Detail(String bus_name, float ticket, String route) {
        this.bus_name = bus_name;
        this.route = route;
        this.ticket = ticket;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public float getTicket() {
        return ticket;
    }

    public void setTicket(float ticket) {
        this.ticket = ticket;
    }
}
