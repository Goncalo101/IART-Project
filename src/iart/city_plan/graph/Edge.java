package iart.city_plan.graph;

public class Edge {
    private Vertex dest;
    public double weight;

    Edge(Vertex dest, double weight){
        this.dest = dest;
        this.weight = weight;
    }

    public Vertex getDest() {
        return dest;
    }

}
