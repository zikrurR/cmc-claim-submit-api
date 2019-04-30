package uk.gov.hmcts.reform.cmc.submit.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import uk.gov.hmcts.reform.cmc.submit.converter.ClaimConverter;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.samples.SampleClaimImput;
import uk.gov.hmcts.reform.cmc.submit.merger.MergeCaseData;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReverseMappingTest {

    @Autowired
    private MergeCaseData mergeCaseData;

    @Autowired
    private ClaimConverter claimConverter;

    @Test
    @DisplayName("Should map and reverse map a simple claim imput - valid default")
    public void shouldMapSimpleClaimImput() {


        Map<String, Object> initData = new HashMap<>();

        ClaimInput claim = SampleClaimImput.validDefaults();

        Map<String, Object> merge = mergeCaseData.merge(initData, claim);

        assertThat(claim).isEqualToComparingFieldByFieldRecursively(claimConverter.convert(merge));
    }

    @Test
    @DisplayName("Should map and reverse map a simple claim imput - valid organisation claim")
    public void shouldMapOrganisationClaimImput() {


        Map<String, Object> initData = new HashMap<>();

        ClaimInput claim = SampleClaimImput.validOrganisationClaim();

        Map<String, Object> merge = mergeCaseData.merge(initData, claim);

        assertThat(claim).isEqualToComparingFieldByFieldRecursively(claimConverter.convert(merge));
    }

    @Test
    @DisplayName("Should map and reverse map a simple claim imput - valid company claim")
    public void shouldMapCompanyClaimImput() {


        Map<String, Object> initData = new HashMap<>();

        ClaimInput claim = SampleClaimImput.validCompanyClaim();

        Map<String, Object> merge = mergeCaseData.merge(initData, claim);

        assertThat(claim).isEqualToComparingFieldByFieldRecursively(claimConverter.convert(merge));
    }

    @Test
    @DisplayName("Should map and reverse map a simple claim imput - valid sole trader claim")
    public void shouldMapSoleTraderClaimImput() {


        Map<String, Object> initData = new HashMap<>();

        ClaimInput claim = SampleClaimImput.validSoleTraderClaim();

        Map<String, Object> merge = mergeCaseData.merge(initData, claim);

        assertThat(claim).isEqualToComparingFieldByFieldRecursively(claimConverter.convert(merge));
    }
}