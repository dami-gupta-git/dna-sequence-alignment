package alignment.controllers;

import com.google.gson.Gson;
import alignment.model.AlignmentQuery;
import alignment.model.QueryMatch;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;

import java.net.URI;

// Start the server with a random port
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AlignmentRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void indexPageShouldReturnDefaultMessage() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/";
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = template.getForEntity(uri, String.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().equals("You have reached the Alignment App"));
    }

    @Test
    public void alignPostShouldReturnNonNullObject() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/alignAsync";
        URI uri = new URI(baseUrl);

        AlignmentQuery qry = new AlignmentQuery();
        qry.setSequence("ATGAC");
        String qryString = new Gson().toJson(qry);

        // OK status expected
        ResponseEntity<String> result = template.postForEntity(uri, qry, String.class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        // Return object is not null
        Assert.assertNotNull(result.getBody());
    }

    @Test
    public void alignPostSequenceShouldMatch() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/alignAsync";
        URI uri = new URI(baseUrl);

        AlignmentQuery qry = new AlignmentQuery();
        qry.setSequence("ATGCCATGGGACGAAACCACAC");

        // Irrespective of whether there is a match, the returned object should have the original
        // query sequence
        ResponseEntity<String> result = template.postForEntity(uri, qry, String.class);
        QueryMatch match = new Gson().fromJson(result.getBody(), QueryMatch.class);
        Assert.assertEquals(match.getQuery(), "ATGCCATGGGACGAAACCACAC");
    }

    @Test
    public void alignPostNoMatch() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/alignAsync";
        URI uri = new URI(baseUrl);

        // Known that this sequence does not have a match
        AlignmentQuery qry = new AlignmentQuery();
        qry.setSequence("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        ResponseEntity<String> result = template.postForEntity(uri, qry, String.class);
        QueryMatch match = new Gson().fromJson(result.getBody(), QueryMatch.class);
        Assert.assertNull(match.getAssembly());
    }

    @Test
    public void alignPostMatch() throws Exception {
        final String baseUrl = "http://localhost:" + port + "/alignAsync";
        URI uri = new URI(baseUrl);

        AlignmentQuery qry = new AlignmentQuery();
        // Match results for this sequence is known
        qry.setSequence("GAATATTATCATGTCAAAATTAGA");

        ResponseEntity<String> result = template.postForEntity(uri, qry, String.class);
        QueryMatch match = new Gson().fromJson(result.getBody(), QueryMatch.class);
        Assert.assertEquals( match.getQuery(), "GAATATTATCATGTCAAAATTAGA");
        Assert.assertEquals( match.getProteinId(), "YP_003969634.1");
        Assert.assertEquals(match.getPosition(), 2);
        Assert.assertEquals(match.getAssembly(), "NC_014637.1");
    }

    // TODO test that multiple async POST requests run simultaneously
}
