package com.flywithmet7.entity;

import java.util.List;

/*
 * public class AllTickets {
 * 
 * public List<Object> tickets;
 * 
 * public List<Object> getTickets() { return tickets; }
 * 
 * public void getTickets(List<Object> tickets) { this.tickets = tickets; }
 * 
 * public AllTickets() {
 * 
 * } public List<Object> gettickets(){ return tickets; }
 * 
 * public void setTickets(List<Object> tickets) { this.tickets = tickets; }
 * 
 * 
 * }
 */
public class AllTickets {
   
    private List<Object> tickets;

    public AllTickets(List<Object> tickets) {
        
        this.tickets = tickets;
    }

    public AllTickets() {
    }

	
	public List<Object> getTickets() {
		return tickets;
	}

	public void setTickets(List<Object> tickets) {
		this.tickets = tickets;
	}
    
}