package com.umass.elena;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import java.io.IOException;
import org.springframework.stereotype.Service;
import com.google.maps.model.TravelMode;

/**
 */
@Service
public class GoogleMapsService {

  public DirectionsRoute[] getRoutes(String source, String destination, Integer x)
      throws IOException, InterruptedException, ApiException {

    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey("AIzaSyDFWQUqp3hlC5sR9YetyObvoTGuyIudBtY")
        .build();

    DirectionsResult result = DirectionsApi.newRequest(context)
                                           .alternatives(true)
                                           .origin(source)
                                           .destination(destination)
                                           .await();

    System.out.println(source);
    System.out.println(destination);
    System.out.println(456);

    System.out.println(result.routes);

    return result.routes;
  }

}
