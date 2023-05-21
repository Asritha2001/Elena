package com.umass.elena;

import com.arkondata.slothql.cypher.GraphPath;
import com.google.maps.ElevationApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.ElevationResult;
import com.google.maps.model.LatLng;

import java.io.IOException;

import org.springframework.web.bind.annotation.*;
import org.assertj.core.api.PathAssert;
import org.jgrapht.Graph;
import org.jgrapht.graph.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;



@RestController
@RequestMapping("google-maps")
public class GoogleMapsController {

  private final GoogleMapsService googleMapsService;

  public GoogleMapsController(GoogleMapsService googleMapsService) {
    this.googleMapsService = googleMapsService;
  }

  public static List<String> get_elevation(DirectionsRoute route) throws InterruptedException, ApiException, IOException {
    List<String> elevations = new ArrayList<>();
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    String apiKey = "AIzaSyA-UCoOovnOluxqPTNjCLQOPndIUa0Wf5A";
    GeoApiContext context = new GeoApiContext.Builder()
            .apiKey(apiKey)
            .build();
    DirectionsStep[] steps = route.legs[0].steps;
    double maxElevation = 0;
    double minElevation = Double.POSITIVE_INFINITY;
    for (int i=0; i<steps.length; i++){
        double latitude = steps[i].endLocation.lat;
        double longitude = steps[i].endLocation.lng;
        LatLng location = new LatLng(latitude, longitude);
        ElevationResult[] results = ElevationApi.getByPoints(context, location).await();
        double elevation = results[0].elevation;
        maxElevation = Math.max(maxElevation, elevation);
        minElevation = Math.min(minElevation, elevation);
    }
    elevations.add(decimalFormat.format(minElevation));
    elevations.add(decimalFormat.format(maxElevation));
    return elevations;
}

public static List<Object> get_x_routes(List<DirectionsRoute> routes, Integer x) throws InterruptedException, ApiException, IOException {
      List<Object> candidates = new ArrayList<>();
    for (int i=0; i<routes.size(); i++){
        List<Object> triple = new ArrayList<>();
        DirectionsRoute r = routes.get(i);
        triple.add(r);
        triple.add(r.legs[0].distance.inMeters);
        triple.add(get_elevation(r));
        candidates.add(triple);
    }

    List<Object> results = new ArrayList<>();
    long min = Long.MAX_VALUE;
    int index = 0;
    for (int i=0; i<candidates.size(); i++){
        long c1 = (long) ((List<Object>) candidates.get(i)).get(1);
        if(c1 < min){
            min = c1;
            index = i;
        }
    }
    long c2 = (long) ((List<Object>) candidates.get(index)).get(1);
    for (int j=0; j<candidates.size(); j++){
        if(j != index){
            long c1 = (long) ((List<Object>) candidates.get(j)).get(1);
            if(c1<= (c2*(100+x)/100)){
                results.add(candidates.get(j));
            }
        }
    }
    return candidates;
}

//   private static double heuristic(String vertex) {
//     // Implement your heuristic function here like manhattan distance
//     return 0.0;
// }


  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/routes")
//<<<<<<< HEAD
  public List<Object> getRoutes(@RequestParam String source,
                                @RequestParam String destination,
                                @RequestParam Integer x) throws IOException, InterruptedException, ApiException {

      DirectionsRoute[] routes = googleMapsService.getRoutes(source, destination, x);
      //System.out.println(routes);
      List<DirectionsRoute> r = new ArrayList<>();
      for (int e=0; e< routes.length; e++){
          r.add(routes[e]);
      }
// possibles paths - distance, elevation
    
  Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
  for (int i = 0; i < routes.length; i++) {
      DirectionsRoute route = routes[i];
//      System.out.println("for loop");
      //System.out.println(route);
      try {
          for(int j=0; j< route.legs[0].steps.length; j++){
              String dest = String.valueOf(route.legs[0].steps[j].endLocation);
              String src = String.valueOf(route.legs[0].steps[j].startLocation);
              Long weight = route.legs[0].steps[j].distance.inMeters;
//          System.out.println("for loop 2");
              if (!graph.containsVertex(src)){
                  graph.addVertex(src);
              }
              if (!graph.containsVertex(dest)) {
                  graph.addVertex(dest);
              }
              DefaultWeightedEdge edge = graph.addEdge(src, dest);
              graph.setEdgeWeight(edge, weight);
          }


      } catch (NullPointerException e) {
          // Exception handling code
          // Handle the NullPointerException
          // Log the error or display an appropriate error message
//          e.printStackTrace(); // Print the stack trace for debugging purposes

          // Optionally, you can provide a custom error message or perform specific error handling logic
          // For example:
          // System.out.println("NullPointerException occurred: " + e.getMessage());
          // Show a user-friendly error message to the client or perform recovery actions
//=======
//          if (!graph.containsVertex(src)){
//              graph.addVertex(src);
//          }
//          if (!graph.containsVertex(dest)) {
//              graph.addVertex(dest);
//          }
//          boolean containsEdge = graph.containsEdge(src, dest);
//          if(!containsEdge){
//            DefaultWeightedEdge edge = graph.addEdge(src, dest);
//            graph.setEdgeWeight(edge, weight);
//          }
//>>>>>>> origin/main
      }

//      System.out.println("for loop 3");
    }
//    System.out.println("for loop 4");


    // Dijkstra code

//    DijkstraShortestPath<String, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);

//    GraphPath<String, DefaultWeightedEdge> path = shortestPath.getPath("21.02318860,79.06078510", "20.59348680,78.96281250");
//    if (path != null) {
//        System.out.println("Shortest Path: " + path.getVertexList());
//        System.out.println("Shortest Path Weight: " + path.getWeight());
//    } else {
//        System.out.println("No path found");
//    }
//
//    //bellman ford code
//    BellmanFordShortestPath<String, DefaultWeightedEdge> bellmanFord = new BellmanFordShortestPath<>(graph);
//    String sourceVertex = "21.02318860,79.06078510";
//    String destinationVertex = "20.59348680,78.96281250";
//    GraphPath<String, DefaultWeightedEdge> shortestPath_bellman = bellmanFord.getPath(sourceVertex, destinationVertex);
//    if (shortestPath_bellman != null) {
//        System.out.println("Shortest Path Bellman: " + shortestPath_bellman.getVertexList());
//        System.out.println("Shortest Path Weight: " + shortestPath_bellman.getWeight());
//    } else {
//        System.out.println("No path found");
//    }

    // AStarShortestPath<String, DefaultWeightedEdge> a_star = new AStarShortestPath<>(graph, heuristic(sourceVertex));

    // ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> paths = a_star.getPaths(sourceVertex);
    // GraphPath<String, DefaultWeightedEdge> shortestPath_astar = paths.getPath(destinationVertex);
    // if (shortestPath_astar != null) {
    //     System.out.println("Shortest path from " + sourceVertex + " to " + destinationVertex + ": " +
    //             shortestPath_astar.getVertexList() + ", Weight: " + shortestPath_astar.getWeight());
    // } else {
    //     System.out.println("No path found from " + sourceVertex + " to " + destinationVertex);
    // }


  return get_x_routes( r,x);

}


}
