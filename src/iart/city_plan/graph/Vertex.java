package iart.city_plan.graph;

import java.util.Objects;
import java.util.Vector;

public class Vertex<T> {
    private T info; // contents

    private boolean visited;          // auxiliary field used by dfs and bfs
    private int indegree;          // auxiliary field used by topsort
    private boolean processing;       // auxiliary field used by isDAG
    private double dist = 0;
    private Vertex<T> path = null;
    private int queueIndex = 0;        // required by MutablePriorityQueue

    public Vector<Edge<T>> adj;

    public Vertex(T info) {
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return Objects.equals(info, vertex.info);
    }


    public void setDist(double dist) {
        this.dist = dist;
    }

    public void addEdge(Vertex<T> dest, double weight){
        adj.add(new Edge<>(dest, weight));
    }

    public boolean removeEdgeTo(Vertex<T> d){
        for (Edge<T> e : this.adj) {
            if(e.getDest() == d){
               adj.remove(e);
               return true;
            }
        }

        return false;
    }



}