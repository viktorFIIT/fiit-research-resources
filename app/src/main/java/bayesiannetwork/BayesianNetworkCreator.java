package bayesiannetwork;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import bayesiannetwork.history.UserInputComponent;
import stochastictree.Main;

public class BayesianNetworkCreator {

    // implementation of the Bayesian belief network in JGraphT library
    private static final DefaultDirectedGraph<NodeInBayesianNetwork, DefaultEdge> bayesianBeliefNetwork = new DefaultDirectedGraph<>(DefaultEdge.class);

    private static UserInputComponent userInputComponent;

    /**
     * Creates tab with inputs to insert patterns into Bayesian belief network and with image of the Bayesian belief network.
     */
    public static void initializeBayesianNetworkTab() {
        TabPane bayesianNetworkTabs = new TabPane();

        Tab bayesianNetworkTab = new Tab();
        bayesianNetworkTab.setText("Bayesian Belief Network");
        bayesianNetworkTab.setClosable(false);

        BorderPane bayesianNetworkTabContent = new BorderPane();

        Label labelToAddPattern = new Label();
        labelToAddPattern.setText(" Add node: ");
        TextField addPatternTextField = new TextField();
        Label labelToAddLinkedPattern = new Label();
        labelToAddLinkedPattern.setText(" Add linked pattern: ");
        TextField addLinkedPatternTextField = new TextField();
        Label biDirectionalRelationshipLabel = new Label();
        biDirectionalRelationshipLabel.setText(" Bidirectional ");
        CheckBox biDirectionalRelationshipCheckbox = new CheckBox();
        biDirectionalRelationshipCheckbox.setMinHeight(17);
        Button submitAdditionButton = new Button();
        submitAdditionButton.setText("Add relationship");

        userInputComponent = new UserInputComponent(labelToAddPattern, addPatternTextField, labelToAddLinkedPattern, addLinkedPatternTextField, biDirectionalRelationshipLabel, biDirectionalRelationshipCheckbox, submitAdditionButton);

        // load temporary image instead of the bayesian belief network with nodes
        VBox placeForPicture = initBayesianNetwork();
        placeForPicture.setStyle("-fx-background-color: white;");
        bayesianNetworkTabContent.setCenter(placeForPicture);

        HBox addRelationshipInputRow = new HBox();
        addRelationshipInputRow.getChildren().add(0, userInputComponent.getLabelToAddPattern());
        addRelationshipInputRow.getChildren().add(1, userInputComponent.getAddPatternTextField());
        addRelationshipInputRow.getChildren().add(2, userInputComponent.getLabelToAddLinkedPattern());
        addRelationshipInputRow.getChildren().add(3, userInputComponent.getAddLinkedPatternTextField());
        addRelationshipInputRow.getChildren().add(4, userInputComponent.getBiDirectionalRelationshipLabel());
        addRelationshipInputRow.getChildren().add(5, userInputComponent.getBiDirectionalRelationshipCheckbox());
        addRelationshipInputRow.getChildren().add(6, userInputComponent.getWhitespace());
        addRelationshipInputRow.getChildren().add(7, userInputComponent.getSubmitAdditionButton());
        bayesianNetworkTabContent.setBottom(addRelationshipInputRow); // add components for user input

        bayesianNetworkTab.setContent(bayesianNetworkTabContent);

        bayesianNetworkTabs.getTabs().add(0, bayesianNetworkTab);

        Stage primaryStage = Main.getStage();
        Scene firstTabScene = new Scene(bayesianNetworkTabs, 1200, 600);
        primaryStage.setScene(firstTabScene);
        primaryStage.show();
    }

