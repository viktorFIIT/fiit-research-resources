package patternmaps;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import bayesianstatistic.SymmetryOfRelationship;
import patternmaps.history.LastTabPane;
import patternmaps.history.PreviouslyCreatedTab;
import stochastictree.Main;
import bayesianstatistic.SymmetryCalculator;

public class PatternTabs {

    private TabPane allTabs = new TabPane();

    private ImageView currentPatternMap;

    private Set<String> edgeSymmetries = new HashSet<>();

    private TableView tableWithProbabilities = new TableView();

    private BorderPane tabContent;

    private Set<String> alreadyInsertedRowsInTable = new HashSet<>();

    private Map<String, Double> symmetryValuesForFindingStrongestOne = new HashMap<>();

    private Label patternMapLabel;

    private Label nextApplicablePatternLabel;

    private TextField nextApplicablePatternTextField;

    private CheckBox twoWayRelationshipCheckBox;

    private Button nextApplicablePatternSubmitButton;

    private Map<String, Boolean> edgeForUnknownPatternAlreadyProcessed = new HashMap<>();

    private static List<PreviouslyCreatedTab> tabsWithContents = new LinkedList<>();

    private String nextApplicablePattern;

    private String currentCentralPattern;

    private Map<String, String> symmetriesForUnknownPatterns = new HashMap<>();

