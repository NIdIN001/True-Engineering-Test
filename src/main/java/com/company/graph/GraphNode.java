package com.company.graph;

import java.util.HashSet;
import java.util.Set;

public class GraphNode {

    private Integer id;

    private Integer value;

    private Set<GraphNode> neighbours;

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

    public Set<GraphNode> getNeighbours() {
        return neighbours;
    }
}
