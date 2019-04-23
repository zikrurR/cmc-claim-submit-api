package uk.gov.hmcts.reform.cmc.submit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses=FunctionalTestApplication.class)
@SuppressWarnings("HideUtilityClassConstructor") // Spring needs a constructor, its not a utility class
public class FunctionalTestApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FunctionalTestApplication.class, args);
    }
}
