package edu.postech.csed332.homework3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Tree, where each vertex has a reference to its parent node but
 * no references to its children.
 *
 * @param <N> type of vertices, which must be immutable and comparable
 */
public class ParentPointerTree<N extends Comparable<N>> implements MutableTree<N> {

    private static class Node<V> {
        final @Nullable V parent;
        final int depth;

        Node(@Nullable V parent, int depth) {
            this.parent = parent;
            this.depth = depth;
        }
    }

    /**
     * A root vertex of this tree; {@code null} for an empty tree.
     */
    private @Nullable N root;

    /**
     * A map assigning to each vertex a pair of a parent reference and a depth. The parent
     * of the root is {@code null}. For example, a tree with four vertices {v1, v2, v3, v4}
     * and three edges {(v1,v2), (v1,v3), (v2,v4)}, where v1 is the root, is represented by
     * the map {v1 |-> (null,0), v2 |-> (v1,1), v3 |-> (v1,1), v4 |-> (v2,2)}.
     */
    private final @NotNull SortedMap<N, Node<N>> nodeMap;

    /**
     * Creates an empty parent pointer tree.
     */
    public ParentPointerTree() {
        this.root = null;
        this.nodeMap = new TreeMap<>();
    }

    @Override
    public @NotNull Optional<N> getRoot() {
        // TODO: implement this
        if (root == null) return Optional.empty();
        return Optional.of(root);
    }

    @Override
    public int getDepth(@NotNull N vertex) {
        // TODO: implement this
        if (root == null) throw new IllegalStateException();
        if (!nodeMap.containsKey(vertex)) throw new IllegalArgumentException();

        return nodeMap.get(vertex).depth;
    }

    @Override
    public int getHeight() {
        // TODO: implement this
        if (root == null) throw new IllegalStateException();
        int max = 0;
        for (N n : nodeMap.keySet())
            max = (nodeMap.get(n).depth > max) ? nodeMap.get(n).depth : max;
        return max;
    }

    @Override
    public boolean containsVertex(@NotNull N vertex) {
        // TODO: implement this
        return nodeMap.containsKey(vertex);
    }

    @Override
    public boolean addVertex(@NotNull N vertex) {
        // TODO: implement this
        if (root == null) {
            root = vertex;
            nodeMap.put(vertex, new Node<>(null, 0));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeVertex(@NotNull N vertex) {
        // TODO: implement this
        if(!nodeMap.containsKey(vertex)) return false;

        if (vertex == root) {
            root = null;
            nodeMap.clear();
            return true;
        }

        Set<N> children = new HashSet<N>();

        for(N n : nodeMap.keySet())
            if(nodeMap.get(n).parent == vertex)
                children.add(n);

        for(N child : children)
            removeVertex(child);

        nodeMap.remove(vertex);
        return true;
    }

    @Override
    public boolean containsEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(nodeMap.containsKey(target) && source == nodeMap.get(target).parent) return true;
        return false;
    }

    @Override
    public boolean addEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(!nodeMap.containsKey(source)) return false;
        if(nodeMap.containsKey(target)) return false;
        nodeMap.put(target, new Node<>(source, nodeMap.get(source).depth + 1));
        return true;
    }

    @Override
    public boolean removeEdge(@NotNull N source, @NotNull N target) {
        // TODO: implement this
        if(!nodeMap.containsKey(target)) return false;
        if(nodeMap.get(target).parent != source) return false;
        removeVertex(target);
        return true;
    }

    @Override
    public @NotNull Set<N> getSources(N target) {
        // TODO: implement this
        Set<N> s = new HashSet<>();
        if (target == root) {
            return s;
        }
        s.add(nodeMap.get(target).parent);
        return s;
    }

    @Override
    public @NotNull Set<N> getTargets(N source) {
        // TODO: implement this
        Set<N> s = new HashSet<>();
        for(N n : nodeMap.keySet())
            if(nodeMap.get(n).parent == source)
                s.add(n);
        return s;
    }

    @Override
    public @NotNull Set<N> getVertices() {
        // TODO: implement this
        return nodeMap.keySet();
    }

    @Override
    public @NotNull Set<Edge<N>> getEdges() {
        // TODO: implement this
        Set<Edge<N>> s = new HashSet<>();
        for(N n : nodeMap.keySet()){
            N p = nodeMap.get(n).parent;
            Edge<N> e = new Edge<>(p, n);
            s.add(e);
        }
        return s;
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
        int count = 1;
        check.add(root);

        while(!check.isEmpty()) {
            for(N n : nodeMap.keySet()){
                if (check.contains(nodeMap.get(n).parent)) {
                    next.add(n);
                    count++;
                }
            }
            check.clear();
            for (N n : next) {
                check.add(n);
            }
            next.clear();
        }
        if (count != nodeMap.size()) return false;

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
