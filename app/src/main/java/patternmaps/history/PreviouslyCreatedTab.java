package patternmaps.history;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PreviouslyCreatedTab {

    private String createdForThisPattern;

    private String patternExpectedToBeAppliedNext;

    private TableView filledTableWithProbabilities;

    private Tab tab;

    private BorderPane tabContent;

    public PreviouslyCreatedTab(String createdForThisPattern, TableView filledTableWithProbabilities, Tab tab, BorderPane tabContent) {
        this.createdForThisPattern = createdForThisPattern;
        this.filledTableWithProbabilities = filledTableWithProbabilities;
        this.tab = tab;
        this.tabContent = tabContent;
    }

    public String getForPattern() {
        return this.createdForThisPattern;
    }

    public Tab getPreviouslyCreatedTab() {
        return tab;
    }

    public BorderPane getPreviouslyCreatedTabContent() {
        return tabContent;
    }

    public void setPatternExpectedToBeAppliedNext(String patternExpectedToBeAppliedNext) {
        this.patternExpectedToBeAppliedNext = patternExpectedToBeAppliedNext;
    }

    public Tab recreateTab() throws FileNotFoundException {
        BorderPane recreatedBorderPane = new BorderPane();
        recreatedBorderPane.setLeft(recreateLeftGroup());
        recreatedBorderPane.setCenter(recreateCenterGroup());
        Tab recreatedTab = new Tab();
        recreatedTab.setText(this.createdForThisPattern + " tab");
        recreatedTab.setClosable(false);
        recreatedTab.setContent(recreatedBorderPane);
        return recreatedTab;
    }

    private VBox recreateLeftGroup() throws FileNotFoundException {
        Label recreatedPatternMapLabel = new Label();
        recreatedPatternMapLabel.setText("  Pattern Map of Applicable Patterns Before or After " + this.createdForThisPattern + " pattern ");

        Image recreatedPatternMapImage = new Image(new FileInputStream("src/test/resources/"+this.createdForThisPattern+"-pattern-map.png"));
        ImageView recreatedImageView = new ImageView(recreatedPatternMapImage);

        VBox recreatedLeftGroup = new VBox();
        recreatedLeftGroup.getChildren().addAll(recreatedPatternMapLabel, recreatedImageView);
        return recreatedLeftGroup;
    }

    private VBox recreateCenterGroup() {
        VBox recreatedCenterGroup = new VBox();
        Label patternAppliedNextInThisTab = new Label();
        patternAppliedNextInThisTab.setText("  Pattern expected to be applied next after " + this.createdForThisPattern + " was " + this.patternExpectedToBeAppliedNext);
        recreatedCenterGroup.getChildren().addAll(this.filledTableWithProbabilities, patternAppliedNextInThisTab);
        return recreatedCenterGroup;
    }


    public TableView getFilledTableWithProbabilities() {
        return filledTableWithProbabilities;
    }
}
