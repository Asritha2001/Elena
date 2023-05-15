package com.umass.elena;

import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsRoute;
import java.io.IOException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bahadirmemis
 */
@RestController
public class GoogleMapsController {

  private final GoogleMapsService googleMapsService;

  public GoogleMapsController(GoogleMapsService googleMapsService) {
    this.googleMapsService = googleMapsService;
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/test")
  public DirectionsRoute[] getMailSendRequestDto(@RequestParam String source,
                                                 @RequestParam String destination,
                                                 @RequestParam Integer x) throws IOException, InterruptedException, ApiException {

    var routes = googleMapsService.getRoutes(source, destination, x);

    System.out.println(5);

    return routes;

    //GeoApiContext context = new GeoApiContext.Builder()
    //    .apiKey("AIzaSyA-UCoOovnOluxqPTNjCLQOPndIUa0Wf5A")
    //    .build();
    //GeocodingResult[] results = GeocodingApi.geocode(context,
    //                                                 "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
    //Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //System.out.println(gson.toJson(results[0].addressComponents));
    //
    //// Invoke .shutdown() after your application is done making requests
    //context.shutdown();

    //String url = "";
    //RestTemplate restTemplate = new RestTemplate();
    //
    //ResponseEntity<MailSendRequestDto> responseEntity = restTemplate.getForEntity(url, MailSendRequestDto.class);
    //
    //return responseEntity.getBody();
  }
}
