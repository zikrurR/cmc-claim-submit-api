package uk.gov.hmcts.reform.cmc.submit;

import feign.FeignException;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import uk.gov.hmcts.reform.idam.client.IdamClient;
import uk.gov.hmcts.reform.idam.client.IdamTestApi;
import uk.gov.hmcts.reform.idam.client.models.test.CreateUserRequest;
import uk.gov.hmcts.reform.idam.client.models.test.UserGroup;

import java.io.IOException;

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

    protected String getClaimEndPoint;

    protected RestTemplate restTemplate;

    @Autowired
    protected IdamClient idamClient;

    @Autowired
    protected IdamTestApi idamTestApi;

    @Autowired // httpClient form Feign.
    protected HttpClient httpClient;


    // Might be better to use TestRestTemplate how ever it does the same
    private static class NoOpResponseErrorHandler extends DefaultResponseErrorHandler {
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
        }
    }

    @PostConstruct
    public void init() {
        getClaimEndPoint = baseUrl + "/claim/{externalIdentifier}";

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new NoOpResponseErrorHandler());

    }

    public String citizenToken() {

        try {
            idamTestApi.createUser(CreateUserRequest.builder()
                    .email(citizenUsername)
                    .password(citizenPassword)
                    .userGroup(new UserGroup("citizens"))
                    .build());
        } catch (FeignException e) {
            if (e.status() == HttpStatus.FORBIDDEN.value()) {
                log.info("Bad request from idam, probably user already exists, continuing");
            } else {
                throw e;
            }
        }

        return idamClient.authenticateUser(citizenUsername, citizenPassword);
    }
}
