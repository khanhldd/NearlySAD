package com.flywithmet7.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flywithmet7.entity.BBATicket;

@RestController
@RequestMapping("/")
public class BBATicketController {
	@Autowired
	private Environment env;

	@RequestMapping("/")
	public String home() {
		// This is useful for debugging
		// When having multiple instance of image service running at different ports.
		// We load balance among them, and display which instance received the request.
		return "Hello from Ticket Service running at port: " + env.getProperty("local.server.port");
	}
		
	@RequestMapping("/tickets")
	public List<BBATicket> getTickets() {
		List<BBATicket> tickets = Arrays.asList(
			new BBATicket("bba", 1, "A", "HCM", "DN", "22-2-2020", "2A", "1", "2", "3"),
			new BBATicket("bba", 1, "A", "HCM", "HN", "22-2-2020", "2A", "1", "2", "3"),
			new BBATicket("bba", 1, "A", "HCM", "HN", "20-2-2020", "2A", "1", "2", "3"));
		return tickets;
	}
	
	@RequestMapping("/tickets/{date}/{from}/{to}/{adult}/{children}/{baby}")
	public List<BBATicket> getTickets(@PathVariable final String date, 
			                  @PathVariable final String from, 
			                  @PathVariable final String to, 
			                  @PathVariable final String adult, 
			                  @PathVariable final String children, 
			                  @PathVariable final String baby) {
								
		List<BBATicket> tickets = Arrays.asList(
				new BBATicket("bba", 1, "A", "HCM", "DN", "22-2-2020", "2A", "1", "2", "3"),
				new BBATicket("bba", 1, "A", "HCM", "HN", "22-2-2020", "2A", "1", "2", "3"),
				new BBATicket("bba", 1, "A", "HCM", "HN", "20-2-2020", "2A", "1", "2", "3"));
		List<BBATicket> tc = new ArrayList<BBATicket>();
		try {
			for(BBATicket subTicket : tickets) {
				if(subTicket.getDate().equalsIgnoreCase(date) == true &&
						subTicket.getFrom().equalsIgnoreCase(from) == true &&
						subTicket.getTo().equalsIgnoreCase(to) == true &&
						subTicket.getAdult().equalsIgnoreCase(adult) == true &&
						subTicket.getChildren().equalsIgnoreCase(children) ==true) {
					tc.add(subTicket);
				}
						}
			
		} catch (Exception e) {
			tc.add(null);
		}
		return tc;
}
}
