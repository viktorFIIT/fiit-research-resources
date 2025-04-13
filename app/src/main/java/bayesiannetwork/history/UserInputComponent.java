package bayesiannetwork.history;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 * Wrapper for passing components in the input row as one parameter
 */
public final class UserInputComponent {
    private final Label labelToAddPattern;

    private final TextField addPatternTextField;

    private final Label labelToAddLinkedPattern;

    private final TextField addLinkedPatternTextField;

    private final Label biDirectionalRelationshipLabel;

    private final CheckBox biDirectionalRelationshipCheckbox;

    private final Button submitAdditionButton;

    private final Region whitespace;

    public UserInputComponent(Label labelToAddPattern, TextField addPatternTextField, Label labelToAddLinkedPattern, TextField addLinkedPatternTextField, Label biDirectionalRelationshipLabel, CheckBox biDirectionalRelationshipCheckbox, Button submitAdditionButton) {
        this.labelToAddPattern = labelToAddPattern;
        this.addPatternTextField = addPatternTextField;
        this.labelToAddLinkedPattern = labelToAddLinkedPattern;
        this.addLinkedPatternTextField = addLinkedPatternTextField;
        this.biDirectionalRelationshipLabel = biDirectionalRelationshipLabel;
        this.biDirectionalRelationshipCheckbox = biDirectionalRelationshipCheckbox;
        this.whitespace = new Region();
        this.whitespace.setMinWidth(10);
        this.submitAdditionButton = submitAdditionButton;
    }

    public Label getLabelToAddPattern() {
        return labelToAddPattern;
    }

    public TextField getAddPatternTextField() {
        return addPatternTextField;
    }

    public Label getLabelToAddLinkedPattern() {
        return labelToAddLinkedPattern;
    }

    public TextField getAddLinkedPatternTextField() {
        return addLinkedPatternTextField;
    }

    public Label getBiDirectionalRelationshipLabel() {
        return biDirectionalRelationshipLabel;
    }

    public CheckBox getBiDirectionalRelationshipCheckbox() {
        return biDirectionalRelationshipCheckbox;
    }

    public Button getSubmitAdditionButton() {
        return submitAdditionButton;
    }

    public Region getWhitespace() {
        return whitespace;
    }
}