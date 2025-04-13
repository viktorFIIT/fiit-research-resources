package stochastictree;

import java.io.IOException;
import java.util.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import components.NumberTextField;
import bayesiannetwork.BayesianNetworkCreator;


/**
 * Start of the program. Execute main method in this class to interact with the program.
 * */
public class Main extends Application {

    private static StochasticTree stochasticTree = new StochasticTree();

    private static int numberOfPatterns;

    private static Stage stage;

    private static List<String> kickOffPatternSequencePatternsFromTextArea;

    private static int numberOfPatternsUserWorksWithInModalWindow;

    private static List<String> alreadyInsertedPatternNames = new ArrayList<>();

    public static Stage getStage() {
        return Main.stage;
    }

    public static StochasticTree getStochasticTree() {
        return stochasticTree;
    }

    /**
     * used to calculate symmetry of relationship between patterns
     * */
    public static List<String> getKickOffPatternSequencePatternsFromTextArea() {
        return Main.kickOffPatternSequencePatternsFromTextArea;
    }

    /**
     * used to calculate symmetry of relationship between patterns
     * */
    public static int getNumberOfPatternsUserWorksWithInModalWindow() {
        return Main.numberOfPatternsUserWorksWithInModalWindow;
    }

    /**
     * used to calculate symmetry of relationship between patterns
     * */
    public static List<String> getKickOffPatternSequencePatternsFromTextFields() {
        return Main.alreadyInsertedPatternNames;
    }

    /**
     * used to calculate symmetry of relationship between patterns
     * */
    public static int getNumberOfPatternsUserWorksWithInTextField() {
        return numberOfPatterns;
    }

    private HBox createUserInputComponent(boolean showLastPatternAdded, String lastPatternAdded) {
        HBox hBox = new HBox();

        Label patternNameLabel = new Label();
        patternNameLabel.setText("   Pattern Abbrev.   ");
        hBox.getChildren().add(patternNameLabel);

        TextField patterNameTextField = new TextField();
        hBox.getChildren().add(patterNameTextField);

        Label numberOfPatternsLabel = new Label();
        numberOfPatternsLabel.setText("   Number of patterns   ");
        hBox.getChildren().add(numberOfPatternsLabel);

        NumberTextField numberOfPatternsTextField;
        if (numberOfPatterns > 0) {
            numberOfPatternsTextField = new NumberTextField();
            numberOfPatternsTextField.setText(numberOfPatterns + "");
            numberOfPatternsTextField.setEditable(false);
        } else {
            numberOfPatternsTextField = new NumberTextField();
        }
        hBox.getChildren().add(numberOfPatternsTextField);

        Label lastNodeAdded = new Label();
        lastNodeAdded.setText(" Last node added: " + lastPatternAdded);
        lastNodeAdded.setVisible(showLastPatternAdded);

        Button insertButton = new Button();
        insertButton.setText("Insert node");
        insertButton.setOnAction(event -> {
            if (!patterNameTextField.getText().isEmpty() && !numberOfPatternsTextField.getText().isEmpty()) {
                if (alreadyInsertedPatternNames.contains(patterNameTextField.getText())) {
                    Alert patternAlreadyInsertedWarning = new Alert(Alert.AlertType.WARNING);
                    patternAlreadyInsertedWarning.setContentText("Node for this pattern already exists in the tree");
                    patternAlreadyInsertedWarning.show();
                } else {

                    alreadyInsertedPatternNames.add(patterNameTextField.getText());
                    numberOfPatterns = Integer.valueOf(numberOfPatternsTextField.getText());

                    WebView stochasticTreeVisualization = stochasticTree.visualizeStochasticTreeUsingTextField(patterNameTextField.getText(), numberOfPatterns);

                    if (stochasticTreeVisualization != null) {
                        BorderPane borderPane = new BorderPane();
                        borderPane.setCenter(stochasticTreeVisualization);
                        borderPane.setBottom(createUserInputComponent(true, patterNameTextField.getText()));

                        Scene updatedScene = new Scene(borderPane, 1200, 600);
                        Main.stage.setScene(updatedScene);
                        Main.stage.show();
                    } else {
                        Alert stochasticTreeTooBigInfo = new Alert(Alert.AlertType.INFORMATION);
                        stochasticTreeTooBigInfo.setContentText("Stochastic tree would be too big, click on button Find candidate instead");
                        stochasticTreeTooBigInfo.show();
                    }
                }
            } else if (patterNameTextField.getText().isEmpty()) {
                Alert missingPatternNameInfo = new Alert(Alert.AlertType.INFORMATION);
                missingPatternNameInfo.setContentText("Pattern abbreviation must be provided");
                missingPatternNameInfo.show();
            } else if (numberOfPatternsTextField.getText().isEmpty()) {
                Alert missingNumberOfPatternsInfo = new Alert(Alert.AlertType.INFORMATION);
                missingNumberOfPatternsInfo.setContentText("Number of patterns you work with must be provided");
                missingNumberOfPatternsInfo.show();
            }
        });

        hBox.getChildren().add(insertButton);

        Button findCandidateForExpectedPatternSequenceButton = new Button();
        findCandidateForExpectedPatternSequenceButton.setText("Find candidate");
        findCandidateForExpectedPatternSequenceButton.setOnAction(findCandidateEvent -> {
            CandidateExtractor candidateExtractor = new CandidateExtractor();
            candidateExtractor.identifyExpectedPatternSequenceCandidate();
        });
        hBox.getChildren().add(findCandidateForExpectedPatternSequenceButton);

        Button uploadFullSequenceButton = new Button();
        uploadFullSequenceButton.setText("Insert Kick-off Sequence");
        uploadFullSequenceButton.setOnAction(batchUploadAction -> {
            readKickOffPatternSequenceFromTextArea();
        });
        hBox.getChildren().add(uploadFullSequenceButton);

        Button clearButton = new Button();
        clearButton.setText("Clear");
        clearButton.setOnAction(clearButtonAction -> {
            numberOfPatterns = 0; // make number of patterns text field editable again
            stochasticTree.clearGraphStorage(alreadyInsertedPatternNames);
            Scene emptyScene = new Scene(buildStochasticTreeGUI(), 1200, 600);
            Main.stage.setScene(emptyScene);
            Main.stage.setTitle("Pattern Sequence Establisher");
            Main.stage.show();
        });
        hBox.getChildren().add(clearButton);

        Button simplifyBayesNetworkButton = new Button();
        simplifyBayesNetworkButton.setText("Use Bayes");
        simplifyBayesNetworkButton.setOnAction(createNetworkAction -> {
            BayesianNetworkCreator.initializeBayesianNetworkTab();
        });

        hBox.getChildren().add(simplifyBayesNetworkButton);
        hBox.getChildren().add(lastNodeAdded);
        return hBox;
    }

