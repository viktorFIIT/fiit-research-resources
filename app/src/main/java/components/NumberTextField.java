package components;

import javafx.scene.control.TextField;

/**
 * Text field that allows only integers and any value that
 * is not integer is automatically stripped from the input value.
 */
public class NumberTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text) {
        return text.matches("[1-9]*");
    }
}