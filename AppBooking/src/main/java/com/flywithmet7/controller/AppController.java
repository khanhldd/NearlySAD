package com.flywithmet7.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.lang.annotation.Retention;
import java.text.ParseException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.flywithmet7.entity.AllTickets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/")
public class AppController {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;

	@RequestMapping("/")
	public String home() {
		// This is useful for debugging
		// When having multiple instance of gallery service running at different ports.
		// We load balance among them, and display which instance received the request.
		return "Hello from VNA Booking running at port: " + env.getProperty("local.server.port");
	}

	@HystrixCommand(fallbackMethod = "myfallback")
	@Retryable(value = { RuntimeException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	@RequestMapping("/tickets/{airLineFirm}")
	public AllTickets getAirLineFirm(@PathVariable String airLineFirm) {
		AllTickets list = new AllTickets();
		String[] hang = airLineFirm.split(",");
		JSONArray list2 = new JSONArray();
		for (int i = 0; i < hang.length; i++) {
			String result = restTemplate.getForObject("http://" + hang[i] + "-gateway/" + hang[i] + "/tickets/",
					String.class);

			try {
				@SuppressWarnings("unchecked")
				Object objFirm = new JSONParser().parse(result);
				JSONObject objPlane = (JSONObject) objFirm;
				JSONArray arrP1 = (JSONArray) objPlane.get("tickets");
				JSONObject plane = new JSONObject();
				plane.put(hang[i], arrP1);
				list2.add(plane);

			} catch (Exception e) {
				continue;
			}
		}
		list.setTickets((List<Object>) list2);
		return list;

	}

	@Recover
	public String recovergetAirlineFirm(Throwable th) {
		System.out.print("Error class : " + th.getClass().getName());

		return null;
	}

	public AllTickets myfallback(String airLineFirm) {
		AllTickets list = new AllTickets();
		String[] hang = airLineFirm.split(",");
		JSONArray list2 = new JSONArray();
		for (int i = 0; i < hang.length; i++) {
			JSONParser jsoParser = new JSONParser();
			Object object;
			JSONObject jsonOject = new JSONObject();
			try {
				String result = restTemplate.getForObject("http://" + hang[i] + "-gateway/" + hang[i] + "/tickets/",
						String.class);
				object = jsoParser.parse(result);
			} catch (Exception e) {
				object = null;
			}
			if (object == null) {
				jsonOject.put(hang[i], hang[i] + " Error <3");
			} else {
				JSONObject objPlane = (JSONObject) object;
				JSONArray arrP1 = (JSONArray) objPlane.get("tickets");
				jsonOject.put(hang[i], arrP1);

			}
			list2.add(jsonOject);
		}
		list.setTickets((List<Object>) list2);
		return list;
	}

	/*
	 * @RequestMapping("/tickets/{date}/{from}/{to}/{adult}/{children}/{baby}")
	 * public AppBooking getBooking(@PathVariable final String date,
	 * 
	 * @PathVariable final String from,
	 * 
	 * @PathVariable final String to,
	 * 
	 * @PathVariable final Integer adult,
	 * 
	 * @PathVariable final Integer children,
	 * 
	 * @PathVariable final Integer baby) {
	 * 
	 * AppBooking booking = new AppBooking(); List<Object> rsTickets =
	 * restTemplate.getForObject("http://bba-ticket-service/tickets/" + date + "/" +
	 * from + "/" + to + "/" + adult + "/" + children + "/" + baby + "/",
	 * List.class); booking.setTickets(rsTickets); return booking; }
	 */
	// -------- Admin Area --------
	// This method should only be accessed by users with role of 'admin'
	// We'll add the logic of role based auth later
	@RequestMapping("/admin")
	public String homeAdmin() {
		return "This is the admin area of Gallery service running at port: " + env.getProperty("local.server.port");
	}
}
