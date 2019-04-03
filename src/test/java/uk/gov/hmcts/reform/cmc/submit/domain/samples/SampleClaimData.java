package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimData;
import uk.gov.hmcts.reform.cmc.submit.domain.models.StatementOfTruth;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.Amount;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.evidence.Evidence;
import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.HousingDisrepair;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.PersonalInjury;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.Payment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.timeline.TimelineEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonList;

public class SampleClaimData {

    private UUID externalId = UUID.randomUUID();
    private List<Party> claimants = singletonList(SampleParty.individual());
    private List<TheirDetails> defendants = singletonList(SampleTheirDetails.individualDetails());
    private Payment payment = SamplePayment.validDefaults();
    private Amount amount = SampleAmountBreakdown.validDefaults();
    private Interest interest = SampleInterest.standard();
    private String reason = "reason";
    private StatementOfTruth statementOfTruth = SampleStatementOfTruth.validDefaults();
    private PersonalInjury personalInjury = SamplePersonalInjury.validDefaults();
    private String preferredCourt = "LONDON COUNTY COUNCIL";
    private List<TimelineEvent> timeline = Arrays.asList(SampleTimelineEvent.validDefaults());
    private List<Evidence> evidence = Arrays.asList(SampleEvidence.validDefaults());

    private HousingDisrepair housingDisrepair = SampleHousingDisrepair.validDefaults();

    public static SampleClaimData builder() {
        return new SampleClaimData();
    }

    public SampleClaimData withExternalId(UUID externalId) {
        this.externalId = externalId;
        return this;
    }

    public SampleClaimData withHousingDisrepair(HousingDisrepair housingDisrepair) {
        this.housingDisrepair = housingDisrepair;
        return this;
    }

    public SampleClaimData withPersonalInjury(PersonalInjury personalInjury) {
        this.personalInjury = personalInjury;
        return this;
    }

    public SampleClaimData addClaimant(Party claimant) {
        this.claimants.add(claimant);
        return this;
    }

    public SampleClaimData addClaimants(List<Party> claimants) {
        this.claimants.addAll(claimants);
        return this;
    }

    public SampleClaimData clearClaimants() {
        this.claimants = new ArrayList<>();
        return this;
    }

    public SampleClaimData clearDefendants() {
        this.defendants = new ArrayList<>();
        return this;
    }

    public SampleClaimData withClaimants(List<Party> claimants) {
        this.claimants = claimants;
        return this;
    }

    public SampleClaimData withClaimant(Party party) {
        this.claimants = singletonList(party);
        return this;
    }

    public SampleClaimData addDefendant(TheirDetails defendant) {
        this.defendants.add(defendant);
        return this;
    }

    public SampleClaimData addDefendants(List<TheirDetails> defendants) {
        this.defendants.addAll(defendants);
        return this;
    }

    public SampleClaimData withDefendants(List<TheirDetails> defendants) {
        this.defendants = defendants;
        return this;
    }

    public SampleClaimData withPayment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public SampleClaimData withDefendant(TheirDetails defendant) {
        this.defendants = singletonList(defendant);
        return this;
    }

    public SampleClaimData withInterest(Interest interest) {
        this.interest = interest;
        return this;
    }

    public SampleClaimData withReason(String reason) {
        this.reason = reason;
        return this;
    }

    public SampleClaimData withStatementOfTruth(StatementOfTruth statementOfTruth) {
        this.statementOfTruth = statementOfTruth;
        return this;
    }

    public SampleClaimData withPreferredCourt(String preferredCourt) {
        this.preferredCourt = preferredCourt;
        return this;
    }

    public SampleClaimData withAmount(Amount amount) {
        this.amount = amount;
        return this;
    }

    public SampleClaimData addTimeline(TimelineEvent timeline) {
        this.timeline.add(timeline);
        return this;
    }

    public SampleClaimData addEvidence(Evidence evidence) {
        this.evidence.add(evidence);
        return this;
    }

    public ClaimData build() {
        ClaimData claimData = new ClaimData();

        claimData.setExternalId(externalId);
        claimData.setClaimants(claimants);
        claimData.setDefendants(defendants);
        claimData.setPayment(payment);
        claimData.setAmount(amount);
        claimData.setInterest(interest);
        claimData.setPersonalInjury(personalInjury);
        claimData.setHousingDisrepair(housingDisrepair);
        claimData.setReason(reason);
        claimData.setStatementOfTruth(statementOfTruth);
        claimData.setPreferredCourt(preferredCourt);
        claimData.setTimeline(timeline);
        claimData.setEvidences(evidence);

        return claimData;
    }

    public static ClaimData validDefaults() {
        return builder().build();
    }
}
