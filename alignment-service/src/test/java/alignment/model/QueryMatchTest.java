package alignment.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryMatchTest {

    @Test
    void testAssemblyAndQuery() {
        QueryMatch qry = new QueryMatch("ATGCA");
        qry.setAssembly("NC_014637.1");

        Assert.assertEquals(qry.getAssembly(),"NC_014637.1" );
        Assert.assertEquals(qry.getQuery(),"ATGCA" );
    }


}