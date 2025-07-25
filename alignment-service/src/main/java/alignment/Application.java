package alignment;

import org.biojava.bio.BioException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException, BioException {
        SpringApplication.run(Application.class, args);
        System.out.println("Started Alignment App....");
    }

}