package uk.gov.hmcts.reform.cmc.submit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import uk.gov.hmcts.reform.idam.client.IdamClient;
import uk.gov.hmcts.reform.idam.client.IdamTestApi;
import uk.gov.hmcts.reform.idam.client.models.UserDetails;

import javax.annotation.PostConstruct;

@SpringBootTest
public abstract class BaseFunctionalTest {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected User citizen;


    @Value("${cmc.api.submit.url}")
    protected String baseUrl;

    @Value("${cmc.test.functional.username}")
    protected String citizenUsername;

    @Value("${cmc.test.functional.password}")
    protected String citizenPassword;

    protected String postClaimEndPoint;
    protected String getClaimEndPoint;

    @Autowired
    protected IdamClient idamClient;

    @Autowired
    protected IdamTestApi idamTestApi;

    @PostConstruct
    public void init() {
        postClaimEndPoint = baseUrl + "/claim";
        getClaimEndPoint = baseUrl + "/claim/{externalIdentifier}";
    }

    public User citizen() {

        String token = idamClient.authenticateUser(citizenUsername, citizenPassword);
        UserDetails userDetails = idamClient.getUserDetails(token);

        return new User(token, userDetails);
    }
}
