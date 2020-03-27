package iart.city_plan.graph;

import java.util.Vector;

public class Graph<T> {

    Vector<Vertex<T>> vertexSet;

    public Vertex<T> findVertex(T info) {
        for (Vertex<T> v : this.vertexSet)
            if (v.getInfo() == info) return v;
        return null;
    }

    public int findVertexIdx(T info) {
        for (int i = 0; i < this.vertexSet.size(); i++) {
            if (this.vertexSet.elementAt(i).getInfo() == info)
                return i;
        }
        return -1;
    }

    public Vector<Vertex<T>> getVertexes() {
        return this.vertexSet;
    }

    public int getNumVertex() {
        return this.vertexSet.size();
    }

    public Vertex<T> addVertex(T info) {
        if (this.findVertex(info) != null) return null;

        Vertex<T> toAdd = new Vertex<>(info);
        this.vertexSet.add(toAdd);
        return toAdd;
    }

    public boolean removeVertex(T info) {
        for (Vertex<T> v : this.vertexSet) {
            if (v.getInfo() == info) {
                this.vertexSet.remove(v);
                return true;
            }
        }
        return true;
    }

    public boolean addEdge(Vertex<T> v1, Vertex<T> v2, double weight) {
        if (v1 == null || v2 == null)
            return false;

        v1.addEdge(v2, weight);
        return true;
    }

    public boolean removeEdge(T src, T dest) {
        Vertex<T> v1 = this.findVertex(src);
        Vertex<T> v2 = this.findVertex(src);

        if(v1 == null || v2 == null) return false;

        v1.removeEdgeTo(v2);
        return true;
    }

}
