package stochastictree;

import java.text.DecimalFormat;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import patternmaps.history.Rooter;
import stochastictree.strategies.HighestProbableSequencesWithMoreThanOnePatternStrategy;

/**
 * Class that extracts candidates for expected pattern sequences from stochastic tree.
 * Each node in the tree represents pattern sequence. This node represents last pattern
 * in the sequence and its parents leading to the root of the tree represent previously
 * applied patterns.
 * */
public class CandidateExtractor {

    /**
     * Starts extraction of the expected pattern sequence candidates.
     * */
    void identifyExpectedPatternSequenceCandidate() {
        if (Main.getStochasticTree().getNumberOfLevels() == 0) {
            Alert missingTreeInfo = new Alert(Alert.AlertType.INFORMATION);
            missingTreeInfo.setContentText("Create stochastic tree first");
            missingTreeInfo.show();
            return;
        }
        if (Main.getStochasticTree().getNumberOfLevels() == 1) {
            List<Vertex> twoVertices = Main.getStochasticTree().getLevelsAndNodes().get(0);
            if (twoVertices.get(0).getProbability() > twoVertices.get(1).getProbability()) {
                showOneExpectedPatternSequenceCandidateInModalWindow(twoVertices.get(0).getVertexName(), twoVertices.get(0).getProbability());
            } else {
                showOneExpectedPatternSequenceCandidateInModalWindow(twoVertices.get(1).getVertexName(), twoVertices.get(1).getProbability());
            }
        } else {
            Map<String, Double> candidatesToShow = HighestProbableSequencesWithMoreThanOnePatternStrategy.findExpectedPatternSequenceCandidates();
            showListOfExpectedPatternSequenceCandidatesInModalWindow(candidatesToShow);
        }
    }

    /**
     * Shows one expected pattern sequence candidate user can start establishing expected pattern sequence with.
     * Stochastic tree has only one node with the highest probability if it consists of only two child nodes (and
     * one root).
     *
     * @param candidateSequence string representation of the expected pattern sequence candidate
     * @param probabilityOfUsingIt probability of using the expected pattern sequence candidate extracted from the stochastic tree
     * */
    private void showOneExpectedPatternSequenceCandidateInModalWindow(String candidateSequence, Double probabilityOfUsingIt) {
        Stage dialogStage = new Stage();
        DecimalFormat df = new DecimalFormat("0.00000");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Text candidateSequenceText = new Text("Extracted candidate sequence " + candidateSequence);
        Text probabilityOfApplyingSequenceText = new Text("Probability this sequence will be applied is " + df.format(probabilityOfUsingIt));

        Button establishExpectedPatternSequenceButton = new Button("Establish");
        establishExpectedPatternSequenceButton.setOnAction(establishAction -> {
            Alert notEnoughPatternsInfo = new Alert(Alert.AlertType.INFORMATION);
            notEnoughPatternsInfo.setContentText("At least two patterns must be present in the kick-off pattern sequence");
            notEnoughPatternsInfo.show();
            dialogStage.close();
        });

        VBox modalWindowContent = new VBox(candidateSequenceText, probabilityOfApplyingSequenceText, establishExpectedPatternSequenceButton);
        modalWindowContent.setAlignment(Pos.CENTER);
        modalWindowContent.setPadding(new Insets(15));
        dialogStage.setScene(new Scene(modalWindowContent));
        dialogStage.show();
    }

    /**
     * Shows a list of all expected pattern sequence candidates in the modal window after user clicks on the button "Find candidate".
     * This must be a list because there can be more than one node with the highest probability in the stochastic tree (they may have
     * equal probability). This list is displayed in the table where first column shows the expected pattern sequence candidate, and
     * the second column contains checkbox that must be checked if the user wants to start establishing expected pattern sequences
     * with the selected candidate.
     *
     * @param allCandidateSequencesWithProbabilities map where key is the string representation of the pattern sequence and value is
     *                                               probability this candidate pattern sequence will be applied that is extracted from
     *                                               the stochastic tree
     * */
    private void showListOfExpectedPatternSequenceCandidatesInModalWindow(Map<String, Double> allCandidateSequencesWithProbabilities) {

        Stage dialogStage = new Stage();

        DecimalFormat df = new DecimalFormat("0.00000");
        List<CandidateSequence> candidatesToChoose = new ArrayList<>();
        for (Map.Entry<String, Double> candidatePatternSequence : allCandidateSequencesWithProbabilities.entrySet()) {
            candidatesToChoose.add(new CandidateSequence(candidatePatternSequence.getKey(), df.format(candidatePatternSequence.getValue()), false));
        }

        dialogStage.initModality(Modality.WINDOW_MODAL);
        Text instructionText = new Text("Select one candidate pattern sequence below");

        var view  = new TableView<CandidateSequence>();
        view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List columns = view.getColumns();

        TableColumn candidateColumn = new TableColumn<CandidateSequence, Boolean>( "Candidate");
        candidateColumn.setCellValueFactory( new PropertyValueFactory<>("sequence"));
        columns.add(candidateColumn);

        TableColumn probabilityColumn = new TableColumn<CandidateSequence, Boolean>("Probability");
        probabilityColumn.setCellValueFactory( new PropertyValueFactory<>("probability"));
        columns.add(probabilityColumn);

        TableColumn selectedColumn = new TableColumn<CandidateSequence, Boolean>("Select");
        selectedColumn.setCellValueFactory( new PropertyValueFactory<>("selected"));
        selectedColumn.setCellFactory( tc -> new CheckBoxTableCell<>());
        columns.add(selectedColumn);

        ObservableList items = FXCollections.observableArrayList(candidatesToChoose);
        view.setItems(items);
        view.setEditable(true);

        Button selectButton = new Button("Establish Pattern Sequence");
        selectButton.setMaxWidth(Double.MAX_VALUE);
        selectButton.setOnAction(e -> {
            for (CandidateSequence candidate : view.getItems()) {
                if (candidate.selectedProperty().get()) {

                    if (candidate.sequenceProperty().get().contains("->")) {
                        String firstPatternInCandidateSequence = candidate.sequenceProperty().get().substring(0, candidate.sequenceProperty().get().indexOf("->") - 1);
                        Rooter rooter = new Rooter();
                        rooter.showFirstPatternTab(firstPatternInCandidateSequence);
                    }

                    dialogStage.close();
                    break;
                }
            }
        });

        VBox modalWindowContent = new VBox(instructionText, new BorderPane(view, null, null, selectButton, null));
        modalWindowContent.setAlignment(Pos.CENTER);
        modalWindowContent.setPadding(new Insets(15));
        dialogStage.setScene(new Scene(modalWindowContent, 500, 250));
        dialogStage.show();
    }

}