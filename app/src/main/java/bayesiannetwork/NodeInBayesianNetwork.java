package bayesiannetwork;

import java.util.HashSet;
import java.util.Set;


class NodeInBayesianNetwork {

    private String nodeName;

    private Set<NodeInBayesianNetwork> uniDirectionalRelationships = new HashSet<>();

    private Set<NodeInBayesianNetwork> biDirectionalRelationships = new HashSet<NodeInBayesianNetwork>();

    void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    String getNodeName() {
        return nodeName;
    }

    boolean hasBiDirectionalRelationship() {
        return !biDirectionalRelationships.isEmpty();
    }

    void setUniDirectionalRelationships(Set<NodeInBayesianNetwork> uniDirectionalRelationships) {
        this.uniDirectionalRelationships = uniDirectionalRelationships;
    }

    Set<NodeInBayesianNetwork> getUniDirectionalRelationships() {
        return uniDirectionalRelationships;
    }

    void setBiDirectionalRelationships(Set<NodeInBayesianNetwork> biDirectionalRelationships) {
        this.biDirectionalRelationships = biDirectionalRelationships;
    }

    Set<NodeInBayesianNetwork> getBiDirectionalRelationships() {
        return biDirectionalRelationships;
    }

    @Override
    public String toString() {
        return nodeName;
    }
}