    public TabPane findNextApplicablePatternAfter(String thisCentralPattern, boolean subSequentTab) throws IOException {

        this.currentCentralPattern = thisCentralPattern;

        Tab tab = new Tab();
        tab.setText(thisCentralPattern + " tab");
        tab.setClosable(false);

        DefaultDirectedGraph<String, DefaultEdge> graphRepresentation = new DefaultDirectedGraph<>(DefaultEdge.class);

        tabContent = new BorderPane();

        currentPatternMap = createPatternMap(tab, graphRepresentation, thisCentralPattern, null, false);

        VBox leftGroup = new VBox();

        patternMapLabel = new Label();
        patternMapLabel.setText("  Pattern Map of Applicable Patterns Before or After " + thisCentralPattern + " pattern ");

        nextApplicablePatternLabel = new Label();
        nextApplicablePatternLabel.setText("Next applicable pattern abbreviation");

        nextApplicablePatternTextField = new TextField();

        twoWayRelationshipCheckBox = new CheckBox();
        twoWayRelationshipCheckBox.setText("Two-way relationship");

        nextApplicablePatternSubmitButton = new Button();
        nextApplicablePatternSubmitButton.setText("Insert node");
        nextApplicablePatternSubmitButton.setOnAction(actionEvent -> {
            if (!nextApplicablePatternTextField.getText().isEmpty()) {
                try {
                    if (twoWayRelationshipCheckBox.isSelected()) {
                        TabPane lastTabPane = LastTabPane.getLastVisibleTabPane();

                        if (lastTabPane != null) {

                            TabPane substituteTabPane = new TabPane();

                            for (int i=0; i < tabsWithContents.size(); i++) {
                                PreviouslyCreatedTab previouslyCreatedTab = tabsWithContents.get(i);
                                Tab recreatedTab = previouslyCreatedTab.recreateTab();
                                if (!previouslyCreatedTab.getForPattern().equals(thisCentralPattern)) substituteTabPane.getTabs().add(i, recreatedTab);
                            }

                            ImageView updatedPatternMap = createPatternMap(tab, graphRepresentation, thisCentralPattern, nextApplicablePatternTextField.getText(), true);

                            VBox newLeftGroup = new VBox();
                            newLeftGroup.getChildren().addAll(patternMapLabel, updatedPatternMap, nextApplicablePatternLabel, nextApplicablePatternTextField, twoWayRelationshipCheckBox, nextApplicablePatternSubmitButton);
                            tabContent.setLeft(newLeftGroup);

                            VBox newCenterGroup = new VBox();
                            newCenterGroup.getChildren().add(0, tableWithProbabilities);
                            addComponentToContinueInEstablishing(newCenterGroup);
                            tabContent.setCenter(newCenterGroup);

                            tab.setContent(tabContent);

                            substituteTabPane.getTabs().add(tab);

                            substituteTabPane.getSelectionModel().select(tab);

                            LastTabPane.setLastTabPane(substituteTabPane);

                            Scene scene = new Scene(substituteTabPane, 1200, 600);
                            Stage stage = Main.getStage();
                            stage.setScene(scene);
                            stage.show();

                        } else {
                            PreviouslyCreatedTab previouslyCreatedTab = tabsWithContents.get(0);

                            ImageView updatedPatternMap = createPatternMap(tab, graphRepresentation, thisCentralPattern, nextApplicablePatternTextField.getText(), true);

                            VBox newLeftGroup = new VBox();
                            newLeftGroup.getChildren().addAll(patternMapLabel, updatedPatternMap, nextApplicablePatternLabel, nextApplicablePatternTextField, twoWayRelationshipCheckBox, nextApplicablePatternSubmitButton);
                            previouslyCreatedTab.getPreviouslyCreatedTabContent().setLeft(newLeftGroup);

                            VBox newCenterGroup = new VBox();
                            newCenterGroup.getChildren().add(tableWithProbabilities);
                            previouslyCreatedTab.getPreviouslyCreatedTabContent().setCenter(newCenterGroup);

                            Main.getStage().show();
                        }
                    } else {

                        TabPane lastTabPane = LastTabPane.getLastVisibleTabPane();

                        if (lastTabPane != null) {

                            TabPane substituteTabPane = new TabPane();

                            for (int i=0; i < tabsWithContents.size(); i++) {
                                PreviouslyCreatedTab previouslyCreatedTab = tabsWithContents.get(i);
                                Tab recreatedTab = previouslyCreatedTab.recreateTab();
                                if (!previouslyCreatedTab.getForPattern().equals(thisCentralPattern)) substituteTabPane.getTabs().add(i, recreatedTab);
                            }

                            ImageView updatedPatternMap = createPatternMap(tab, graphRepresentation, thisCentralPattern, nextApplicablePatternTextField.getText(), false);

                            VBox newLeftGroup = new VBox();
                            newLeftGroup.getChildren().addAll(patternMapLabel, updatedPatternMap, nextApplicablePatternLabel, nextApplicablePatternTextField, twoWayRelationshipCheckBox, nextApplicablePatternSubmitButton);
                            tabContent.setLeft(newLeftGroup);

                            VBox newCenterGroup = new VBox();
                            newCenterGroup.getChildren().add(0, tableWithProbabilities);
                            addComponentToContinueInEstablishing(newCenterGroup);
                            tabContent.setCenter(newCenterGroup);

                            tab.setContent(tabContent);

                            substituteTabPane.getTabs().add(tab);

                            substituteTabPane.getSelectionModel().select(tab);

                            LastTabPane.setLastTabPane(substituteTabPane);

                            Scene scene = new Scene(substituteTabPane, 1200, 600);
                            Stage stage = Main.getStage();
                            stage.setScene(scene);
                            stage.show();

                        } else {
                            PreviouslyCreatedTab previouslyCreatedTab = tabsWithContents.get(0);

                            ImageView updatedPatternMap = createPatternMap(tab, graphRepresentation, thisCentralPattern, nextApplicablePatternTextField.getText(), false);

                            VBox newLeftGroup = new VBox();
                            newLeftGroup.getChildren().addAll(patternMapLabel, updatedPatternMap, nextApplicablePatternLabel, nextApplicablePatternTextField, twoWayRelationshipCheckBox, nextApplicablePatternSubmitButton);
                            previouslyCreatedTab.getPreviouslyCreatedTabContent().setLeft(newLeftGroup);

                            VBox newCenterGroup = new VBox();
                            newCenterGroup.getChildren().add(tableWithProbabilities);
                            previouslyCreatedTab.getPreviouslyCreatedTabContent().setCenter(newCenterGroup);

                            Main.getStage().show();
                        }
                    }

                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                    Alert lastEditablePatternMapCannotBeUpdated = new Alert(Alert.AlertType.ERROR);
                    lastEditablePatternMapCannotBeUpdated.setContentText("Pattern map of applicable patterns cannot be updated");
                    lastEditablePatternMapCannotBeUpdated.show();
                }
            }
        });


        HBox submitButtonsHBox = new HBox();
        submitButtonsHBox.getChildren().addAll(nextApplicablePatternSubmitButton);

        leftGroup.getChildren().addAll(patternMapLabel, currentPatternMap, nextApplicablePatternLabel, nextApplicablePatternTextField, twoWayRelationshipCheckBox, submitButtonsHBox);
        tabContent.setLeft(leftGroup);

        VBox centerGroup = new VBox();
        constructTableWithProbabilities();
        centerGroup.getChildren().addAll(tableWithProbabilities);
        tabContent.setCenter(centerGroup);

        tab.setContent(tabContent);

        if (subSequentTab) {
            TabPane substituteTabPane = new TabPane();

            for (int i=0; i < tabsWithContents.size(); i++) {
                PreviouslyCreatedTab previouslyCreatedTab = tabsWithContents.get(i);
                Tab recreatedTab = previouslyCreatedTab.recreateTab();
                substituteTabPane.getTabs().add(i, recreatedTab);
            }

            substituteTabPane.getTabs().add(tab);

            // clear caches for new pattern map tab
            edgeSymmetries.clear();
            alreadyInsertedRowsInTable.clear();
            symmetryValuesForFindingStrongestOne.clear();
            edgeForUnknownPatternAlreadyProcessed.clear();

            substituteTabPane.getSelectionModel().select(tab);

            tabsWithContents.add(new PreviouslyCreatedTab(thisCentralPattern, tableWithProbabilities, tab, tabContent));

            LastTabPane.setLastTabPane(substituteTabPane);

            Scene scene = new Scene(substituteTabPane, 1200, 600);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } else {
            allTabs.getTabs().add(tab);
            tabsWithContents.add(new PreviouslyCreatedTab(thisCentralPattern, tableWithProbabilities, tab, tabContent));
        }

        return allTabs;
    }

