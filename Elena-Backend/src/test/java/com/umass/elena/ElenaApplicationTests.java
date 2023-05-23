package com.umass.elena;

import com.google.maps.errors.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElenaApplicationTests{

	@Test
	public void testGetElevation() throws InterruptedException, ApiException, IOException {
		// Create mock DirectionsRoute object
		DirectionsRoute route = new DirectionsRoute();
		route.legs = new DirectionsLeg[1];
		route.legs[0] = new DirectionsLeg();
		route.legs[0].steps = new DirectionsStep[1];
		route.legs[0].steps[0] = new DirectionsStep();
		route.legs[0].steps[0].endLocation = new LatLng(40.748817, -73.985428);

		// Call method
		List<String> elevations = GoogleMapsController.get_elevation(route);

		// Check output
		Assertions.assertNotNull(elevations);
		Assertions.assertEquals(2, elevations.size());
	}

	@Test
	public void testGetXRoutes() throws InterruptedException, ApiException, IOException {
		// Create mock list of DirectionsRoute objects
		List<DirectionsRoute> routes = new ArrayList<>();
		DirectionsRoute route = new DirectionsRoute();
		route.legs = new DirectionsLeg[1];
		route.legs[0] = new DirectionsLeg();
		route.legs[0].steps = new DirectionsStep[1];
		route.legs[0].steps[0] = new DirectionsStep();
		route.legs[0].steps[0].endLocation = new LatLng(40.748817, -73.985428);
		routes.add(route);

		// Call method
		List<Object> results = GoogleMapsController.get_x_routes(routes, 10);

		// Check output
		Assertions.assertNotNull(results);
		Assertions.assertFalse(results.isEmpty());
	}
}

