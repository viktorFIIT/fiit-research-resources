package bayesianstatistic;

import java.text.DecimalFormat;

import stochastictree.Main;

public class SymmetryCalculator {

    /**
     *
     * */
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
                double probOfApplyingCentralPattern = 1.0/Main.getNumberOfPatternsUserWorksWithInModalWindow();
                double nominatorForBayesRule = (1.0/(Main.getNumberOfPatternsUserWorksWithInModalWindow() - numberOfAppliedPatternsBefore))*probOfApplyingCentralPattern;
                double bayesRule = nominatorForBayesRule / ((nominatorForBayesRule) + ((1.0/(Main.getNumberOfPatternsUserWorksWithInModalWindow() - numberOfAppliedPatternsBefore - 1))*(1.0-probOfApplyingCentralPattern)));
                DecimalFormat formatter = new DecimalFormat("0.00000");
                String symmetry = formatter.format(Math.abs((1.0/(Main.getNumberOfPatternsUserWorksWithInModalWindow() - numberOfAppliedPatternsBefore)) - bayesRule));
                SymmetryOfRelationship symmetryOfRelationship = new SymmetryOfRelationship(betweenCentralPattern, andAdditionalPattern, betweenCentralPattern + "->" + andAdditionalPattern, formatter.format(1.0/(Main.getNumberOfPatternsUserWorksWithInModalWindow() - numberOfAppliedPatternsBefore)), andAdditionalPattern + "->" + betweenCentralPattern, formatter.format(bayesRule), symmetry);
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
                double probOfApplyingCentralPattern = 1.0/Main.getNumberOfPatternsUserWorksWithInTextField();
                double nominatorForBayesRule = (1.0/(Main.getNumberOfPatternsUserWorksWithInTextField() - numberOfAppliedPatternsBefore))*probOfApplyingCentralPattern;
                double bayesRule = nominatorForBayesRule / ((nominatorForBayesRule) + ((1.0/(Main.getNumberOfPatternsUserWorksWithInTextField() - numberOfAppliedPatternsBefore - 1))*(1.0-probOfApplyingCentralPattern)));
                DecimalFormat formatter = new DecimalFormat("0.00000");
                String symmetry = formatter.format(Math.abs((1.0/(Main.getNumberOfPatternsUserWorksWithInTextField() - numberOfAppliedPatternsBefore)) - bayesRule));
                SymmetryOfRelationship symmetryOfRelationship = new SymmetryOfRelationship(betweenCentralPattern, andAdditionalPattern, betweenCentralPattern + "->" + andAdditionalPattern, formatter.format(1.0/(Main.getNumberOfPatternsUserWorksWithInTextField() - numberOfAppliedPatternsBefore)), andAdditionalPattern + "->" + betweenCentralPattern, formatter.format(bayesRule), symmetry);
                return symmetryOfRelationship;
            } else {
                return null;
            }
        }
        return null;
    }
}