    /**
     * Set up the tab with Bayesian belief network at the center of the screen and input components at the bottom of this tab
     */
    private static VBox initBayesianNetwork() {
        VBox bayesianNetworkPart = new VBox();

        UserInputComponent userInputComponent = getUserInputComponent();

        // bayesianNetworkPart.getChildren().add(0, BayesianNetworkUtils.loadTemporaryBlankImage());

        userInputComponent.getSubmitAdditionButton().setOnAction(addRelationshipSubmitAction -> {

            if (userInputComponent.getBiDirectionalRelationshipCheckbox().isSelected()) {

                NodeInBayesianNetwork nodeWithBiDirectionalRelationship = new NodeInBayesianNetwork();
                nodeWithBiDirectionalRelationship.setNodeName(userInputComponent.getAddPatternTextField().getText());

                Set<NodeInBayesianNetwork> bidirectionalRelationships = new HashSet<>();
                // creating object for linked pattern, nodeWithBiDirectionalRelationship links to anotherPattern
                NodeInBayesianNetwork anotherPattern = new NodeInBayesianNetwork();
                anotherPattern.setNodeName(userInputComponent.getAddLinkedPatternTextField().getText());
                bidirectionalRelationships.add(anotherPattern);
                // saving information that there is a bidirectional relationship between nodeWithBiDirectionalRelationship and anotherPattern
                nodeWithBiDirectionalRelationship.setBiDirectionalRelationships(bidirectionalRelationships);

                recreateTabWithUpdatedBayesianNetwork(nodeWithBiDirectionalRelationship);

            } else if (!userInputComponent.getBiDirectionalRelationshipCheckbox().isSelected()) { // node with unidirectional relationship is added
                if (BayesianNetworkUtils.vertexWithThisNameIsAlreadyPresentInGraph(bayesianBeliefNetwork, userInputComponent.getAddLinkedPatternTextField().getText())) {
                    // new path needs to be created pointing to already existing pattern in the graph
                    NodeInBayesianNetwork newPatternLinkingToExistingPatternInGraph = new NodeInBayesianNetwork();
                    newPatternLinkingToExistingPatternInGraph.setNodeName(userInputComponent.getAddPatternTextField().getText());

                    Set<NodeInBayesianNetwork> uniDirectionalRelationships = new HashSet<>();
                    NodeInBayesianNetwork alreadyExistingPatternInTheGraph = BayesianNetworkUtils.getVertexByName(bayesianBeliefNetwork, userInputComponent.getAddLinkedPatternTextField().getText());
                    uniDirectionalRelationships.add(alreadyExistingPatternInTheGraph);

                    newPatternLinkingToExistingPatternInGraph.setUniDirectionalRelationships(uniDirectionalRelationships);

                    recreateTabWithUpdatedBayesianNetwork(newPatternLinkingToExistingPatternInGraph);

                } else if (!BayesianNetworkUtils.vertexWithThisNameIsAlreadyPresentInGraph(bayesianBeliefNetwork, userInputComponent.getAddLinkedPatternTextField().getText())) {
                    // new path needs to be created pointing to new pattern from existing pattern

                    NodeInBayesianNetwork nodeWithUniDirectionalRelationship = new NodeInBayesianNetwork();
                    nodeWithUniDirectionalRelationship.setNodeName(userInputComponent.getAddPatternTextField().getText());

                    Set<NodeInBayesianNetwork> uniDirectionalRelationships = new HashSet<>();
                    // creating object for linked pattern, nodeWithUniDirectionalRelationship links to anotherPattern
                    NodeInBayesianNetwork anotherPattern = new NodeInBayesianNetwork();
                    anotherPattern.setNodeName(userInputComponent.getAddLinkedPatternTextField().getText());
                    uniDirectionalRelationships.add(anotherPattern);

                    // saving information that there is a unidirectional relationship between nodeWithUniDirectionalRelationship and anotherPattern
                    nodeWithUniDirectionalRelationship.setBiDirectionalRelationships(uniDirectionalRelationships);

                    recreateTabWithUpdatedBayesianNetwork(nodeWithUniDirectionalRelationship);
                }
            }
        });
        return bayesianNetworkPart;
    }

