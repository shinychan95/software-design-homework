package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Graph with an adjacency list representation.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */

/**
 * abstract function의 경우 상속 받는 MutableGraph 및 Graph interface에 나열되어 있는 함수들에 해당한다.
 * 인터페이스 내에는 함수가 어떻게 기능하는지 그리고 param, return 에 대한 명세가 적혀 있다.
 * 각 함수 주석에 언급한 것들이 모두 충족하도록 AdjacencyListGraph class 내에 함수를 concrete하게 정의하였다.
 *
 * class invariant의 경우, homework3.md에 적힌 Graph와 MutableGraph의 invariant에 해당한다.
 */
public class AdjacencyListGraph<N extends Comparable<N>> implements MutableGraph<N> {

    /**
     * A map from vertices to the sets of their adjacent vertices. For example, a graph
     * with four vertices {v1, v2, v3, v4} and four edges {(v1,v1), (v1,v2), (v3,v1), (v3,v2)}
     * is represented by the map {v1 |-> {v1,v2}, v2 |-> {}, v3 -> {v1,v2}, v4 -> {}}.
     */
    private final @NotNull SortedMap<N, SortedSet<N>> adjMap;

    /**
     * Creates an empty graph
     */
    public AdjacencyListGraph() {
        adjMap = new TreeMap<>();
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        // TODO: implement this
        return adjMap.containsKey(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        // TODO: implement this
        if (adjMap.containsKey(vertex)) return false;
        SortedSet<N> ss = new TreeSet<>();
        adjMap.put(vertex, ss);
        return true;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        // TODO: implement this
        if (!adjMap.containsKey(vertex)) return false;
        adjMap.remove(vertex);
        for(N n : adjMap.keySet())
            if (adjMap.get(n).contains(vertex))
                adjMap.get(n).remove(vertex);
        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if (adjMap.containsKey(source) && adjMap.get(source).contains(target)) return true;
        return false;
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if (adjMap.containsKey(source) && adjMap.get(source).contains(target)) return false;

        if (!adjMap.containsKey(source)) adjMap.put(source, new TreeSet<>());
        if (!adjMap.containsKey(target)) adjMap.put(target, new TreeSet<>());

        adjMap.get(source).add(target);

        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if (adjMap.containsKey(source) && adjMap.get(source).contains(target)) {
            adjMap.get(source).remove(target);
            return true;
        }
        return false;
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        // TODO: implement this
        Set<N> s = new HashSet<>();
        for(N n : adjMap.keySet())
            if (adjMap.get(n).contains(target))
                s.add(n);
        return s;
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        // TODO: implement this
        if (!adjMap.containsKey(source)) return new HashSet<>();
        return adjMap.get(source);
    }

    @Override
    public @NotNull Set<N> getVertices() {
        return Collections.unmodifiableSet(adjMap.keySet());
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        return adjMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(n -> new Edge<>(entry.getKey(), n)))
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if all class invariants hold for this object.
     *
     * @return true if the representation of this graph is valid
     */
    boolean checkInv() {
        // TODO: implement this
        for(N sn : adjMap.keySet())
            for (N tn : adjMap.get(sn))
                if (!adjMap.containsKey(tn)) return false;
        return true;
    }

    /**
     * Provides a human-readable string representation for the abstract value of the graph
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return String.format("[vertex: {%s}, edge: {%s}]",
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }
}
