package uk.gov.hmcts.reform.cmc.submit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = SmokeTestApplication.class)
@SuppressWarnings("HideUtilityClassConstructor") // Spring needs a constructor, its not a utility class
public class SmokeTestApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SmokeTestApplication.class, args);
    }
}
