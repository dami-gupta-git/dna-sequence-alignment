package alignment.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class AlignmentQueryTest {

    @Test
    public void sequenceShouldMatch(){
        AlignmentQuery qry = new AlignmentQuery();
        qry.setSequence("ATGGC");
        Assert.assertEquals(qry.getSequence(), "ATGGC");
    }
}