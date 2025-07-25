package alignment.services;

import alignment.model.QueryMatch;
import org.biojava.bio.BioException;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;
import org.biojava.bio.seq.io.SeqIOTools;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for aligning a query string against the protein coding sequences
 * of a set of genome assemblies.
 */

@Service
public class AlignmentService {

    // Genome assemblies that will be queried
    final String[] assemblies = {"NC_027867.1", "NC_023719.1", "NC_016072.1", "NC_023423.1", "NC_020104.1", "NC_014637.1", "NC_009899.1",
            "NC_008724.1", "NC_007346.1", "NC_000852.5"};

    /**
     * Parent method for performing an alignment for a query sequence
     *
     * @param query Query sequence
     * @return QueryMatch object holding match data
     */
    public QueryMatch doAlignment(String query){
        QueryMatch match = parseFastaFiles(query, assemblies);
        return match;
    }

        /**
     * Search across FASTA files of protein coding sequences
     *
     * @param query Query sequence
     * @return QueryMatch object
     */ /**
     * Parent method for performing an alignment for a query sequence
     *
     * @param query Query sequence
     * @return QueryMatch object holding match data
     */
    public QueryMatch parseFastaFiles(String query, String[] fastaFileNames)  {
        query = query.toUpperCase();
        QueryMatch match = new QueryMatch(query);

        for (String fastaFile : fastaFileNames) {
            InputStream isFasta = getClass().getClassLoader().getResourceAsStream("coding-sequences/" + fastaFile + ".fasta");
            match = matchFastaFile(isFasta, fastaFile, query, match);
            // If a match is found, exit
            if (match != null && !StringUtils.isEmpty(match.getAssembly())) {
                break;
            }
        }
        return match;
    }

    /**
     * Parses an input stream from a FASTA file to find a match object.
     * The file supplied is a set of coding sequences.
     *
     * Extract from a sample file -
     * >lcl|NC_000852.5_cds_YP_004678870.1_3 [gene=a002aR] [locus_tag=PBCV1_a002aR] [db_xref=GeneID:10971234] [protein=hypothetical protein] [protein_id=YP_004678870.1] [location=1022..1177] [gbkey=CDS]
     * ATGAACTCTCATATATACACGAGAGGCGGTTGTGTCAGTCATAATTTTAGTGGTAATCTCAAAGACATAG
     * GACAAAATACTCAATGGTGTCGATATGCTGAAATAATCTCGTATCGACAAACATCATCGTTGGTGATAAA
     * TCTCACAAAACTCTAA
     * >lcl|NC_000852.5_cds_YP_004678871.1_4 [gene=A002bL] [locus_tag=PBCV1_A002bL] [db_xref=GeneID:10971235] [protein=hypothetical protein] [protein_id=YP_004678871.1] [location=complement(1174..1335)] [gbkey=CDS]
     * ATGCGTGCTGTGCGTGCTGTGCTTAATAATGTCTTGATTAATATTCCTGTTGTGTCCTTAGATTTTCGTA
     * TACAACCGACGACCCCGTTTGTCGATACGAGACCAGAGACCACGAAACTCTTAATGTTTCTCCTTAGGAT
     * TTCTGCCAGAGTTTGTCCTTAG
     *
     * @param isFasta  FASTA file input stream
     * @param assembly Name of assembly representing the FASTA file
     * @param query    Query sequence
     * @param match    Initilialized match object
     * @return match   Populated match object
     */    /**
     * Parses an input stream from a FASTA file to find a match object.
     * The file supplied is a set of coding sequences.
     *
     * Extract from a sample file -
     * >lcl|NC_000852.5_cds_YP_004678870.1_3 [gene=a002aR] [locus_tag=PBCV1_a002aR] [db_xref=GeneID:10971234] [protein=hypothetical protein] [protein_id=YP_004678870.1] [location=1022..1177] [gbkey=CDS]
     * ATGAACTCTCATATATACACGAGAGGCGGTTGTGTCAGTCATAATTTTAGTGGTAATCTCAAAGACATAG
     * GACAAAATACTCAATGGTGTCGATATGCTGAAATAATCTCGTATCGACAAACATCATCGTTGGTGATAAA
     * TCTCACAAAACTCTAA
     * >lcl|NC_000852.5_cds_YP_004678871.1_4 [gene=A002bL] [locus_tag=PBCV1_A002bL] [db_xref=GeneID:10971235] [protein=hypothetical protein] [protein_id=YP_004678871.1] [location=complement(1174..1335)] [gbkey=CDS]
     * ATGCGTGCTGTGCGTGCTGTGCTTAATAATGTCTTGATTAATATTCCTGTTGTGTCCTTAGATTTTCGTA
     * TACAACCGACGACCCCGTTTGTCGATACGAGACCAGAGACCACGAAACTCTTAATGTTTCTCCTTAGGAT
     * TTCTGCCAGAGTTTGTCCTTAG
     *
     * @param isFasta  FASTA file input stream
     * @param assembly Name of assembly representing the FASTA file
     * @param query    Query sequence
     * @param match    Initilialized match object
     * @return match   Populated match object
     */
    protected QueryMatch matchFastaFile(InputStream isFasta, String assembly, String query, QueryMatch match)  {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(isFasta));
            SequenceIterator iter =
                    (SequenceIterator) SeqIOTools.fileToBiojava("fasta", "DNA", br);

            // Iterate over CDS
            while (iter.hasNext()) {
                // Sequence corresponds to the information for a protein coding sequence
                Sequence seq = iter.nextSequence();
                String seqStr = seq.seqString().toUpperCase();

                // Get description string and do string manipulation to extract protein id from it
                String proteinId = "";
                String desc = (String) seq.getAnnotation().getProperty("description");
                String split[] = desc.split("protein_id=");

                if (split != null && split.length == 2) {
                    proteinId = split[1].substring(0, split[1].indexOf("]"));

                    // Check if query string is contained in coding sequence
                    int idx = seqStr.indexOf(query);
                    if (idx != -1) {
                        match.setProteinId(proteinId);
                        match.setPosition(idx);
                        match.setAssembly(assembly);
                        return match;
                    }
                }
            }
        }
        catch(BioException ex){
            ex.printStackTrace();
        }

        return match;
    }

    /**
     * This method is not used, but is left as a means of extracting a value from a header for a CDS.
     *
     * An example header description string -
     * >lcl|NC_014637.1_cds_YP_003969646.1_14 [locus_tag=crov014] [db_xref=GeneID:9887416] [protein=hypothetical protein]
     * [protein_id=YP_003969646.1] [location=12570..12950] [gbkey=CDS]
     *
     * @param desc Header description string
     * @param key  Key object e.g. db_xref
     * @return value for key e.g. GeneID:9887416
     */
    String extractValueFromMapString(String desc, String key) {
        String str = desc.replace("[", "").replace("]", ":|").replaceAll("\\s", "");

        String[] arr = str.split(":\\|");
        Map<String, String> map = new HashMap<>();
        for (String s : arr) {
            String[] split = s.split("=");
            if (split != null && split.length == 2) {
                map.put(split[0], split[1]);
            }
        }
        String value = map.get(key);
        return value;
    }

}
