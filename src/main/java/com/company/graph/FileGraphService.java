package com.company.graph;

import com.company.graph.excetion.GraphServiceException;
import com.company.graph.serialization.GraphSerializerService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileGraphService implements GraphService {

    private static final String ERROR_MESSAGE = "Ошибка работы с файлом";

    private final File file;
    private final GraphSerializerService graphSerializer;

    public FileGraphService(File file, GraphSerializerService graphSerializer) {
        this.file = file;
        this.graphSerializer = graphSerializer;
    }

    @Override
    public Optional<Graph> findById(Integer id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String serializedGraph;

            while ((serializedGraph = readSerializedGraph(reader)) != null) {
                var graphId = graphSerializer.getGraphId(serializedGraph);

                if (graphId.equals(id)) {
                    var graph = graphSerializer.deserialize(serializedGraph);
                    return Optional.of(graph);
                }
            }
        } catch (IOException e) {
            throw new GraphServiceException(ERROR_MESSAGE);
        }

        return Optional.empty();
    }

    @Override
    public void save(Graph graph) {
        if (isGraphContainsInFile(graph)) {
            throw new GraphServiceException("Граф с ID = " + graph.getId() + " уже существует");
        }

        var serializedGraph = graphSerializer.serialize(graph);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(serializedGraph);
        } catch (IOException e) {
            throw new GraphServiceException(ERROR_MESSAGE);
        }
    }

    @Override
    public List<Integer> listIds() {
        List<Integer> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String serializedGraph;

            while ((serializedGraph = readSerializedGraph(reader)) != null) {
                result.add(graphSerializer.getGraphId(serializedGraph));
            }
        } catch (IOException e) {
            throw new GraphServiceException(ERROR_MESSAGE);
        }

        return result;
    }

    private boolean isGraphContainsInFile(Graph graph) {
        return listIds().stream()
                .anyMatch(graphId -> graph.getId().equals(graphId));
    }

    private String readSerializedGraph(BufferedReader reader) throws IOException {
        return reader.readLine();
    }
}
