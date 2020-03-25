package iart.city_plan.graph;

import iart.city_plan.model.City;

import java.util.Objects;
import java.util.Vector;

class Vertex {
    private City info; // contents

    private boolean visited;          // auxiliary field used by dfs and bfs
    private int indegree;          // auxiliary field used by topsort
    private boolean processing;       // auxiliary field used by isDAG
    private double dist = 0;
    private Vertex path = null;
    private int queueIndex = 0;        // required by MutablePriorityQueue

    public Vector<Edge> adj;

    Vertex(City info){
        this.info = info;
    }

    public City getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) { // TODO
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(info, vertex.info);
    }


    public void setDist(double dist) {
        this.dist = dist;
    }

    public void addEdge(Vertex dest, double weight){
        adj.add(new Edge(dest, weight));
    }

    public boolean removeEdgeTo(Vertex d){
        for (Edge e : this.adj) {
            if(e.getDest() == d){
               adj.remove(d);
               return true;
            }
        }
        return false;
    }



}