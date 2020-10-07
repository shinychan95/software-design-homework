package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Tree that delegates to a given instance of Graph. This class
 * is a wrapper of a MutableGraph instance that enforces the Tree invariant.
 * NOTE: you should NOT add more member variables to this class.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class DelegateTree<N extends Comparable<N>> implements MutableTree<N> {

    /**
     * A root vertex of this tree; {@code null} for an empty tree.
     */
    private @Nullable N root;

    /**
     * The underlying graph of this tree
     */
    private final @NotNull MutableGraph<N> delegate;

    /**
     * A map assigning a depth to each vertex in this tree
     */
    private final @NotNull Map<N, Integer> depthMap;

    /**
     * Creates an empty tree that delegates to a given graph.
     *
     * @param emptyGraph an empty graph
     * @throws IllegalArgumentException if {@code emptyGraph} is not empty
     */
    public DelegateTree(@NotNull MutableGraph<N> emptyGraph) {
        if (!emptyGraph.getVertices().isEmpty())
            throw new IllegalArgumentException();

        delegate = emptyGraph;
        depthMap = new HashMap<>();
    }

    @Override
    public @NotNull Optional<N> getRoot() {
        // TODO: implement this
        if(depthMap.isEmpty()) return Optional.empty();
        return Optional.of(root);
    }

    @Override
    public int getDepth(@NotNull N vertex) {
        // TODO: implement this
        if (depthMap.isEmpty()) throw new IllegalStateException();
        if (!delegate.containsVertex(vertex)) throw new IllegalArgumentException();
        return depthMap.get(vertex);
    }

    @Override
    public int getHeight() {
        // TODO: implement this
        if (depthMap.isEmpty()) throw new IllegalStateException();
        int max = 0;
        for (N n : depthMap.keySet())
            max = (depthMap.get(n) > max) ? depthMap.get(n) : max;
        return max;
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        // TODO: implement this
        return depthMap.containsKey(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        // TODO: implement this
        if (depthMap.isEmpty()) {
            root = vertex;
            depthMap.put(vertex, 0);
            return delegate.addVertex(vertex);
        }
        return false;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        // TODO: implement this
        if(!depthMap.containsKey(vertex)) return false;

        if (depthMap.get(vertex) == 0) {
            root = null;
            depthMap.clear();
            delegate.removeVertex(vertex);
            return true;
        }

        Set<N> children = new HashSet<>();
        Set<N> check = new HashSet<>();
        Set<N> next = new HashSet<>();

        children.add(vertex);
        check.add(vertex);

        while (!check.isEmpty()) {
            for (N cn : check) {
                for (N tn : delegate.getTargets(cn)) {
                    children.add(tn);
                    next.add(tn);
                }
            }
            check.clear();
            for (N n : next) {
                check.add(n);
            }
            next.clear();
        }

        for (N n: children) depthMap.remove(n);
        delegate.removeVertex(vertex);

        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        return delegate.containsEdge(source, target);
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(!depthMap.containsKey(source)) return false;
        if(depthMap.containsKey(target)) return false;
        depthMap.put(target, depthMap.get(source) + 1);
        delegate.addEdge(source, target);
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(!depthMap.containsKey(target)) return false;
        if(!delegate.getSources(target).contains(source)) return false;
        removeVertex(target);
        return true;
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        // TODO: implement this
        return delegate.getSources(target);
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        // TODO: implement this
        return delegate.getTargets(source);
    }

    @Override
    public @NotNull Set<N> getVertices() {
        // TODO: implement this
        return delegate.getVertices();
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        // TODO: implement this
        return delegate.getEdges();
    }

    /**
     * Checks if all class invariants hold for this object
     *
     * @return true if the representation of this tree is valid
     */
    boolean checkInv() {
        // TODO: implement this
        Set<N> check = new HashSet<>();
        Set<N> next = new HashSet<>();

        int depth = 0;

        check.add(root);

        while(!check.isEmpty()) {
            for (N n : check) {
                if (depthMap.get(n) != depth) return false;
                next.addAll(delegate.getTargets(n));
            }

            check.clear();
            for (N n : next) {
                check.add(n);
            }
            next.clear();

            depth++;
        }

        return true;
    }

    /**
     * Provides a human-readable string representation for the abstract value of the tree
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return String.format("[root: %s, vertex: {%s}, edge: {%s}]",
                root,
                getVertices().stream().map(N::toString).collect(Collectors.joining(", ")),
                getEdges().stream().map(Edge::toString).collect(Collectors.joining(", ")));
    }
}
