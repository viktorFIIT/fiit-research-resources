package stochastictree.strategies;

import java.util.*;

import stochastictree.Main;
import stochastictree.StochasticTree;
import stochastictree.Vertex;

/**
 * Provides method to extract node in the stochastic tree with the highest probability.
 * */
public class HighestProbableSequencesWithMoreThanOnePatternStrategy {

    /**
     * Returns string representation of pattern sequences with the highest probabilities in the stochastic tree.
     * Each node in the stochastic tree represents a pattern sequence. Returned pattern sequences may have any
     * number of the patterns negated (as not applied), but all start with the first pattern applied such that
     * establishing expected pattern sequences can start.
     *
     * @return map where key is string representation of the pattern sequence and value is probability that it will
     * be applied
     * */
    public static Map<String, Double> findExpectedPatternSequenceCandidates() {
        return findNodesWithTheHighestProbability();
    }

    private static Map<String, Double> findNodesWithTheHighestProbability() {
        StochasticTree stochasticTree = Main.getStochasticTree();
        List<Vertex> nodesInTheStochasticTree = stochasticTree.getAllNodesInTree();
        Collections.sort(nodesInTheStochasticTree, Comparator.comparingDouble(Vertex::getProbability).reversed());
        Vertex firstNodeWithTheHighestProbability = findFirstPatternSequenceWithHighestProbability(nodesInTheStochasticTree);
        nodesInTheStochasticTree.remove(firstNodeWithTheHighestProbability);

        // search for other nodes with the same probability as the first (and which also have first pattern applied)
        List<Vertex> allCandidatesWithEqualProbability = new ArrayList<>();
        allCandidatesWithEqualProbability.add(firstNodeWithTheHighestProbability);
        allCandidatesWithEqualProbability.addAll(findOtherPatternSequencesWithTheSameHighestProbability(nodesInTheStochasticTree, firstNodeWithTheHighestProbability.getProbability()));

        Map<String, Double> expectedPatternSequenceCandidatesAndProbabilities = new LinkedHashMap<>();
        for (Vertex candidate : allCandidatesWithEqualProbability) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Vertex previouslyAppliedPattern : candidate.getParents()) {
                stringBuilder.append(" -> " + previouslyAppliedPattern.getVertexName());
            }
            stringBuilder.append(" -> " + candidate.getVertexName());
            String sequenceRepresentationOfTheNode = stringBuilder.toString().replaceFirst(" -> ", "").trim();
            expectedPatternSequenceCandidatesAndProbabilities.put(sequenceRepresentationOfTheNode, candidate.getProbability());
        }

        return expectedPatternSequenceCandidatesAndProbabilities;
    }

    private static Vertex findFirstPatternSequenceWithHighestProbability(List<Vertex> usingTheseNodes) {
        Vertex nodeWithHighestProbability = null;
        // find first node in the list of nodes sorted in descending order that has the first pattern applied and save its probability
        for (Vertex sortedNode : usingTheseNodes) {
            // because we don't want expected pattern sequence candidates that consist of the one pattern
            if (!sortedNode.getParents().isEmpty() && !sortedNode.getParents().get(0).getVertexName().contains("¬")) {
                nodeWithHighestProbability = sortedNode;
                break;
            }
        }
        return nodeWithHighestProbability;
    }

    private static List<Vertex> findOtherPatternSequencesWithTheSameHighestProbability(List<Vertex> usingTheseNodes, double highestProbability) {
        List<Vertex> otherNodes = new LinkedList<>();
        for (Vertex sortedNode : usingTheseNodes) {
            if (sortedNode.getProbability() == highestProbability) {
                Vertex nodeWithProbabilityEqualToOtherNodes = sortedNode;

                if (!nodeWithProbabilityEqualToOtherNodes.getParents().isEmpty()) {
                    if (!nodeWithProbabilityEqualToOtherNodes.getParents().get(0).getVertexName().contains("¬")) {
                        if (!otherNodes.contains(nodeWithProbabilityEqualToOtherNodes)) {
                            otherNodes.add(nodeWithProbabilityEqualToOtherNodes);
                        }
                    }
                }
            }
        }
        return otherNodes;
    }
}
