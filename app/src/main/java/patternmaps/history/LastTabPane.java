package patternmaps.history;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class LastTabPane {

    private static TabPane lastVisibleTabPane;

    public static void setLastTabPane(TabPane tabPane) {
        lastVisibleTabPane = tabPane;
    }

    public static TabPane getLastVisibleTabPane() {
        return lastVisibleTabPane;
    }

    public static boolean alreadyContainsTabWithThisName(TabPane tabPane, String tabName) {
        boolean contains = false;
        for (Tab tabToCheck : tabPane.getTabs()) {
            if (tabToCheck.getText().equals(tabName)) {
                contains = true;
                break;
            }
        }
        return contains;
    }

}