    private void readKickOffPatternSequenceFromTextArea() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Insert Kick-off Pattern Sequence");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Text instructionText = new Text();
        instructionText.setText(" Insert abbreviations of pattern names deliminated with -> ");
        instructionText.setTextAlignment(TextAlignment.LEFT);

        Label numberOfPatternsUserWorksWithLabel = new Label();
        numberOfPatternsUserWorksWithLabel.setText(" Number of patterns you work with:  ");

        TextField numberOfPatternsUserWorksWithTextField = new TextField();

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefColumnCount(20);

        Button establishExpectedPatternSequenceButton = new Button("Construct Tree");
        establishExpectedPatternSequenceButton.setOnAction(establishAction -> {

            List<String> abbreviationsFromUser = Arrays.asList(textArea.getText().replace("\s", "").split("->"));

            List<String> patternAbbreviationsWeWorkWith = new ArrayList<>();
            for (String userAbbreviation : abbreviationsFromUser) {
                patternAbbreviationsWeWorkWith.add(userAbbreviation.trim());
            }

            this.kickOffPatternSequencePatternsFromTextArea = patternAbbreviationsWeWorkWith;

            try {
                int numberOfPatterns = Integer.valueOf(numberOfPatternsUserWorksWithTextField.getText());
                Main.numberOfPatternsUserWorksWithInModalWindow = numberOfPatterns;
                WebView stochasticTreeVisualization = stochasticTree.visualizeStochasticTreeUsingListOfPatternAbbreviations(patternAbbreviationsWeWorkWith, numberOfPatterns);
                if (stochasticTreeVisualization == null) {
                    dialogStage.close();
                    Alert stochasticTreeTooBigAlert = new Alert(Alert.AlertType.WARNING);
                    stochasticTreeTooBigAlert.setContentText("Stochastic tree would be too big, click on button Find candidate");
                    stochasticTreeTooBigAlert.show();
                } else {
                    dialogStage.close();
                    BorderPane borderPane = new BorderPane();
                    borderPane.setCenter(stochasticTreeVisualization);
                    borderPane.setBottom(createUserInputComponent(false, ""));

                    Scene updatedScene = new Scene(borderPane, 1200, 600);
                    Main.stage.setScene(updatedScene);
                    Main.stage.show();
                }

            } catch (IOException e) {
                Alert missingPatternNameError = new Alert(Alert.AlertType.ERROR);
                missingPatternNameError.setContentText("Stochastic tree cannot be constructed because of error");
                missingPatternNameError.show();
            }
        });
        HBox instruction = new HBox();
        instruction.getChildren().add(instructionText);

        HBox userInput = new HBox();
        userInput.getChildren().addAll(numberOfPatternsUserWorksWithLabel, numberOfPatternsUserWorksWithTextField);

        VBox textAreaVBox = new VBox(textArea, establishExpectedPatternSequenceButton);
        textAreaVBox.setAlignment(Pos.CENTER);

        VBox group = new VBox();
        group.getChildren().addAll(instruction, userInput, textAreaVBox);
        dialogStage.setScene(new Scene(group));
        dialogStage.show();
    }

    @Override
    public void start(Stage stage) {
        Scene initialScene = new Scene(buildStochasticTreeGUI(), 1200, 600);
        Main.stage = stage;
        Main.stage.setScene(initialScene);
        Main.stage.setTitle("Pattern Sequence Establisher");
        Main.stage.show();
    }

    /**
     * At the start of application, only input text fields and
     * buttons are visible.
     * */
    private BorderPane buildStochasticTreeGUI() {
        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(createUserInputComponent(false, ""));
        return borderPane;
    }

    public static void main(String... args) {
        launch(args);
    }
}
