package com.umass.elena;

import com.arkondata.slothql.cypher.GraphPath;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsRoute;
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



@RestController
@RequestMapping("google-maps")
public class GoogleMapsController {

  private final GoogleMapsService googleMapsService;

  public GoogleMapsController(GoogleMapsService googleMapsService) {
    this.googleMapsService = googleMapsService;
  }

//   private static double heuristic(String vertex) {
//     // Implement your heuristic function here like manhattan distance
//     return 0.0;
// }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/routes")
  public DirectionsRoute[] getRoutes(@RequestParam String source,
                                                 @RequestParam String destination,
                                                 @RequestParam Integer x) throws IOException, InterruptedException, ApiException {

    var routes = googleMapsService.getRoutes(source, destination, x);
// possibles paths - distance, elevation
    
  Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
  for (int i = 0; i < routes.length; i++) {
      DirectionsRoute route = routes[i];
      for(int j=0; j< route.legs[0].steps.length; j++){
          String dest = String.valueOf(route.legs[0].steps[j].endLocation);
          String src = String.valueOf(route.legs[0].steps[j].startLocation);
          Long weight = route.legs[0].steps[j].distance.inMeters;

          if (!graph.containsVertex(src)){
              graph.addVertex(src);
          }
          if (!graph.containsVertex(dest)) {
              graph.addVertex(dest);
          }
          DefaultWeightedEdge edge = graph.addEdge(src, dest);
          graph.setEdgeWeight(edge, weight);
      }
    }


    // Dijkstra code
    DijkstraShortestPath<String, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);
    GraphPath<String, DefaultWeightedEdge> path = shortestPath.getPath("21.02318860,79.06078510", "20.59348680,78.96281250");
    if (path != null) {
        System.out.println("Shortest Path: " + path.getVertexList());
        System.out.println("Shortest Path Weight: " + path.getWeight());
    } else {
        System.out.println("No path found");
    }

    //bellman ford code
    BellmanFordShortestPath<String, DefaultWeightedEdge> bellmanFord = new BellmanFordShortestPath<>(graph);
    String sourceVertex = "21.02318860,79.06078510";
    String destinationVertex = "20.59348680,78.96281250";
    GraphPath<String, DefaultWeightedEdge> shortestPath_bellman = bellmanFord.getPath(sourceVertex, destinationVertex);
    if (shortestPath_bellman != null) {
        System.out.println("Shortest Path Bellman: " + shortestPath_bellman.getVertexList());
        System.out.println("Shortest Path Weight: " + shortestPath_bellman.getWeight());
    } else {
        System.out.println("No path found");
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

  return routes;

}
}
