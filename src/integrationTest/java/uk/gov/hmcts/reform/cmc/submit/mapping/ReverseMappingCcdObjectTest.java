package uk.gov.hmcts.reform.cmc.submit.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.converter.ClaimConverter;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimImput;
import uk.gov.hmcts.reform.cmc.submit.merger.MergeCaseData;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReverseMappingCcdObjectTest {

    @Autowired
    private MergeCaseData mergeCaseData;

    @Autowired
    private ClaimConverter claimConverter;

    @Test
    @DisplayName("Should map and reverse map a simple claim imput - valid default")
    public void shouldMapSimpleClaimImput() {

        CcdCaseBuilder ccdCaseBuilder = CcdCaseBuilder.builder();

        ClaimInput claim = SampleClaimImput.validDefaults();

        mergeCaseData.merge(ccdCaseBuilder, claim);

        CcdCase build = ccdCaseBuilder.build();

        assertThat(claim).isEqualToComparingFieldByFieldRecursively(claimConverter.from(build));
    }

    @Test
    @DisplayName("Should map and reverse map a simple claim imput - valid organisation claim")
    public void shouldMapOrganisationClaimImput() {

        CcdCaseBuilder ccdCaseBuilder = CcdCaseBuilder.builder();

        ClaimInput claim = SampleClaimImput.validOrganisationClaim();

        mergeCaseData.merge(ccdCaseBuilder, claim);

        CcdCase build = ccdCaseBuilder.build();

        assertThat(claim).isEqualToComparingFieldByFieldRecursively(claimConverter.from(build));
    }

    @Test
    @DisplayName("Should map and reverse map a simple claim imput - valid company claim")
    public void shouldMapCompanyClaimImput() {

        CcdCaseBuilder ccdCaseBuilder = CcdCaseBuilder.builder();

        ClaimInput claim = SampleClaimImput.validCompanyClaim();

        mergeCaseData.merge(ccdCaseBuilder, claim);

        CcdCase build = ccdCaseBuilder.build();

        assertThat(claim).isEqualToComparingFieldByFieldRecursively(claimConverter.from(build));
    }

    @Test
    @DisplayName("Should map and reverse map a simple claim imput - valid sole trader claim")
    public void shouldMapSoleTraderClaimImput() {

        CcdCaseBuilder ccdCaseBuilder = CcdCaseBuilder.builder();

        ClaimInput claim = SampleClaimImput.validSoleTraderClaim();

        mergeCaseData.merge(ccdCaseBuilder, claim);

        CcdCase build = ccdCaseBuilder.build();

        assertThat(claim).isEqualToComparingFieldByFieldRecursively(claimConverter.from(build));
    }
}