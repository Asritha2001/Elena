package com.umass.elena;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import java.io.IOException;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class GoogleMapsService {

  public DirectionsRoute[] getRoutes(String source, String destination, Integer x)
      throws IOException, InterruptedException, ApiException {

    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey("AIzaSyA-UCoOovnOluxqPTNjCLQOPndIUa0Wf5A")
        .build();

    DirectionsResult result = DirectionsApi.newRequest(context)
                                           .alternatives(true)
                                           .origin(source)
                                           .destination(destination)
                                           .await();

    //
    System.out.println(456);

    System.out.println(result.routes);

    return result.routes;
  }
}
