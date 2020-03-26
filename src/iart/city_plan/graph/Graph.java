package iart.city_plan.graph;

import iart.city_plan.model.City;

import java.util.Vector;

public class Graph {

    Vector<Vertex> vertexSet;

    public Vertex findVertex(City info) {
        for (Vertex v : this.vertexSet)
            if (v.getInfo() == info) return v;
        return null;
    }
    
    public int findVertexIdx(City info) {
        for (int i = 0; i < this.vertexSet.size(); i++) {
            if (this.vertexSet.elementAt(i).getInfo() == info)
                return i;
        }
        return -1;
    }

    public Vector<Vertex> getVertexes() {
        return this.vertexSet;
    }

    public int getNumVertex() {
        return this.vertexSet.size();
    }

    public boolean addVertex(City info) {
        if (this.findVertex(info) != null) return false;

        this.vertexSet.add(new Vertex(info));
        return true;
    }

    public boolean removeVertex(City info) {
        for (Vertex v : this.vertexSet) {
            if (v.getInfo() == info) {
                this.vertexSet.remove(v);
                return true;
            }
        }
        return true;
    }

    public boolean addEdge(Vertex v1, Vertex v2, double weight) {
        if (v1 == null || v2 == null)
            return false;

        v1.addEdge(v2, weight);
        return true;
    }

    public boolean removeEdge(City src, City dest) {
        Vertex v1 = this.findVertex(src);
        Vertex v2 = this.findVertex(src);

        if(v1 == null || v2 == null) return false;

        v1.removeEdgeTo(v2);
        return true;
    }

}