    private ImageView createPatternMap(Tab insideThisTab, DefaultDirectedGraph graphRepresentation,  String centralPattern, String additionalPattern, boolean twoWayRelationship) throws IOException {
        File imgFile = new File("src/test/resources/"+centralPattern+"-pattern-map.png");
        if (imgFile.exists()) {
            imgFile.delete();
            imgFile.createNewFile();
        } else {
            imgFile.createNewFile();
        }

        if (!graphRepresentation.containsVertex(centralPattern)) graphRepresentation.addVertex(centralPattern);

        if (additionalPattern != null && !additionalPattern.isEmpty() && !graphRepresentation.containsVertex(additionalPattern)) {
            graphRepresentation.addVertex(additionalPattern);

            graphRepresentation.addEdge(centralPattern, additionalPattern);

            if (twoWayRelationship) {
                graphRepresentation.addEdge(additionalPattern, centralPattern);
            }

        } else if (additionalPattern != null && !additionalPattern.isEmpty() && graphRepresentation.containsVertex(additionalPattern)) {
            Alert cannotInsertAlreadyInsertedNode = new Alert(Alert.AlertType.ERROR);
            cannotInsertAlreadyInsertedNode.setContentText("Node for this pattern is already present in pattern map");
            cannotInsertAlreadyInsertedNode.show();
        }

        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<>(graphRepresentation);
        recalculateProbabilitiesAssignedToEdges(insideThisTab, centralPattern, graphAdapter);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, java.awt.Color.WHITE, true, null);
        ImageIO.write(image, "PNG", imgFile);

