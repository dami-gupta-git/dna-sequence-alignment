package alignment.services;

import alignment.model.QueryMatch;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests fasta file in resource directory
 */
public class AlignmentServiceTest {

   @Test
   public void testNoMatch() {
       AlignmentService alignmentService = new AlignmentService();
       QueryMatch match = alignmentService.parseFastaFiles("GGGGGGGGGGGGGGGGGGGGG", new String[]{"test"});
       Assert.assertNull(match.getAssembly());
    }

    @Test
    public void testMatchValues() {
        AlignmentService alignmentService = new AlignmentService();
        QueryMatch match = alignmentService.parseFastaFiles("TGAAATACCAAAATAAATATATAGTAT", new String[]{"test"});
        Assert.assertEquals(match.getAssembly(),"test");
        Assert.assertEquals(match.getProteinId(),"P456");
        Assert.assertEquals(match.getPosition(),1);
    }
}