package stochastictree;

import java.text.DecimalFormat;
import java.util.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import patternmaps.history.Rooter;

public class CandidateExtractor {

    /**
     * Method that extracts candidates for expected pattern sequences from stochastic tree.
     * Each node in the tree represents pattern sequence. This node represents last pattern
     * in the sequence and its parents leading to the root of the tree represent previously
     * applied patterns.
     * */
    void identifyExpectedPatternSequenceCandidate() {
        if (Main.getStochasticTree().getNumberOfLevels() == 0) return; // if user clicks on Find candidate button without tree
        if (Main.getStochasticTree().getNumberOfLevels() == 1) {
            List<Vertex> twoVertices = Main.getStochasticTree().getLevelsAndNodes().get(0);
            if (twoVertices.get(0).getProbability() > twoVertices.get(1).getProbability()) {
                showExpectedPatternSequenceCandidateInModalWindow(twoVertices.get(0).getVertexName(), twoVertices.get(0).getProbability());
            } else {
                showExpectedPatternSequenceCandidateInModalWindow(twoVertices.get(1).getVertexName(), twoVertices.get(1).getProbability());
            }
        } else {
            Collections.sort(Main.getStochasticTree().getAllNodesInTree(), Comparator.comparingDouble(Vertex::getProbability));
            List<Vertex> allNodesInTree = Main.getStochasticTree().getAllNodesInTree();
            List<Vertex> acceptedNodes = acceptNodesWithAtLeastHalfParentsApplied(allNodesInTree);
            Map<String, Double> allSequencesInTree = convertAcceptedNodesToStringPatternSequences(acceptedNodes);
            Map<String, Double> candidateSequenceWithProbability = findExpectedPatternSequenceCandidate(allSequencesInTree);
            Map.Entry<String, Double> candidateSequenceForUser = candidateSequenceWithProbability.entrySet().iterator().next();
            showExpectedPatternSequenceCandidateInModalWindow(candidateSequenceForUser.getKey(), candidateSequenceForUser.getValue());
        }
    }

    /**
     * Method that takes as input nodes in the stochastic tree and returns nodes that have at least (levelsInTree / 2)
     * parents applied, that is, at least (levelsInTree / 2) parent nodes do not contain negation sign
     *
     * @param allNodesInTree list of nodes to check
     * @return list of nodes with at least (levelsInTree / 2) parents applied
     * */
    private List<Vertex> acceptNodesWithAtLeastHalfParentsApplied(List<Vertex> allNodesInTree) {
        double threshold = (Main.getStochasticTree().getNumberOfLevels() / 2);
        List<Vertex> acceptedNodes = new LinkedList<>();
        for (Vertex sortedNode : allNodesInTree) {
            if (!sortedNode.getParents().isEmpty()) {
                int counterOfAppliedParentsBefore = 0;
                for (Vertex parentOfSortedNode : sortedNode.getParents()) {
                    if (!parentOfSortedNode.getVertexName().contains("¬")) {
                        counterOfAppliedParentsBefore++;
                    }
                }
                if (counterOfAppliedParentsBefore >= threshold && !acceptedNodes.contains(sortedNode)) acceptedNodes.add(sortedNode);
            }
        }
        return acceptedNodes;
    }

    /**
     * Method that takes as input nodes list of nodes in tree that have at least (levelsInTree / 2) of their
     * parents applied, that is, at least (levelsInTree / 2) of their parent nodes do not contain negation sign,
     * and using information about parent nodes builds a string representation of pattern sequence. First parent
     * node's name is used as first pattern in this sequence and accepted node's name is used as last pattern in
     * this sequence. Pattern names (node names) are delimited with '->' character.
     *
     * @param acceptedNodes list of accepted nodes that have at least (levelsInTree / 2) of their parents applied.
     *                      Some parent nodes of these accepted nodes may represent patterns not applied.
     * @return map where key is string representation of pattern sequence and value is probability this sequence
     * will be applied. Some patterns in these pattern sequences don't have to be applied. Because input to this
     * method is a list of accepted nodes, output from this method contains string representations of accepted
     * pattern sequences.
     * */
    private Map<String, Double> convertAcceptedNodesToStringPatternSequences(List<Vertex> acceptedNodes) {
        Map<String, Double> sequencesAndProbabilitiesInTree = new HashMap<>();
        for (Vertex nodeInTree : acceptedNodes) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Vertex previouslyAppliedPattern : nodeInTree.getParents()) {
                stringBuilder.append(" -> " + previouslyAppliedPattern.getVertexName());
            }
            stringBuilder.append(" -> " + nodeInTree.getVertexName());
            sequencesAndProbabilitiesInTree.put(stringBuilder.toString().replaceFirst(" -> ", ""), nodeInTree.getProbability());
        }
        return sequencesAndProbabilitiesInTree;
    }

    /**
     * Method that takes as input map where key is string representation of the accepted pattern sequence and value probability
     * this sequence will be applied, and returns first pattern sequence with all patterns applied that it finds.
     *
     * @param acceptedPatternSequences map where key is string representation of accepted pattern sequence and value probability
     *                                 this sequence will be applied.
     * @return expected pattern sequence candidate in map, where key is its string representation and value probability this
     * sequence will be applied.
     * */
    private Map<String, Double> findExpectedPatternSequenceCandidate(Map<String, Double> acceptedPatternSequences) {
        Map<String, Double> sequencesWithAllPatternsApplied = new HashMap<>();
        for (Map.Entry<String, Double> acceptedSequence : acceptedPatternSequences.entrySet()) {
            if (!acceptedSequence.getKey().contains("¬")) {
                sequencesWithAllPatternsApplied.put(acceptedSequence.getKey(), acceptedSequence.getValue());
            }
        }
        String sequenceWithHighestProbability = Collections.max(sequencesWithAllPatternsApplied.entrySet(), Map.Entry.comparingByValue()).getKey();
        Map<String, Double> expectedPatternSequenceCandidate = new HashMap<>();
        expectedPatternSequenceCandidate.put(sequenceWithHighestProbability, sequencesWithAllPatternsApplied.get(sequenceWithHighestProbability));
        return expectedPatternSequenceCandidate;
    }

    private void showExpectedPatternSequenceCandidateInModalWindow(String candidateSequence, Double itsProbability) {
        Stage dialogStage = new Stage();
        DecimalFormat df = new DecimalFormat("0.00000");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Text candidateSequenceText = new Text("Extracted candidate sequence " + candidateSequence);
        Text probabilityOfApplyingSequenceText = new Text("Probability this sequence will be applied is " + df.format(itsProbability));

        Button establishExpectedPatternSequenceButton = new Button("Establish");
        establishExpectedPatternSequenceButton.setOnAction(establishAction -> {
            dialogStage.close();
            if (candidateSequence.contains("->")) {
                String firstPatternInCandidateSequence = candidateSequence.substring(0, candidateSequence.indexOf("->") - 1);
                showPatternMap(firstPatternInCandidateSequence);
            } else {
                // TODO make for case there is only one pattern in expected pattern sequence candidate
            }
        });

        VBox modalWindowContent = new VBox(candidateSequenceText, probabilityOfApplyingSequenceText, establishExpectedPatternSequenceButton);
        modalWindowContent.setAlignment(Pos.CENTER);
        modalWindowContent.setPadding(new Insets(15));
        dialogStage.setScene(new Scene(modalWindowContent));
        dialogStage.show();
    }

    private void showPatternMap(String firstPatternInCandidateSequence) {
        Rooter rooter = new Rooter();
        rooter.showFirstPatternTab(firstPatternInCandidateSequence);
    }

}