    /**
     * Displays tab with image of the Bayesian network with additional pattern passed as parameter
     *
     * @param newNodeAdded new node that is to be added to the image of Bayesian belief network
     */
    private static void recreateTabWithUpdatedBayesianNetwork(NodeInBayesianNetwork newNodeAdded) {
        TabPane substituteTabPane = new TabPane();
        Tab substituteTab = new Tab();
        substituteTab.setText("Bayesian Belief Network");
        substituteTab.setClosable(false);

        BorderPane substituteBorderPane = new BorderPane();

        VBox updatedBayesianNetwork = new VBox();
        ImageView updatedImage = loadNewImageOfBayesianNetwork(newNodeAdded);
        updatedBayesianNetwork.getChildren().add(updatedImage);
        VBox.setMargin(updatedImage, new Insets(10, 540, 10, 540)); // setting margin of updated image to center of the screen
        updatedBayesianNetwork.setStyle("-fx-background-color: white;");
        substituteBorderPane.setCenter(updatedBayesianNetwork);

        HBox substituteInputRow = new HBox();
        UserInputComponent userInputComponent = getUserInputComponent();
        substituteInputRow.getChildren().add(0, userInputComponent.getLabelToAddPattern());
        substituteInputRow.getChildren().add(1, userInputComponent.getAddPatternTextField());
        substituteInputRow.getChildren().add(2, userInputComponent.getLabelToAddLinkedPattern());
        substituteInputRow.getChildren().add(3, userInputComponent.getAddLinkedPatternTextField());
        substituteInputRow.getChildren().add(4, userInputComponent.getBiDirectionalRelationshipLabel());
        substituteInputRow.getChildren().add(5, userInputComponent.getBiDirectionalRelationshipCheckbox());
        substituteInputRow.getChildren().add(6, userInputComponent.getWhitespace());
        substituteInputRow.getChildren().add(7, userInputComponent.getSubmitAdditionButton());
        substituteBorderPane.setBottom(substituteInputRow);

        substituteTab.setContent(substituteBorderPane);

        substituteTabPane.getTabs().add(0, substituteTab);

        Stage primaryStage = Main.getStage();
        Scene updatedTabScene = new Scene(substituteTabPane, 1200, 600);
        primaryStage.setScene(updatedTabScene);
        primaryStage.show();
    }

    /* This would not need to be a method but its name indicates its role in recreateTabWithUpdatedBayesianNetwork() method in this class */
    private static ImageView loadNewImageOfBayesianNetwork(NodeInBayesianNetwork patternToAdd) {
        try {
            return createNewImageOfBayesianNetwork(patternToAdd);
        } catch (IOException graphSaveException) {
            graphSaveException.printStackTrace();
        }
        return null;
    }

    private static UserInputComponent getUserInputComponent() {
        return userInputComponent;
    }

    private static ImageView createNewImageOfBayesianNetwork(NodeInBayesianNetwork patternToAdd) throws IOException {
        File imgFile = new File("src/test/resources/bayesian-belief-network.png");
        if (imgFile.exists()) {
            imgFile.delete();
            imgFile.createNewFile();
        } else {
            imgFile.createNewFile();
        }

        if (!bayesianBeliefNetwork.containsVertex(patternToAdd)) bayesianBeliefNetwork.addVertex(patternToAdd);

        if (patternToAdd.hasBiDirectionalRelationship()) {
            for (NodeInBayesianNetwork linkedPattern : patternToAdd.getBiDirectionalRelationships()) {
                bayesianBeliefNetwork.addVertex(linkedPattern);
                bayesianBeliefNetwork.addEdge(patternToAdd, linkedPattern);
                bayesianBeliefNetwork.addEdge(linkedPattern, patternToAdd);
            }
        } else {
            for (NodeInBayesianNetwork linkedPattern : patternToAdd.getUniDirectionalRelationships()) {
                bayesianBeliefNetwork.addVertex(linkedPattern);
                bayesianBeliefNetwork.addEdge(patternToAdd, linkedPattern);
            }
        }

        JGraphXAdapter<NodeInBayesianNetwork, DefaultEdge> graphAdapter = new JGraphXAdapter<>(bayesianBeliefNetwork);

        graphAdapter.getCellToEdgeMap().forEach((edge, cell) -> {
            edge.setValue(null);
        });

        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, java.awt.Color.WHITE, true, null);
        ImageIO.write(image, "PNG", imgFile);

        Image bayesianNetworkImage = new Image(new FileInputStream("src/test/resources/bayesian-belief-network.png"));
        ImageView imageView = new ImageView(bayesianNetworkImage);

        return imageView;
    }

}