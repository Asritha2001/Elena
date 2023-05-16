package com.umass.elena;

import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsRoute;
import java.io.IOException;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("google-maps")
public class GoogleMapsController {

  private final GoogleMapsService googleMapsService;

  public GoogleMapsController(GoogleMapsService googleMapsService) {
    this.googleMapsService = googleMapsService;
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/routes")
  public DirectionsRoute[] getRoutes(@RequestParam String source,
                                                 @RequestParam String destination,
                                                 @RequestParam Integer x) throws IOException, InterruptedException, ApiException {

    var routes = googleMapsService.getRoutes(source, destination, x);

    System.out.println(5);

    return routes;
  }
}
