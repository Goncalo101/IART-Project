package iart.city_plan.graph;

public class Edge<T> {
    protected Vertex<T> dest;
    public double weight;

    Edge(Vertex<T> dest, double weight){
        this.dest = dest;
        this.weight = weight;
    }

    public Vertex<T> getDest() {
        return dest;
    }

}
