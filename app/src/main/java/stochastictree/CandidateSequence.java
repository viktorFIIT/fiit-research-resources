package stochastictree;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CandidateSequence {

    private final StringProperty sequence = new SimpleStringProperty();

    private final StringProperty probability = new SimpleStringProperty();
    private final BooleanProperty selected = new SimpleBooleanProperty();

    public CandidateSequence(String seq, String prob, boolean select) {
        sequence.set(seq);
        selected.set(select);
        probability.set(prob);
    }

    public StringProperty sequenceProperty() {
        return sequence;
    }

    public StringProperty probabilityProperty() {
        return probability;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }
}