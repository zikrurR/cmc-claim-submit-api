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
import uk.gov.hmcts.reform.cmc.submit.domain.models.Claim;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.NotKnown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.ReferencePayment;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimData;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.gov.hmcts.reform.cmc.ccd.assertion.Assertions.assertThat;

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

        //

        ClaimInput validDefaults = SampleClaimData.validDefaults();
        ReferencePayment referencePayment = (ReferencePayment)validDefaults.getPayment();
        Interest interest = validDefaults.getInterest();

        // mock amount
        Map<String,Object> amountBreakDown = Maps.newHashMap();
        amountBreakDown.put("id", "359fda9d-e5fd-4d6e-9525-238642d0157d");
        Map<String,Object> amountBreakDownData = Maps.newHashMap();
        amountBreakDownData.put("reason","reason");
        amountBreakDownData.put("amount","40");
        amountBreakDown.put("value", amountBreakDownData);

        // mock evidence
        Map<String,Object> evidences = Maps.newHashMap();
        evidences.put("id", "d839f2f0-025f-4ee9-9a98-16bbe6ab3b35");
        Map<String,Object> evidencesData = Maps.newHashMap();
        evidencesData.put("type","CORRESPONDENCE");
        evidencesData.put("description","description");
        evidences.put("value", evidencesData);

        // mock timeline
        Map<String,Object> timelines = Maps.newHashMap();
        timelines.put("id", "359fda9d-e5fd-4d6e-9525-238642d0157d");
        Map<String,Object> timelinesData = Maps.newHashMap();
        timelinesData.put("description", "signed a contract");
        timelinesData.put("date", "Last Year");
        timelines.put("value", timelinesData);

        // mock applicants
        Map<String,Object> applicants = Maps.newHashMap();
        applicants.put("id", "acd82549-d279-4adc-b38c-d195dd0db0d6");
        Map<String,Object> applicantsData = Maps.newHashMap();
        applicantsData.put("partyName", "John Rambo");

        Map<String,Object> partyDetailData = Maps.newHashMap();

        Map<String,Object> primaryAddressData = Maps.newHashMap();
        primaryAddressData.put("AddressLine1", "52");
        primaryAddressData.put("AddressLine2", "Down Street");
        primaryAddressData.put("AddressLine3", "Salford");
        primaryAddressData.put("PostTown", "Manchester");
        primaryAddressData.put("PostCode", "DF1 3LJ");

        Map<String,Object> telephoneNumberData = Maps.newHashMap();
        telephoneNumberData.put("telephoneNumber", "07873727165");

        Map<String,Object> correspondenceAddressData = Maps.newHashMap();
        correspondenceAddressData.put("AddressLine1", "52");
        correspondenceAddressData.put("AddressLine2", "Down Street");
        correspondenceAddressData.put("AddressLine3", "Salford");
        correspondenceAddressData.put("PostTown", "Manchester");
        correspondenceAddressData.put("PostCode", "DF1 3LJ");

        partyDetailData.put("primaryAddress", primaryAddressData);
        partyDetailData.put("telephoneNumber", telephoneNumberData);
        partyDetailData.put("correspondenceAddress", correspondenceAddressData);
        partyDetailData.put("dateOfBirth", "1968-01-02");
        partyDetailData.put("type", "INDIVIDUAL");

        applicantsData.put("partyDetail", partyDetailData);
        Map<String,Object> contactDetailData = Maps.newHashMap();
        contactDetailData.put("phone", "7873738547");
        contactDetailData.put("email", "representative@example.com");
        contactDetailData.put("dxAddress", "DX123456");

        Map<String,Object> representativeOrganisationAddress = Maps.newHashMap();
        representativeOrganisationAddress.put("AddressLine1", "52");
        representativeOrganisationAddress.put("AddressLine2", "Down Street");
        representativeOrganisationAddress.put("AddressLine3", "Salford");
        representativeOrganisationAddress.put("PostTown", "Manchester");
        representativeOrganisationAddress.put("PostCode", "DF1 3LJ");
        applicantsData.put("representativeOrganisationAddress", representativeOrganisationAddress);
        applicantsData.put("representativeOrganisationName", "Trading ltd");
        applicantsData.put("representativeOrganisationEmail", "representative@example.org");
        applicantsData.put("representativeOrganisationDxAddress", "DX123456");
        applicantsData.put("representativeOrganisationPhone", "7873738547");

        applicants.put("value", applicantsData);

        // mock defendents
        Map<String,Object> respondents = Maps.newHashMap();
        respondents.put("id", "acd82549-d279-4adc-b38c-d195dd0db0d6");
        Map<String,Object> respondentsData = Maps.newHashMap();
        Map<String,Object> resPartyDetailData = Maps.newHashMap();

        resPartyDetailData.put("type", "INDIVIDUAL");
        respondentsData.put("claimantProvidedDetail", resPartyDetailData);
        respondents.put("value", respondentsData);

        // mock ccd call
        Map<String,Object> mandatoryData = Maps.newHashMap();
        mandatoryData.put("evidence", Arrays.asList(evidences));
        mandatoryData.put("amountBreakDown", Arrays.asList(amountBreakDown));
        mandatoryData.put("timeline", Arrays.asList(timelines));
        mandatoryData.put("respondents", Arrays.asList(respondents));
        mandatoryData.put("applicants", Arrays.asList(applicants));

        mandatoryData.put("externalId", validDefaults.getExternalId().toString());
        mandatoryData.put("referenceNumber", "random_reference_number");
        mandatoryData.put("reason", validDefaults.getReason());
        mandatoryData.put("amountType", "BREAK_DOWN");
        mandatoryData.put("interestType", interest.getType().name());
        mandatoryData.put("interestRate", interest.getRate());
        mandatoryData.put("interestReason", interest.getReason());
        mandatoryData.put("interestDateType", interest.getInterestDate().getType().name());
        mandatoryData.put("interestClaimStartDate", interest.getInterestDate().getDate());
        mandatoryData.put("interestStartDateReason", interest.getInterestDate().getReason());
        mandatoryData.put("interestEndDateType", interest.getInterestDate().getEndDateType().name());
        mandatoryData.put("paymentId", referencePayment.getId());
        mandatoryData.put("paymentAmount", referencePayment.getAmount());
        mandatoryData.put("paymentReference", referencePayment.getReference());
        mandatoryData.put("paymentStatus", referencePayment.getStatus());
        mandatoryData.put("paymentDateCreated", referencePayment.getDateCreated());
        mandatoryData.put("preferredCourt", validDefaults.getPreferredCourt());
        mandatoryData.put("personalInjuryGeneralDamages", "MORE_THAN_THOUSAND_POUNDS");
        mandatoryData.put("housingDisrepairCostOfRepairDamages", "MORE_THAN_THOUSAND_POUNDS");
        mandatoryData.put("housingDisrepairOtherDamages", "MORE_THAN_THOUSAND_POUNDS");
        mandatoryData.put("sotSignerName", validDefaults.getStatementOfTruth().getSignerName());
        mandatoryData.put("sotSignerRole", validDefaults.getStatementOfTruth().getSignerRole());

        when(coreCaseDataApi.searchCases(any(), any(), any(), any()))
             .thenReturn(SearchResult.builder().total(1)
                                               .cases(Arrays.asList(CaseDetails.builder().data(mandatoryData).build()))
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

        assertThat(validDefaults).isSameAs(claim);
    }

}