        Image patternMapImage = new Image(new FileInputStream("src/test/resources/"+centralPattern+"-pattern-map.png"));
        ImageView imageView = new ImageView(patternMapImage);
        return imageView;
    }

    private void recalculateProbabilitiesAssignedToEdges(Tab insideThisTab, String centralPatternInPatternMap, JGraphXAdapter<String, DefaultEdge> graphRepresentation) {
        graphRepresentation.getCellToEdgeMap().forEach((edge, cell) -> {
            String jGraphTGeneratedNameOfTheEdge = edge.getValue().toString();
            String abbrevOfAdditionalPattern = jGraphTGeneratedNameOfTheEdge.substring(jGraphTGeneratedNameOfTheEdge.indexOf(":") + 1, jGraphTGeneratedNameOfTheEdge.indexOf(")")).trim();
            if (!edgeSymmetries.contains(jGraphTGeneratedNameOfTheEdge)) { // because edges are traversed during each graph rendering
                if (!centralPatternInPatternMap.equals(abbrevOfAdditionalPattern)) { // if edge is from central pattern to some another pattern
                    SymmetryOfRelationship symmetryObject = SymmetryCalculator.calculateSymmetryOfRelationship(centralPatternInPatternMap, abbrevOfAdditionalPattern);
                    if (symmetryObject != null) {
                        String symmetry = symmetryObject.getSymmetry();
                        edge.setValue(symmetry);
                        edgeSymmetries.add(centralPatternInPatternMap + ":" + abbrevOfAdditionalPattern);
                        if (!alreadyInsertedRowsInTable.contains(symmetryObject.getAdditionalPattern())) {
                            updateTable(symmetryObject);
                            alreadyInsertedRowsInTable.add(symmetryObject.getAdditionalPattern());
                            symmetryValuesForFindingStrongestOne.put(symmetryObject.getAdditionalPattern(), Double.valueOf(symmetry));
                        }
                    } else {
                        String unknownRelationship = centralPatternInPatternMap + ":" + abbrevOfAdditionalPattern;
                        if (!edgeForUnknownPatternAlreadyProcessed.containsKey(abbrevOfAdditionalPattern)) {
                            edge.setValue(unknownRelationship);
                            edgeForUnknownPatternAlreadyProcessed.put(abbrevOfAdditionalPattern, true);
                            forceUsersToInsertProbabilities(insideThisTab, centralPatternInPatternMap, abbrevOfAdditionalPattern, graphRepresentation);
                        } else {
                            if (symmetriesForUnknownPatterns.containsKey(unknownRelationship)) {
                                edge.setValue(symmetriesForUnknownPatterns.get(unknownRelationship));
                            }
                        }
                    }
                } else {
                    // such that checking two-way relationship does not rewrite calculated symmetry with label from library
                    edge.setValue(null);
                }
            }
        });
    }

    private void updateTable(SymmetryOfRelationship symmetryObject) {

        VBox newCenterGroup = new VBox();
        newCenterGroup.getChildren().add(tableWithProbabilities);
        tabContent.setCenter(newCenterGroup);

        VBox updatedCenter = (VBox) tabContent.getCenter(); // get last rendered table
        TableView tableView = (TableView) updatedCenter.getChildren().get(0);
        tableView.getItems().add(symmetryObject); // update table
        updatedCenter.getChildren().clear();
        updatedCenter.getChildren().add(0, tableView);
        addComponentToContinueInEstablishing(updatedCenter);
        tabContent.setCenter(updatedCenter);

        Tab tab = new Tab();
        tab.setText(symmetryObject.getCentralPattern() + " tab");
        tab.setClosable(false);
        tab.setContent(tabContent);

        TabPane recreatedTabPane = new TabPane();

        for (int i=0; i < tabsWithContents.size(); i++) {
            PreviouslyCreatedTab previouslyCreatedTab = tabsWithContents.get(i);
            if (!previouslyCreatedTab.getPreviouslyCreatedTab().getText().contains(this.currentCentralPattern)) {
                try {
                    recreatedTabPane.getTabs().add(previouslyCreatedTab.recreateTab());
                } catch (FileNotFoundException ex) {
                    System.err.println("Tab from history could not be recreated in attempt to update table");
                }
            }
        }

        recreatedTabPane.getTabs().add(tab);
        recreatedTabPane.getSelectionModel().select(tab);

        LastTabPane.setLastTabPane(recreatedTabPane);

        Stage primaryStage = Main.getStage();
        primaryStage.getScene().setRoot(recreatedTabPane);
        primaryStage.show();
    }

    private VBox addComponentToContinueInEstablishing(VBox centralPartOfWindow) {
        HBox findNextApplicablePatternRow = new HBox();
        Button findNextApplicablePatternButton = new Button();
        findNextApplicablePatternButton.setText("Find next applicable pattern ");

        Button continueButton = new Button();
        continueButton.setText("Continue");
        continueButton.setVisible(false);
        continueButton.setOnAction(continueAction -> {
            String expectedPatternAppliedNext = nextApplicablePatternLabel.getText().trim();
            if (!expectedPatternAppliedNext.isEmpty()) {
                try {
                    tableWithProbabilities = new TableView();
                    findNextApplicablePatternAfter(nextApplicablePattern, true);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                    Alert cannotContinueInEstablishing = new Alert(Alert.AlertType.ERROR);
                    cannotContinueInEstablishing.setContentText("Cannot continue in establishing because pattern map construction thrown an error");
                    cannotContinueInEstablishing.show();
                }
            }
        });

        Label nextApplicablePatternLabel = new Label();
        findNextApplicablePatternButton.setOnAction(findNextAction -> {
            Map.Entry<String, Double> minSymmetry = Collections.min(symmetryValuesForFindingStrongestOne.entrySet(), Map.Entry.comparingByValue());
            nextApplicablePatternLabel.setText("  " + minSymmetry.getKey() + "  ");
            nextApplicablePattern = minSymmetry.getKey();
            continueButton.setVisible(true);

            // save information about the expected pattern applied next to tab stored in history
            PreviouslyCreatedTab updatedObject = null;
            int indexOfUpdatedTab = 0;
            for (int i=0; i < tabsWithContents.size(); i++) {
                if (tabsWithContents.get(i).getForPattern().equals(this.currentCentralPattern)) {
                    tabsWithContents.get(i).setPatternExpectedToBeAppliedNext(minSymmetry.getKey());
                    updatedObject = tabsWithContents.get(i);
                    indexOfUpdatedTab = i;
                    break;
                }
            }

            if (updatedObject != null) {
                tabsWithContents.set(indexOfUpdatedTab, updatedObject);
            }

        });
        findNextApplicablePatternRow.getChildren().addAll(findNextApplicablePatternButton, nextApplicablePatternLabel, continueButton);
        centralPartOfWindow.getChildren().add(1, findNextApplicablePatternRow);
        return centralPartOfWindow;
    }

    private void constructTableWithProbabilities() {
        TableColumn<SymmetryOfRelationship, String> column1 = new TableColumn<>("Relationship");
        column1.setCellValueFactory(new PropertyValueFactory<>("probAfterName"));
        TableColumn<SymmetryOfRelationship, String> column2 = new TableColumn<>("Probability");
        column2.setCellValueFactory(new PropertyValueFactory<>("probAfter"));

        TableColumn<SymmetryOfRelationship, String> column3 = new TableColumn<>("Opposite Relationship");
        column3.setCellValueFactory(new PropertyValueFactory<>("inverseProbName"));
        TableColumn<SymmetryOfRelationship, String> column4 = new TableColumn<>("Opposite Probability");
        column4.setCellValueFactory(new PropertyValueFactory<>("inverseProb"));

        TableColumn<SymmetryOfRelationship, String> column5 = new TableColumn<>("Symmetry of Relationship");
        column5.setCellValueFactory(new PropertyValueFactory<>("symmetry"));

        tableWithProbabilities.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tableWithProbabilities.getColumns().addAll(column1, column2, column3, column4, column5);

        tableWithProbabilities.setPlaceholder(new Label("no probabilities to show"));
    }

    private void forceUsersToInsertProbabilities(Tab insideThisTab, String centralPatternInPatternMap, String abbrevOfAdditionalUnknownPattern, JGraphXAdapter<String, DefaultEdge> graphRepresentation) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Insert probabilities");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        VBox stackedHBoxes = new VBox();
        stackedHBoxes.setSpacing(5);

        Text instructionText = new Text();
        instructionText.setText(" You must insert probability of applying pattern " + abbrevOfAdditionalUnknownPattern + " after and before " + centralPatternInPatternMap + " ");
        instructionText.setTextAlignment(TextAlignment.LEFT);
        HBox instructionTextRow = new HBox();
        instructionTextRow.getChildren().add(instructionText);
        stackedHBoxes.getChildren().add(instructionTextRow);

        // applying abbrevOfAdditionalPattern after centralPatternInPatternMap
        Label probOfApplyingAfterLabel = new Label();
        probOfApplyingAfterLabel.setText(" Prob. of applying " + abbrevOfAdditionalUnknownPattern + " after " + centralPatternInPatternMap + ": ");
        TextField probOfApplyingAfterTextField = new TextField();
        HBox applyingAfterProbabilityRow = new HBox();
        applyingAfterProbabilityRow.getChildren().addAll(probOfApplyingAfterLabel, probOfApplyingAfterTextField);
        stackedHBoxes.getChildren().add(applyingAfterProbabilityRow);

        // applying abbrevOfAdditionalPattern before centralPatternInPatternMap
        Label probOfApplyingBeforeLabel = new Label();
        probOfApplyingBeforeLabel.setText(" Prob. of applying " + centralPatternInPatternMap + " after " + abbrevOfAdditionalUnknownPattern + ": ");
        TextField probOfApplyingBeforeTextField = new TextField();
        HBox applyingBeforeProbabilityRow = new HBox();
        applyingBeforeProbabilityRow.getChildren().addAll(probOfApplyingBeforeLabel, probOfApplyingBeforeTextField);
        stackedHBoxes.getChildren().add(applyingBeforeProbabilityRow);

        Button saveProbabilitiesButton = new Button("Submit");
        saveProbabilitiesButton.setOnAction(saveAction -> {

            DecimalFormat formatter = new DecimalFormat("0.00000");

            double probAfter = Double.valueOf(probOfApplyingAfterTextField.getText());

            double probBefore = Double.valueOf(probOfApplyingBeforeTextField.getText());

            SymmetryOfRelationship symmetryObjectForUnknownPattern = new SymmetryOfRelationship(centralPatternInPatternMap, abbrevOfAdditionalUnknownPattern, centralPatternInPatternMap + "->" + abbrevOfAdditionalUnknownPattern, formatter.format(probAfter), abbrevOfAdditionalUnknownPattern + "->" + centralPatternInPatternMap, formatter.format(probBefore), formatter.format(Math.abs(probAfter - probBefore)));
            symmetryValuesForFindingStrongestOne.put(abbrevOfAdditionalUnknownPattern, Double.valueOf(symmetryObjectForUnknownPattern.getSymmetry()));
            labelEdgeForPatternNotInKickOffSequence(insideThisTab, symmetryObjectForUnknownPattern, graphRepresentation, abbrevOfAdditionalUnknownPattern);

            updateTable(symmetryObjectForUnknownPattern);
            dialogStage.close();
        });

        HBox submitButtonRow = new HBox();
        submitButtonRow.setAlignment(Pos.BOTTOM_CENTER);
        submitButtonRow.getChildren().add(saveProbabilitiesButton);
        stackedHBoxes.getChildren().add(submitButtonRow);

        Scene dialogScene = new Scene(stackedHBoxes, 400, 125);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void labelEdgeForPatternNotInKickOffSequence(Tab insideThisTab, SymmetryOfRelationship symmetryObjectForUnknownPattern, JGraphXAdapter<String, DefaultEdge> previousGraphRepresentation, String patternNotPresentInKickOffSequence) {
        previousGraphRepresentation.getCellToEdgeMap().forEach((edge, cell) -> {
            if (edge.getValue() != null) {
                String jGraphTGeneratedNameOfTheEdge = edge.getValue().toString();
                String abbrevOfAdditionalPattern = jGraphTGeneratedNameOfTheEdge.substring(jGraphTGeneratedNameOfTheEdge.indexOf(":") + 1).trim();
                if (!edgeSymmetries.contains(jGraphTGeneratedNameOfTheEdge)) { // because edges are traversed during each graph rendering
                    if (!symmetryObjectForUnknownPattern.getCentralPattern().equals(abbrevOfAdditionalPattern)) {
                        if (abbrevOfAdditionalPattern.equals(patternNotPresentInKickOffSequence)) {
                            symmetriesForUnknownPatterns.put(symmetryObjectForUnknownPattern.getCentralPattern() + ":" + symmetryObjectForUnknownPattern.getAdditionalPattern(), symmetryObjectForUnknownPattern.getSymmetry());
                            edge.setValue(symmetryObjectForUnknownPattern.getSymmetry());
                            edgeSymmetries.add(jGraphTGeneratedNameOfTheEdge);
                        }
                    }
                }
            }
        });

        File imgFile = new File("src/test/resources/"+symmetryObjectForUnknownPattern.getCentralPattern()+"-pattern-map.png");

        mxIGraphLayout layout = new mxCircleLayout(previousGraphRepresentation);
        layout.execute(previousGraphRepresentation.getDefaultParent());

        BufferedImage image = mxCellRenderer.createBufferedImage(previousGraphRepresentation, null, 2, java.awt.Color.WHITE, true, null);
        try {
            ImageIO.write(image, "PNG", imgFile);
            Image patternMapImage = new Image(new FileInputStream("src/test/resources/"+symmetryObjectForUnknownPattern.getCentralPattern()+"-pattern-map.png"));
            ImageView imageView = new ImageView(patternMapImage);

            TabPane substituteTabPane = new TabPane();

            for (int i=0; i < tabsWithContents.size(); i++) {
                PreviouslyCreatedTab previouslyCreatedTab = tabsWithContents.get(i);
                Tab recreatedTab = previouslyCreatedTab.recreateTab();
                if (!previouslyCreatedTab.getForPattern().equals(this.currentCentralPattern)) substituteTabPane.getTabs().add(i, recreatedTab);
            }

            VBox newLeftGroup = new VBox();
            newLeftGroup.getChildren().addAll(patternMapLabel, imageView, nextApplicablePatternLabel, nextApplicablePatternTextField, twoWayRelationshipCheckBox, nextApplicablePatternSubmitButton);
            tabContent.setLeft(newLeftGroup);
            insideThisTab.setContent(tabContent);

            substituteTabPane.getTabs().add(insideThisTab);

            substituteTabPane.getSelectionModel().select(insideThisTab);

            LastTabPane.setLastTabPane(substituteTabPane);

            Scene scene = new Scene(substituteTabPane, 1200, 600);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            Alert cannotRecreateMapError = new Alert(Alert.AlertType.ERROR);
            cannotRecreateMapError.setContentText("Pattern map could not be updated with label for pattern not present in kick-off sequence");
            cannotRecreateMapError.show();
        }
    }

}
