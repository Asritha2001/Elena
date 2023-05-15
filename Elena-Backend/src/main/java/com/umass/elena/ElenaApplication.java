package com.umass.elena;

import com.google.maps.model.DirectionsRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.umass.elena.GoogleMapsController;
import com.umass.elena.GoogleMapsService;


@SpringBootApplication
public class ElenaApplication {

	public static void main(String[] args) {
		System.out.println(44);
		GoogleMapsService googleMapsService = new GoogleMapsService();
		GoogleMapsController controller = new GoogleMapsController(googleMapsService);
		SpringApplication.run(ElenaApplication.class, args);
	}

}
