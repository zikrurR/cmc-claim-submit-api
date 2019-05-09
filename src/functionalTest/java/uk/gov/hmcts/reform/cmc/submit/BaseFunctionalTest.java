package uk.gov.hmcts.reform.cmc.submit;

import feign.FeignException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import uk.gov.hmcts.reform.idam.client.IdamClient;
import uk.gov.hmcts.reform.idam.client.IdamTestApi;
import uk.gov.hmcts.reform.idam.client.models.UserDetails;
import uk.gov.hmcts.reform.idam.client.models.test.CreateUserRequest;
import uk.gov.hmcts.reform.idam.client.models.test.UserGroup;

import javax.annotation.PostConstruct;

@SpringBootTest
public abstract class BaseFunctionalTest {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected User citizen;


    @Value("${cmc.api.submit.url}")
    protected String baseUrl;

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
        String citizenEmail = "cmc-claim-submit-api-ccd-citizen@hmcts.net";
        try {
            idamTestApi.createUser(CreateUserRequest.builder()
                    .email(citizenEmail)
                    .userGroup(new UserGroup("citizens"))
                    .build());
        } catch (FeignException e) {
            if (e.status() == HttpStatus.BAD_REQUEST.value()) {
                log.info("Bad request from idam, probably user already exists, continuing");
            } else {
                throw e;
            }
        }

        String token = idamClient.authenticateUser(citizenEmail, CreateUserRequest.DEFAULT_PASSWORD);
        UserDetails userDetails = idamClient.getUserDetails(token);

        return new User(token, userDetails);
    }
}
