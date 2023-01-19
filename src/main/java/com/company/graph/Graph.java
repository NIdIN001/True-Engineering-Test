package com.company.graph;

import java.util.Set;

public class Graph {

    private final Integer id;

    private final Set<GraphNode> nodes;

    public Graph(Integer id, Set<GraphNode> nodeSet) {
        this.id = id;
        this.nodes = nodeSet;
    }

    public Integer getId() {
        return id;
    }

    public Set<GraphNode> getNodes() {
        return nodes;
    }
}
