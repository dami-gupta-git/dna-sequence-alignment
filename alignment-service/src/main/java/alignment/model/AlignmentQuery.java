package alignment.model;

/**
 * Represents parameter object in REST request
 */
public class AlignmentQuery {
    // Query sequence string
    String sequence;

    public String getSequence() {
        return sequence;
    }
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public AlignmentQuery(){
    }


}
