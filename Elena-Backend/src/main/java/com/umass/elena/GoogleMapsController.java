package com.umass.elena;

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
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.*;
import org.jgrapht.graph.*;



@RestController
@RequestMapping("google-maps")  // requesting to map with google maps
public class GoogleMapsController {   // this class has all the funcions required

  private final GoogleMapsService googleMapsService;

  public GoogleMapsController(GoogleMapsService googleMapsService) {
    this.googleMapsService = googleMapsService;   // talkin with google maps api
  }
// this funciton gets the elevation given a route
  public static List<String> get_elevation(DirectionsRoute route) throws InterruptedException, ApiException, IOException {
    List<String> elevations = new ArrayList<>();
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    String apiKey = process.env.GOOGLE_API_KEY;
    GeoApiContext context = new GeoApiContext.Builder()
            .apiKey(apiKey)
            .build();
    DirectionsStep[] steps = route.legs[0].steps;
    double maxElevation = 0;
    double minElevation = Double.POSITIVE_INFINITY;
    for (int i=0; i<steps.length; i++){
        double latitude = steps[i].endLocation.lat;  // getting the lat and long of the route
        double longitude = steps[i].endLocation.lng;
        LatLng location = new LatLng(latitude, longitude);
        ElevationResult[] results = ElevationApi.getByPoints(context, location).await(); // using the elevations api from google maps
        double elevation = results[0].elevation;
        maxElevation = Math.max(maxElevation, elevation);
        minElevation = Math.min(minElevation, elevation);
    }
    elevations.add(decimalFormat.format(minElevation));
    elevations.add(decimalFormat.format(maxElevation));
    return elevations; // returning both minimum and maximum elevation
}
// this funciotn gets all the routes possible from google maps api
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
    return candidates; // returning the distance elevation and route.
}

//   private static double heuristic(String vertex) {
//     // Implement your heuristic function here like manhattan distance
//     return 0.0;
// }


  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/routes")
  public List<Object> getRoutes(@RequestParam String source,
                                @RequestParam String destination,
                                @RequestParam Integer x) throws IOException, InterruptedException, ApiException {

      DirectionsRoute[] routes = googleMapsService.getRoutes(source, destination, x);
      //System.out.println(routes);
      List<DirectionsRoute> r = new ArrayList<>();
      for (int e=0; e< routes.length; e++){
          r.add(routes[e]);
      }
      String source_vertex = String.valueOf(r.get(1).legs[0].startLocation);
      String destination_vertex = String.valueOf(r.get(1).legs[0].endLocation);
// possibles paths - distance, elevation
    // creating a graph
  Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
  for (int i = 0; i < routes.length; i++) {
      DirectionsRoute route = routes[i];
      for(int j=0; j< route.legs[0].steps.length; j++){
          String dest = String.valueOf(route.legs[0].steps[j].endLocation);
          String src = String.valueOf(route.legs[0].steps[j].startLocation);
          Long weight = route.legs[0].steps[j].distance.inMeters;

          if (!graph.containsVertex(src)){ // checking if the graph already has vertex before adding it.
              graph.addVertex(src);
          }
          if (!graph.containsVertex(dest)) {
              graph.addVertex(dest);
          }
          boolean containsEdge = graph.containsEdge(src, dest);
          if(!containsEdge){
            DefaultWeightedEdge edge = graph.addEdge(src, dest);
            graph.setEdgeWeight(edge, weight);
          }
      }
    }


    // Dijkstra code
    //System.out.println("************************");
    DijkstraShortestPath<String, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);
    List <Object> algo_paths = new ArrayList<>();
    if(graph.containsVertex(source_vertex) && graph.containsVertex(destination_vertex)) {
        GraphPath<String, DefaultWeightedEdge> dijkstra_path = shortestPath.getPath(source_vertex, destination_vertex);
        if (dijkstra_path != null) {
            System.out.println("Shortest Path: " + dijkstra_path.getVertexList());
            System.out.println("Shortest Path Weight: " + dijkstra_path.getWeight());
            algo_paths.add(dijkstra_path.getVertexList()); // getting the shortest path
            algo_paths.add(dijkstra_path.getWeight());

        } else {
            List<String> emptyList = new ArrayList<>();
            algo_paths.addAll(emptyList);
            algo_paths.add(0.0);
        }
    }
    else{
        List<String> emptyList = new ArrayList<>();
        algo_paths.addAll(emptyList);
        algo_paths.add(0.0);
    }

   //bellman ford code
   BellmanFordShortestPath<String, DefaultWeightedEdge> bellmanFord = new BellmanFordShortestPath<>(graph);
   if(graph.containsVertex(source_vertex) && graph.containsVertex(destination_vertex)) {
        GraphPath<String, DefaultWeightedEdge> shortestPath_bellman = bellmanFord.getPath(source_vertex, destination_vertex );
        if (shortestPath_bellman != null) {
            System.out.println("Shortest Path Bellman: " + shortestPath_bellman.getVertexList());
            System.out.println("Shortest Path Weight: " + shortestPath_bellman.getWeight());
            algo_paths.add(shortestPath_bellman.getVertexList());
            algo_paths.add(shortestPath_bellman.getWeight());
        } else {
            System.out.println("No path found");
            List<String> emptyList = new ArrayList<>();
            algo_paths.addAll(emptyList);
            algo_paths.add(0.0);
        }
    }
    else{
        List<String> emptyList = new ArrayList<>();
        algo_paths.addAll(emptyList);
        algo_paths.add(0.0);
    }

    // AStarShortestPath<String, DefaultWeightedEdge> a_star = new AStarShortestPath<>(graph, heuristic(sourceVertex));

    // ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> paths = a_star.getPaths(sourceVertex);
    // GraphPath<String, DefaultWeightedEdge> shortestPath_astar = paths.getPath(destinationVertex);
    // if (shortestPath_astar != null) {
    //     System.out.println("Shortest path from " + sourceVertex + " to " + destinationVertex + ": " +
    //             shortestPath_astar.getVertexList() + ", Weight: " + shortestPath_astar.getWeight());
    // } else {
    //     System.out.println("No path found from " + sourceVertex + " to " + destinationVertex);
    // }
    

    List<Object> all_routes = new ArrayList<>();
    all_routes.add(algo_paths);
    all_routes.add(get_x_routes(r, x));

  return all_routes; // sending all the routes possible along with the ones from algorithms to the front end.

}
}
