package bayesianstatistic;

/**
 * POJO implementation of the class that wraps numerical representation of
 * the strength of symmetry of relationship between two patterns along with
 * values used for its computation.
 */
public class SymmetryOfRelationship {

    private String centralPattern;

    private String additionalPattern;

    private String probAfterName;

    private String probAfter;

    private String inverseProbName;

    private String inverseProb;

    private String symmetry;

    public SymmetryOfRelationship(String centralPattern, String additionalPattern, String probAfterName, String probAfter, String inverseProbName, String inverseProb, String symmetry) {
        this.centralPattern = centralPattern;
        this.additionalPattern = additionalPattern;
        this.probAfterName = probAfterName;
        this.probAfter = probAfter;
        this.inverseProbName = inverseProbName;
        this.inverseProb = inverseProb;
        this.symmetry = symmetry;
    }

    public void setCentralPattern(String centralPattern) {
        this.centralPattern = centralPattern;
    }

    public String getCentralPattern() {
        return centralPattern;
    }

    public void setAdditionalPattern(String additionalPattern) {
        this.additionalPattern = additionalPattern;
    }

    public String getAdditionalPattern() {
        return additionalPattern;
    }

    public void setProbAfterName(String probAfterName) {
        this.probAfterName = probAfterName;
    }

    public String getProbAfterName() {
        return this.probAfterName;
    }

    public void setProbAfter(String probAfter) {
        this.probAfter = probAfter;
    }

    public String getProbAfter() {
        return this.probAfter;
    }

    public void setInverseProbName(String inverseProbName) {
        this.inverseProbName = inverseProbName;
    }

    public String getInverseProbName() {
        return this.inverseProbName;
    }

    public void setInverseProb(String inverseProb) {
        this.inverseProb = inverseProb;
    }

    public String getInverseProb() {
        return this.inverseProb;
    }

    public void setSymmetry(String symmetry) {
        this.symmetry = symmetry;
    }

    public String getSymmetry() {
        return this.symmetry;
    }
}
