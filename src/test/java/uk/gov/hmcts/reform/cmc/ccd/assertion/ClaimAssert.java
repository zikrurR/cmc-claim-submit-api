package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAmountRow;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.StatementOfTruth;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.Amount;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRange;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRow;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.NotKnown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.DamagesExpectation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.HousingDisrepair;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.PersonalInjury;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.AccountPayment;
import uk.gov.hmcts.reform.cmc.submit.domain.models.payment.ReferencePayment;

import java.time.LocalDate;
import java.util.Objects;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;

public class ClaimAssert extends AbstractAssert<ClaimAssert, ClaimInput> {

    public ClaimAssert(ClaimInput actual) {
        super(actual, ClaimAssert.class);
    }

    public ClaimAssert isEqualTo(CcdCase ccdCase) {
        isNotNull();

//////////
//    NEED TO DECIDED WHTA TO DO WHIT THAT FIELD
//////////
//        if (!Objects.equals(actual.getReferenceNumber(), ccdCase.getReferenceNumber())) {
//            failWithMessage("Expected CcdCase.referenceNumber to be <%s> but was <%s>",
//                ccdCase.getReferenceNumber(), actual.getReferenceNumber());
//        }
//
//        if (!Objects.equals(actual.getSubmitterId(), ccdCase.getSubmitterId())) {
//            failWithMessage("Expected CcdCase.submitterId to be <%s> but was <%s>",
//                ccdCase.getSubmitterId(), actual.getSubmitterId());
//        }
//
//        if (!Objects.equals(actual.getCreatedAt(), ccdCase.getSubmittedOn())) {
//            failWithMessage("Expected CcdCase.submittedOn to be <%s> but was <%s>",
//                ccdCase.getSubmittedOn(), actual.getCreatedAt());
//        }
//
//        if (!Objects.equals(actual.getExternalId(), ccdCase.getExternalId())) {
//            failWithMessage("Expected CcdCase.externalId to be <%s> but was <%s>",
//                ccdCase.getExternalId(), actual.getExternalId());
//        }
//
//        if (!Objects.equals(actual.getIssuedOn(), ccdCase.getIssuedOn())) {
//            failWithMessage("Expected CcdCase.issuedOn to be <%s> but was <%s>",
//                ccdCase.getIssuedOn(), actual.getIssuedOn());
//        }
//
//        if (!Objects.equals(actual.getSubmitterEmail(), ccdCase.getSubmitterEmail())) {
//            failWithMessage("Expected CcdCase.submitterEmail to be <%s> but was <%s>",
//                ccdCase.getSubmitterEmail(), actual.getSubmitterEmail());
//        }

        if (!Objects.equals(actual.getReason(), ccdCase.getReason())) {
            failWithMessage("Expected CcdClaim.reason to be <%s> but was <%s>",
                ccdCase.getReason(), actual.getReason());
        }


        if (!Objects.equals(actual.getExternalId().toString(), ccdCase.getExternalId())) {
            failWithMessage("Expected CcdClaim.externalId to be <%s> but was <%s>",
                ccdCase.getExternalId(), actual.getExternalId().toString());
        }

        if (!Objects.equals(actual.getPreferredCourt(), ccdCase.getPreferredCourt())) {
            failWithMessage("Expected CcdClaim.preferredCourt to be <%s> but was <%s>",
                ccdCase.getPreferredCourt(), actual.getPreferredCourt());
        }

        Amount amount = actual.getAmount();
        if (amount instanceof AmountBreakDown) {
            AmountBreakDown amountBreakDown = (AmountBreakDown) amount;

            AmountRow amountRow = amountBreakDown.getRows().get(0);
            CcdAmountRow ccdAmountRow = ccdCase.getAmountBreakDown().get(0).getValue();

            if (!Objects.equals(amountRow.getReason(), ccdAmountRow.getReason())) {
                failWithMessage("Expected CcdCase.amountRowReason to be <%s> but was <%s>",
                    ccdAmountRow.getReason(), amountRow.getReason());
            }

            if (!Objects.equals(amountRow.getAmount(), ccdAmountRow.getAmount())) {
                failWithMessage("Expected CcdCase.amount to be <%s> but was <%s>",
                    ccdAmountRow.getAmount(), amountRow.getAmount());
            }

        } else if (amount instanceof AmountRange) {

            AmountRange amountRange = (AmountRange) amount;

            if (!Objects.equals(amountRange.getHigherValue(), ccdCase.getAmountHigherValue())) {
                failWithMessage("Expected CcdCase.amountHigherValue to be <%s> but was <%s>",
                    ccdCase.getAmountHigherValue(), amountRange.getHigherValue());
            }

            if (!Objects.equals(amountRange.getLowerValue(), ccdCase.getAmountLowerValue())) {
                failWithMessage("Expected CcdCase.amountLowerValue to be <%s> but was <%s>",
                    ccdCase.getAmountLowerValue(), amountRange.getLowerValue());
            }

        } else {
            assertThat(amount).isInstanceOf(NotKnown.class);
        }

        ofNullable(actual.getInterest()).ifPresent(interest -> {
                if (!Objects.equals(interest.getRate(), ccdCase.getInterestRate())) {
                    failWithMessage("Expected CcdCase.interestRate to be <%s> but was <%s>",
                        ccdCase.getInterestRate(), interest.getRate());
                }
                if (!Objects.equals(interest.getType().name(), ccdCase.getInterestType().name())) {
                    failWithMessage("Expected CcdCase.interestType to be <%s> but was <%s>",
                        ccdCase.getInterestType(), interest.getType());
                }
                if (!Objects.equals(interest.getReason(), ccdCase.getInterestReason())) {
                    failWithMessage("Expected CcdCase.interestReason to be <%s> but was <%s>",
                        ccdCase.getInterestReason(), interest.getRate());
                }
                if (!Objects.equals(interest.getSpecificDailyAmount(), ccdCase.getInterestSpecificDailyAmount())) {
                    failWithMessage("Expected CcdCase.interestSpecificDailyAmount to be <%s> but was <%s>",
                        ccdCase.getInterestSpecificDailyAmount(), interest.getSpecificDailyAmount());

                }

                ofNullable(interest.getInterestDate()).ifPresent(interestDate -> {
                    if (!Objects.equals(interestDate.getDate(), ccdCase.getInterestClaimStartDate())) {
                        failWithMessage("Expected CcdCase.interestClaimStartDate to be <%s> but was <%s>",
                            ccdCase.getInterestClaimStartDate(), interestDate.getDate());
                    }
                    if (!Objects.equals(interestDate.getType().name(), ccdCase.getInterestDateType().name())) {
                        failWithMessage("Expected CcdCase.interestDateType to be <%s> but was <%s>",
                            ccdCase.getInterestDateType(), interestDate.getType());
                    }

                    if (!Objects.equals(interestDate.getReason(), ccdCase.getInterestStartDateReason())) {
                        failWithMessage("Expected CcdCase.interestStartDateReason to be <%s> but was <%s>",
                            ccdCase.getInterestStartDateReason(), interestDate.getReason());
                    }

                    if (!Objects.equals(interestDate.getEndDateType().name(),
                        ccdCase.getInterestEndDateType().name())
                    ) {
                        failWithMessage("Expected CcdCase.interestEndDateType to be <%s> but was <%s>",
                            ccdCase.getInterestEndDateType(), interestDate.getEndDateType());
                    }
                });
            }
        );

        ofNullable(actual.getPayment())
            .filter(ReferencePayment.class::isInstance)
            .map(obj -> (ReferencePayment) obj)
            .ifPresent(payment -> {
                if (!Objects.equals(payment.getId(), ccdCase.getPaymentId())) {
                    failWithMessage("Expected CcdCase.paymentId to be <%s> but was <%s>",
                        ccdCase.getPaymentId(), payment.getId());
                }
                if (!Objects.equals(payment.getReference(), ccdCase.getPaymentReference())) {
                    failWithMessage("Expected CcdCase.paymentReference to be <%s> but was <%s>",
                        ccdCase.getPaymentReference(), payment.getReference());
                }
                if (!Objects.equals(payment.getAmount(), ccdCase.getPaymentAmount())) {
                    failWithMessage("Expected CcdCase.paymentAmount to be <%s> but was <%s>",
                        ccdCase.getPaymentAmount(), payment.getAmount());
                }
                if (!Objects.equals(LocalDate.parse(payment.getDateCreated(), ISO_DATE),
                    ccdCase.getPaymentDateCreated())
                ) {
                    failWithMessage("Expected CcdCase.paymentDateCreated to be <%s> but was <%s>",
                        ccdCase.getPaymentDateCreated(), payment.getDateCreated());
                }
                if (!Objects.equals(payment.getStatus(), ccdCase.getPaymentStatus())) {
                    failWithMessage("Expected CcdCase.paymentStatus to be <%s> but was <%s>",
                        ccdCase.getPaymentStatus(), payment.getStatus());
                }
            }
        );

        ofNullable(actual.getPayment())
            .filter(AccountPayment.class::isInstance)
            .map(obj -> (AccountPayment) obj)
            .ifPresent(payment -> {
                if (!Objects.equals(payment.getFeeAccountNumber(), ccdCase.getFeeAccountNumber())) {
                    failWithMessage("Expected CcdCase.feeAccountNumber to be <%s> but was <%s>",
                        ccdCase.getPaymentId(), payment.getFeeAccountNumber());
                }
            }
        );


        PersonalInjury personalInjury = actual.getPersonalInjury();
        if (personalInjury != null) {
            if (!Objects.equals(personalInjury.getGeneralDamages().name(), ccdCase.getPersonalInjuryGeneralDamages())) {
                failWithMessage("Expected CcdCase.personalInjuryGeneralDamages to be <%s> but was <%s>",
                    ccdCase.getPersonalInjuryGeneralDamages(), personalInjury.getGeneralDamages());
            }
        }

        if (actual.getHousingDisrepair() != null) {
            HousingDisrepair housingDisrepair = actual.getHousingDisrepair();
            if (!Objects.equals(housingDisrepair.getCostOfRepairsDamages().name(),
                ccdCase.getHousingDisrepairCostOfRepairDamages())
            ) {
                failWithMessage("Expected CcdCase.housingDisrepairCostOfRepairDamages to be <%s> but was <%s>",
                    ccdCase.getHousingDisrepairCostOfRepairDamages(), housingDisrepair.getCostOfRepairsDamages());
            }

            if (housingDisrepair.getOtherDamages() != null) {
                DamagesExpectation damagesExpectation = housingDisrepair.getOtherDamages();
                if (!Objects.equals(damagesExpectation.name(), ccdCase.getHousingDisrepairOtherDamages())) {
                    failWithMessage("Expected CcdCase.housingDisrepairOtherDamages to be <%s> but was <%s>",
                        ccdCase.getHousingDisrepairOtherDamages(), damagesExpectation.name());
                }
            }

        }

        if (actual.getStatementOfTruth() != null) {
            StatementOfTruth statementOfTruth = actual.getStatementOfTruth();
            if (!Objects.equals(statementOfTruth.getSignerName(), ccdCase.getSotSignerName())) {
                failWithMessage("Expected CcdCase.amountLowerValue to be <%s> but was <%s>",
                    ccdCase.getSotSignerName(), statementOfTruth.getSignerName());
            }

            if (!Objects.equals(statementOfTruth.getSignerRole(), ccdCase.getSotSignerRole())) {
                failWithMessage("Expected CcdCase.sotSignerRole to be <%s> but was <%s>",
                    ccdCase.getSotSignerRole(), statementOfTruth.getSignerRole());
            }
        }

        assertThat(actual.getClaimants().size()).isEqualTo(ccdCase.getApplicants().size());
        assertThat(actual.getDefendants().size()).isEqualTo(ccdCase.getRespondents().size());

        return this;
    }

}
