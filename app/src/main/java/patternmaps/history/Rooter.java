package patternmaps.history;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import patternmaps.PatternTabs;
import stochastictree.Main;

import java.io.IOException;

/**
 * Class that provides functionality to move from the stochastic tree to pattern map tabs.
 * */
public class Rooter {

    private static TabPane lastVisibleTabPane;

    /**
     * Displays first tab with pattern map of applicable patterns and table with probabilities.
     *
     * @param startingPattern first pattern in the expected pattern sequence candidate
     * */
    public void showFirstPatternTab(String startingPattern) {
        try {
            PatternTabs patternTabs = new PatternTabs();
            TabPane firstTabPane = patternTabs.findNextApplicablePatternAfter(startingPattern, true);
            Rooter.setLastTabPane(firstTabPane);
            Stage primaryStage = Main.getStage();
            Scene firstTabScene = new Scene(Rooter.getLastVisibleTabPane(), 1200, 600);
            primaryStage.setScene(firstTabScene);
            primaryStage.show();
        } catch (IOException e) {
            Alert expectedSequenceCannotBeEstablished = new Alert(Alert.AlertType.ERROR);
            expectedSequenceCannotBeEstablished.setContentText("First pattern map cannot be displayed because of error");
            expectedSequenceCannotBeEstablished.show();
        }
    }

    public static void setLastTabPane(TabPane tabPane) {
        lastVisibleTabPane = tabPane;
    }

    public static TabPane getLastVisibleTabPane() {
        return lastVisibleTabPane;
    }
}
