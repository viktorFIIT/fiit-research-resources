package patternmaps.history;

import javafx.scene.control.TabPane;

public class LastTabPane {

    private static TabPane lastVisibleTabPane;

    public static void setLastVisibleTabPane(TabPane tabPane) {
        lastVisibleTabPane = tabPane;
    }

    public static TabPane getLastVisibleTabPane() {
        return lastVisibleTabPane;
    }

}
