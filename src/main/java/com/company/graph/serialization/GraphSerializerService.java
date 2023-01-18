package com.company.graph.serialization;

import com.company.graph.Graph;
import com.company.graph.GraphNode;

import java.util.*;
import java.util.regex.Pattern;

public class GraphSerializerService {

    private static final Character SECTION_SEPARATOR = ' ';
    private static final Character SECTION_BEGIN = '[';
    private static final Character SECTION_END = ']';
    private static final Character ELEMENT_BEGIN = '{';
    private static final Character ELEMENT_END = '}';
    private static final Character ELEMENT_PATH_SEPARATOR = ',';

    private static final Pattern ELEMENT_PATTERN = Pattern.compile("\\{(.*?)}");

    private static final int GRAPH_ID_SECTION = 0;
    private static final int GRAPH_NODES_SECTION = 1;
    private static final int GRAPH_EDGES_SECTION = 2;

    public String serialize(Graph graph) {
        var stringBuilder = new StringBuilder();

        stringBuilder.append(serializeId(graph))
                .append(SECTION_SEPARATOR)
                .append(serializeNodes(graph))
                .append(SECTION_SEPARATOR)
                .append(serializeEdges(graph))
                .append(System.lineSeparator());

        return stringBuilder.toString();
    }

    public Graph deserialize(String serialisedGraph) {
        var serialisedGraphSections = serialisedGraph.split(String.valueOf(SECTION_SEPARATOR));
        var graphId = Integer.parseInt(serialisedGraphSections[GRAPH_ID_SECTION]);
        var serializedNodes = serialisedGraphSections[GRAPH_NODES_SECTION];
        var serializedEdges = serialisedGraphSections[GRAPH_EDGES_SECTION];

        List<String> serializedNodesList = getElements(serializedNodes);

        Map<Integer, GraphNode> nodesIdMap = new HashMap<>();
        for (String serializedNode : serializedNodesList) {
            String[] serializedNodeAttributes = serializedNode.split(String.valueOf(ELEMENT_PATH_SEPARATOR));

            int id = Integer.parseInt(serializedNodeAttributes[0]);
            int value = Integer.parseInt(serializedNodeAttributes[1]);

            var graphNode = new GraphNode(id, value);
            nodesIdMap.put(id, graphNode);
        }

        List<String> edgesList = getElements(serializedEdges);
        for (String serializedEdge : edgesList) {
            String[] node = serializedEdge.split(String.valueOf(ELEMENT_PATH_SEPARATOR));
            int fromId = Integer.parseInt(node[0]);
            int toId = Integer.parseInt(node[1]);

            GraphNode graphNodeFrom = nodesIdMap.get(fromId);
            GraphNode graphNodeTo = nodesIdMap.get(toId);
            graphNodeFrom.getNeighbours().add(graphNodeTo);
        }

        return new Graph(graphId, new HashSet<>(nodesIdMap.values()));
    }

    public Integer getGraphId(String serializedGraph) {
        String[] serialisedGraphSections = serializedGraph.split(String.valueOf(SECTION_SEPARATOR));

        return Integer.parseInt(serialisedGraphSections[GRAPH_ID_SECTION]);
    }

    private List<String> getElements(String elementsTuple) {
        List<String> result = new ArrayList<>();
        var matcher = ELEMENT_PATTERN.matcher(elementsTuple);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }

    private String serializeId(Graph graph) {
        var stringBuilder = new StringBuilder();
        stringBuilder.append(graph.getId());
        return stringBuilder.toString();
    }

    private String serializeNodes(Graph graph) {
        var stringBuilder = new StringBuilder();

        stringBuilder.append(SECTION_BEGIN);
        for (GraphNode node : graph.getNodes()) {
            stringBuilder.append(ELEMENT_BEGIN)
                    .append(node.getId())
                    .append(ELEMENT_PATH_SEPARATOR)
                    .append(node.getValue())
                    .append(ELEMENT_END);
        }
        stringBuilder.append(SECTION_END);

        return stringBuilder.toString();
    }

    private String serializeEdges(Graph graph) {
        var stringBuilder = new StringBuilder();

        stringBuilder.append(SECTION_BEGIN);
        for (GraphNode node : graph.getNodes()) {
            for (GraphNode neighbour : node.getNeighbours()) {
                stringBuilder.append(ELEMENT_BEGIN)
                        .append(node.getId())
                        .append(ELEMENT_PATH_SEPARATOR)
                        .append(neighbour.getId())
                        .append(ELEMENT_END);
            }
        }
        stringBuilder.append(SECTION_END);

        return stringBuilder.toString();
    }
}
