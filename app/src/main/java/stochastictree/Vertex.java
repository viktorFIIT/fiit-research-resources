package stochastictree;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

class Vertex {

    private DecimalFormat df = new DecimalFormat("0.00000");

    private LinkedList<Vertex> parents = new LinkedList<>();

    private String vertexName;

    private double probability;

    Vertex(String vertexName) {
        this.vertexName = vertexName;
    }

    void addParent(Vertex parent) {
        this.parents.add(parent);
    }

    List<Vertex> getParents() {
        return this.parents;
    }

    void setProbability(double probability) {
        this.probability = probability;
    }

    double getProbability() {
        return this.probability;
    }

    String getVertexName() {
        return this.vertexName;
    }

    @Override
    public String toString() {
        if (this.probability != 0.00) {
            return "p(" + vertexName + ")=" + df.format(this.probability);
        } else {
           return "n/a";
        }
    }
}