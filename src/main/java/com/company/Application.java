package com.company;

import com.company.graph.*;
import com.company.graph.serialization.GraphSerializerService;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

public class Application {

    public static void main(String[] args) {
        var graphSerializer = new GraphSerializerService();

        var graphService = new FileGraphService(new File("./src/main/resources/graph.txt"), graphSerializer);

        var graphNode1 = new GraphNode(1, 1);
        var graphNode2 = new GraphNode(2, 2);
        var graphNode3 = new GraphNode(3, 3);

        graphNode1.getNeighbours().add(graphNode2);
        graphNode1.getNeighbours().add(graphNode3);
        graphNode3.getNeighbours().add(graphNode2);

        var graph = new Graph(2, Set.of(graphNode1, graphNode2, graphNode3));
        graphService.save(graph);

        var graphOptional = graphService.findById(1);
        var graphIds = graphService.listIds();

        System.out.println(Arrays.toString(graphIds.toArray()));
    }
}
