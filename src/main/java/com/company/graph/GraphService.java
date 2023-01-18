package com.company.graph;

import java.util.List;
import java.util.Optional;

public interface GraphService {

    Optional<Graph> findById(Integer id);

    void save(Graph graph);

    List<Integer> listIds();
}
