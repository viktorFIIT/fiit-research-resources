package bayesiannetwork;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

class BayesianNetworkUtils {
    static NodeInBayesianNetwork getVertexByName(DefaultDirectedGraph<NodeInBayesianNetwork, DefaultEdge> graph, String name) {
        for (NodeInBayesianNetwork existingVertexInGraph : graph.vertexSet()) {
            if (existingVertexInGraph.getNodeName().equals(name)) {
                return existingVertexInGraph;
            }
        }
        return null;
    }

    static boolean vertexWithThisNameIsAlreadyPresentInGraph(DefaultDirectedGraph<NodeInBayesianNetwork, DefaultEdge> graph, String name) {
        for (NodeInBayesianNetwork existingVertexInGraph : graph.vertexSet()) {
            if (existingVertexInGraph.getNodeName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
