package org.mattpayne.simple;

import org.mattpayne.simple.service.PgpService;
import static org.mattpayne.simple.service.UtilsFromStackOverflow.getResourceFileAsString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SimpleApplication implements CommandLineRunner {

    private final PgpService pgpService;

    public SimpleApplication(PgpService pgpService) {
        this.pgpService = pgpService;
    }

    public static void main(final String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String encryptedString = getResourceFileAsString("encrypted.asc");
        String clearText = pgpService.decrypt(encryptedString);
        System.out.println("\n\n\nclearText is:");
        System.out.println(clearText);
        System.out.println("\n\n");
    }
}
