package uk.gov.hmcts.reform.cmc.submit.domain.models;

import lombok.Data;

import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.Amount;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.Evidence;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.HousingDisrepair;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.PersonalInjury;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.Payment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.timeline.TimelineEvent;

import java.util.List;
import java.util.UUID;

@Data
public class Claim {

    private String referenceNumber;

    private UUID externalId;

    private List<Party> claimants;

    private List<TheirDetails> defendants;

    private Payment payment;

    private Amount amount;

    private Interest interest;

    private PersonalInjury personalInjury;

    private HousingDisrepair housingDisrepair;

    private List<TimelineEvent> timeline;

    private List<Evidence> evidences;

    private String reason;

    private StatementOfTruth statementOfTruth;

    private String preferredCourt;
}
