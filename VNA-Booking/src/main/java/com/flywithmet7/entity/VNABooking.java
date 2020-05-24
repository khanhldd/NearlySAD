package com.flywithmet7.entity;

import java.util.List;

public class VNABooking {
    private Integer id;
    private List<Object> tickets;

    public VNABooking(Integer id, List<Object> tickets) {
        this.id = id;
        this.tickets = tickets;
    }

    public VNABooking() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Object> getTickets() {
        return tickets;
    }

    public void setTickets(List<Object> tickets) {
        this.tickets = tickets;
    }
}
