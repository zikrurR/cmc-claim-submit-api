package uk.gov.hmcts.reform.cmc.submit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import uk.gov.hmcts.reform.idam.client.IdamClient;
import uk.gov.hmcts.reform.idam.client.OAuth2Configuration;

import javax.annotation.PostConstruct;

@SpringBootTest
public abstract class BaseSmokeTest {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${cmc.api.submit.url}")
    protected String baseUrl;

    @Value("${cmc.test.smoke.username}")
    protected String citizenUsername;

    @Value("${cmc.test.smoke.password}")
    protected String citizenPassword;

    protected String postClaimEndPoint;
    protected String getClaimEndPoint;

    @Autowired
    protected IdamClient idamClient;

    @Autowired
    protected OAuth2Configuration oauth2Configuration;

    @PostConstruct
    public void init() {
        getClaimEndPoint = baseUrl + "/claim/{externalIdentifier}";
    }

    public String citizenToken() {

        log.info(new StringBuilder(citizenUsername).reverse().toString() + " : " + new StringBuilder(citizenPassword).reverse().toString());
        log.info(new StringBuilder(oauth2Configuration.getClientId()).reverse().toString() + " : " + new StringBuilder(oauth2Configuration.getClientSecret()).reverse().toString());
        return idamClient.authenticateUser(citizenUsername, citizenPassword);
    }
}
