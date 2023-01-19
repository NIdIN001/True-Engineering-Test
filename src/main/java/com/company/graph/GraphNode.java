package com.company.graph;

import java.util.HashSet;
import java.util.Set;

public class GraphNode {

    private final Integer id;

    private final Integer value;

    private final Set<GraphNode> neighbours;

    public GraphNode(Integer id, Integer value) {
        this.id = id;
        this.value = value;
        this.neighbours = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public Integer getValue() {
        return value;
    }

    public void addNeighbour(GraphNode neighbour) {
        neighbours.add(neighbour);
    }

    public Set<GraphNode> getNeighbours() {
        return neighbours;
    }
}
