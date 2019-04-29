package uk.gov.hmcts.reform.cmc.submit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import uk.gov.hmcts.reform.idam.client.IdamClient;
import uk.gov.hmcts.reform.idam.client.models.test.CreateUserRequest;

import javax.annotation.PostConstruct;

@SpringBootTest
public abstract class BaseSmokeTest {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${cmc.api.submit.url}")
    protected String baseUrl;

    protected String postClaimEndPoint;
    protected String getClaimEndPoint;

    @Autowired
    protected IdamClient idamClient;

    @PostConstruct
    public void init() {
        getClaimEndPoint = baseUrl + "/claim/{externalIdentifier}";
    }

    public String citizenToken() {
        String citizenEmail = "cmc-claim-submit-api-ccd-citizen@hmcts.net";
        String defaultPassword = CreateUserRequest.DEFAULT_PASSWORD;

        return idamClient.authenticateUser(citizenEmail, defaultPassword);
    }
}
