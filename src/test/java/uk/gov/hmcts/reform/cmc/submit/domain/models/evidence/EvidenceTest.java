package uk.gov.hmcts.reform.cmc.submit.domain.models.evidence;

import org.junit.Test;

import uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.Evidence;

import java.util.Set;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.repeat;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.cmc.submit.domain.BeanValidator.validate;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.EvidenceType.CONTRACTS_AND_AGREEMENTS;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.EvidenceType.EXPERT_WITNESS;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.EvidenceType.OTHER;
import static uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.EvidenceType.STATEMENT_OF_ACCOUNT;

public class EvidenceTest {

    @Test
    public void shouldBeSuccessfulValidationForCorrectEvidence() {

        Evidence evidence = new Evidence();
        evidence.setType(EXPERT_WITNESS);
        evidence.setDescription("description");

        Set<String> response = validate(evidence);

        assertThat(response).hasSize(0);
    }

    @Test
    public void shouldBeSuccessfulValidationForNullDescription() {

        Evidence evidence = new Evidence();
        evidence.setType(STATEMENT_OF_ACCOUNT);
        evidence.setDescription(null);

        Set<String> response = validate(evidence);

        assertThat(response).hasSize(0);
    }

    @Test
    public void shouldBeSuccessfulValidationForEmptyDescription() {

        Evidence evidence = new Evidence();
        evidence.setType(CONTRACTS_AND_AGREEMENTS);
        evidence.setDescription("");

        Set<String> response = validate(evidence);

        assertThat(response).hasSize(0);
    }

    @Test
    public void shouldFailValidationForNullEvidenceType() {

        Evidence evidence = new Evidence();
        evidence.setType(null);
        evidence.setDescription("description");

        Set<String> response = validate(evidence);

        assertThat(response)
            .hasSize(1)
            .contains("type : must not be null");
    }

    @Test
    public void shouldFailValidationForTooLongDescription() {

        Evidence evidence = new Evidence();
        evidence.setId(UUID.randomUUID().toString());
        evidence.setType(OTHER);
        evidence.setDescription(repeat("a", 99001));


        Set<String> response = validate(evidence);

        assertThat(response)
            .hasSize(1)
            .contains("description : size must be between 0 and 99000");
    }
}
