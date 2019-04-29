package uk.gov.hmcts.reform.cmc.submit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CoreCaseDataApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.ccd.client.model.SearchResult;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.AmountType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAmountRow;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdApplicant;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdEvidenceRow;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdEvidenceType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdInterestDateType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdInterestEndDateType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdInterestType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdRespondent;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdTimelineEvent;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdAddressBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdAmountRowBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdApplicantBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCollectionElementBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdEvidenceRowBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdPartyBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdRespondentBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdTelephoneBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdTimelineEventBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.Claim;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRow;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.NotKnown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.Evidence;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.ReferencePayment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.timeline.TimelineEvent;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetClaimIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoreCaseDataApi coreCaseDataApi;

    @MockBean
    private AuthTokenGenerator authTokenGenerator;

    protected static final String AUTHORISATION_TOKEN = "Bearer token";

    @DisplayName("Happy path should return a claim requested with 200 response code")
    @Test
    public void happyPathGetClaim() throws Exception {

        // mock ccd call
        Map<String,Object> mandatoryData = Maps.newHashMap();
        String externalId = UUID.randomUUID().toString();
        mandatoryData.put("externalId", externalId);
        mandatoryData.put("amountType", "NOT_KNOWN");
        mandatoryData.put("referenceNumber", "random_reference_number");

        when(coreCaseDataApi.searchCases(any(), any(), any(), any()))
             .thenReturn(SearchResult.builder().total(1)
                                               .cases(Arrays.asList(CaseDetails.builder().data(mandatoryData).build()))
                                               .build());

        // mock idam call
        when(authTokenGenerator.generate()).thenReturn("aaa");

        MvcResult response = mockMvc
                .perform(get("/claim/{externalId}",externalId)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORISATION_TOKEN))
                    .andExpect(status().isOk())
                    .andReturn();

        Claim claim = objectMapper.readValue(response.getResponse().getContentAsString(), Claim.class);

        assertThat(claim.getAmount().getClass()).isEqualTo(NotKnown.class);
    }


    @DisplayName("The mapping data should return a claim requested with 200 response code")
    @Test
    public void mappingDataTest() throws Exception {

        ClaimInput validDefaults = SampleClaimData.validDefaults();

        // mock ccd call
        CcdCaseBuilder builder = CcdCaseBuilder.builder();

        Individual individual = (Individual)validDefaults.getClaimants().get(0);
        builder.applicants(Arrays.asList(CcdCollectionElementBuilder.<CcdApplicant>builder()
                .id(individual.getId())
                .value(CcdApplicantBuilder.builder()
                        .partyName("John Rambo")
                        .representativeOrganisationName("Trading ltd")
                        .representativeOrganisationEmail("representative@example.org")
                        .representativeOrganisationDxAddress("DX123456")
                        .representativeOrganisationPhone("7873738547")
                        .representativeOrganisationAddress(CcdAddressBuilder.builder()
                                .addressLine1("52")
                                .addressLine2("Down Street")
                                .addressLine3("Salford")
                                .postTown("Manchester")
                                .postCode("DF1 3LJ"))
                        .partyDetail(CcdPartyBuilder.builder()
                                .dateOfBirth(LocalDate.parse("1968-01-02"))
                                .type(CcdPartyType.INDIVIDUAL)
                                .telephoneNumber(CcdTelephoneBuilder.builder()
                                        .telephoneNumber("07873727165"))
                                .correspondenceAddress(CcdAddressBuilder.builder()
                                        .addressLine1("52")
                                        .addressLine2("Down Street")
                                        .addressLine3("Salford")
                                        .postTown("Manchester")
                                        .postCode("DF1 3LJ"))
                                .primaryAddress(CcdAddressBuilder.builder()
                                        .addressLine1("52")
                                        .addressLine2("Down Street")
                                        .addressLine3("Salford")
                                        .postTown("Manchester")
                                        .postCode("DF1 3LJ"))
                                )
                        )

                ));

        Evidence evidence = validDefaults.getEvidences().get(0);
        builder.evidence(Arrays.asList(CcdCollectionElementBuilder.<CcdEvidenceRow>builder()
                .id(evidence.getId())
                .value(CcdEvidenceRowBuilder.builder()
                        .type(CcdEvidenceType.CORRESPONDENCE)
                        .description(evidence.getDescription()))
                ));

        AmountRow amountRow = ((AmountBreakDown)validDefaults.getAmount()).getRows().get(0);
        builder.amountBreakDown(Arrays.asList(CcdCollectionElementBuilder.<CcdAmountRow>builder()
                .id(amountRow.getId())
                .value(CcdAmountRowBuilder.builder()
                        .reason(amountRow.getReason())
                        .amount(amountRow.getAmount()))
                ));

        TimelineEvent timeline = validDefaults.getTimeline().get(0);
        builder.timeline(Arrays.asList(CcdCollectionElementBuilder.<CcdTimelineEvent>builder()
                .id(timeline.getId())
                .value(CcdTimelineEventBuilder.builder()
                        .description(timeline.getDescription())
                        .date(timeline.getDate()))
                ));

        IndividualDetails individualDetails = (IndividualDetails)validDefaults.getDefendants().get(0);
        builder.respondents(Arrays.asList(CcdCollectionElementBuilder.<CcdRespondent>builder()
                .id(individualDetails.getId())
                .value(CcdRespondentBuilder.builder()
                        .claimantProvidedDetail(CcdPartyBuilder.builder()
                                .type(CcdPartyType.INDIVIDUAL)
                                .emailAddress("j.smith@example.com")
                                .primaryAddress(CcdAddressBuilder.builder()
                                        .addressLine1("52")
                                        .addressLine2("Down Street")
                                        .addressLine3("Salford")
                                        .postTown("Manchester")
                                        .postCode("DF1 3LJ"))
                                .lastName("Smith")
                                .firstName("John")
                                .title("Dr.")
                                ))

                ));

        ReferencePayment referencePayment = (ReferencePayment)validDefaults.getPayment();
        Interest interest = validDefaults.getInterest();

        builder.externalId(validDefaults.getExternalId().toString());
        builder.referenceNumber("random_reference_number");
        builder.reason(validDefaults.getReason());
        builder.amountType(AmountType.BREAK_DOWN);
        builder.interestType(CcdInterestType.valueOf(interest.getType().name()));
        builder.interestRate(interest.getRate());
        builder.interestReason(interest.getReason());
        builder.interestDateType(CcdInterestDateType.valueOf(interest.getInterestDate().getType().name()));
        builder.interestClaimStartDate(interest.getInterestDate().getDate());
        builder.interestStartDateReason(interest.getInterestDate().getReason());
        builder.interestEndDateType(CcdInterestEndDateType.valueOf(interest.getInterestDate().getEndDateType().name()));
        builder.paymentId(referencePayment.getId());
        builder.paymentAmount(referencePayment.getAmount());
        builder.paymentReference(referencePayment.getReference());
        builder.paymentStatus(referencePayment.getStatus());
        builder.paymentDateCreated(LocalDate.parse(referencePayment.getDateCreated()));
        builder.preferredCourt(validDefaults.getPreferredCourt());
        builder.personalInjuryGeneralDamages("MORE_THAN_THOUSAND_POUNDS");
        builder.housingDisrepairCostOfRepairDamages("MORE_THAN_THOUSAND_POUNDS");
        builder.housingDisrepairOtherDamages("MORE_THAN_THOUSAND_POUNDS");
        builder.sotSignerName(validDefaults.getStatementOfTruth().getSignerName());
        builder.sotSignerRole(validDefaults.getStatementOfTruth().getSignerRole());

        when(coreCaseDataApi.searchCases(any(), any(), any(), any()))
             .thenReturn(SearchResult.builder().total(1)
                                               .cases(Arrays.asList(CaseDetails.builder().data(builder.buildMap()).build()))
                                               .build());

        // mock idam call
        when(authTokenGenerator.generate()).thenReturn("aaa");


        MvcResult response = mockMvc
                .perform(get("/claim/{externalId}", validDefaults.getExternalId().toString())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORISATION_TOKEN))
                    .andExpect(status().isOk())
                    .andReturn();

        Claim claim = objectMapper.readValue(response.getResponse().getContentAsString(), Claim.class);

        assertThat(validDefaults).isEqualToComparingFieldByFieldRecursively(claim);
    }



}
