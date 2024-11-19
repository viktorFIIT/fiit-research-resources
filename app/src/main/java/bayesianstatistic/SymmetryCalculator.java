package bayesianstatistic;

import java.text.DecimalFormat;

import stochastictree.Main;

/**
 * Calculates strength of symmetry of relationship between two patterns as a numerical (double) value.
 */
public class SymmetryCalculator {

    /**
     * Calculates strength of symmetry of relationship between patterns provided as input parameters.
     *
     * @param betweenCentralPattern name or abbreviation of the central pattern in pattern map. This name
     *                              or abbreviation must be the same as used in the text area while inserting
     *                              kick-off pattern sequence.
     * @param andAdditionalPattern  name or abbreviation of another pattern linking to central pattern in pattern
     *                              map or be linked by this central pattern. This name or its abbreviation must be
     *                              the same as used in the text area while inserting kick-off pattern sequence.
     * @return object containing numeric representation of the strength of symmetry of relationship between two patterns
     */
    public static SymmetryOfRelationship calculateSymmetryOfRelationship(String betweenCentralPattern, String andAdditionalPattern) {
        if (!Main.getKickOffPatternSequencePatternsFromTextArea().isEmpty()) {
            if (Main.getKickOffPatternSequencePatternsFromTextArea().contains(andAdditionalPattern)) {
                /*
                 * following code performs calculation like this:
                 * if SW is central pattern in pattern map and FW is pointed pattern, then
                 * p(FW|SW)=0.04167
                 * p(SW|FW)=(p(FW|SW)*p(SW))/(p(FW|SW)*p(SW) + p(FW|¬SW)*p(¬SW))
                 * */
                int numberOfAppliedPatternsBefore = Main.getKickOffPatternSequencePatternsFromTextArea().indexOf(andAdditionalPattern);
                double probOfApplyingCentralPattern = 1.0 / Main.getNumberOfPatternsUserWorksWithInModalWindow();
                double nominatorForBayesRule = (1.0 / (Main.getNumberOfPatternsUserWorksWithInModalWindow() - numberOfAppliedPatternsBefore)) * probOfApplyingCentralPattern;
                double bayesRule = nominatorForBayesRule / ((nominatorForBayesRule) + ((1.0 / (Main.getNumberOfPatternsUserWorksWithInModalWindow() - numberOfAppliedPatternsBefore - 1)) * (1.0 - probOfApplyingCentralPattern)));
                DecimalFormat formatter = new DecimalFormat("0.00000"); // standard five decimal places
                String symmetry = formatter.format(Math.abs((1.0 / (Main.getNumberOfPatternsUserWorksWithInModalWindow() - numberOfAppliedPatternsBefore)) - bayesRule));
                SymmetryOfRelationship symmetryOfRelationship = new SymmetryOfRelationship(betweenCentralPattern, andAdditionalPattern, betweenCentralPattern + "->" + andAdditionalPattern, formatter.format(1.0 / (Main.getNumberOfPatternsUserWorksWithInModalWindow() - numberOfAppliedPatternsBefore)), andAdditionalPattern + "->" + betweenCentralPattern, formatter.format(bayesRule), symmetry);
                return symmetryOfRelationship;
            } else {
                return null;
            }
        } else if (!Main.getKickOffPatternSequencePatternsFromTextFields().isEmpty()) {
            if (Main.getKickOffPatternSequencePatternsFromTextFields().contains(andAdditionalPattern)) {
                /*
                 * following code performs calculation like this:
                 * if SW is central pattern in pattern map and FW is pointed pattern, then
                 * p(FW|SW)=0.04167
                 * p(SW|FW)=(p(FW|SW)*p(SW))/(p(FW|SW)*p(SW) + p(FW|¬SW)*p(¬SW))
                 * */
                int numberOfAppliedPatternsBefore = Main.getKickOffPatternSequencePatternsFromTextFields().indexOf(andAdditionalPattern);
                double probOfApplyingCentralPattern = 1.0 / Main.getNumberOfPatternsUserWorksWithInTextField();
                double nominatorForBayesRule = (1.0 / (Main.getNumberOfPatternsUserWorksWithInTextField() - numberOfAppliedPatternsBefore)) * probOfApplyingCentralPattern;
                double bayesRule = nominatorForBayesRule / ((nominatorForBayesRule) + ((1.0 / (Main.getNumberOfPatternsUserWorksWithInTextField() - numberOfAppliedPatternsBefore - 1)) * (1.0 - probOfApplyingCentralPattern)));
                DecimalFormat formatter = new DecimalFormat("0.00000");
                String symmetry = formatter.format(Math.abs((1.0 / (Main.getNumberOfPatternsUserWorksWithInTextField() - numberOfAppliedPatternsBefore)) - bayesRule));
                SymmetryOfRelationship symmetryOfRelationship = new SymmetryOfRelationship(betweenCentralPattern, andAdditionalPattern, betweenCentralPattern + "->" + andAdditionalPattern, formatter.format(1.0 / (Main.getNumberOfPatternsUserWorksWithInTextField() - numberOfAppliedPatternsBefore)), andAdditionalPattern + "->" + betweenCentralPattern, formatter.format(bayesRule), symmetry);
                return symmetryOfRelationship;
            } else {
                return null;
            }
        }
        return null;
    }
}
