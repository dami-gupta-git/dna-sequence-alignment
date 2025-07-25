package alignment.model;

/**
 *  The QueryMatch object encapsulates information for a query alignment.
 *
 */
public class QueryMatch {

    // Query sequence
    String query;

    // Protein id for CDS on which match was found
    String proteinId;

    // Position of query string on coding  sequence
    int position = -1;

    // Assembly corresponding to the FASTA file in which match was found
    String assembly;

    // Getter, Setters

    public String getAssembly() {
        return assembly;
    }

    public void setAssembly(String assembly) {
        this.assembly = assembly;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPosition() {
        return position;
    }


    public void setPosition(int position) {
        this.position = position;
    }

    public String getProteinId() {
        return proteinId;
    }

    public void setProteinId(String proteinId) {
        this.proteinId = proteinId;
    }

    public QueryMatch(String query){
        this.setQuery(query);
    }

}
