package uk.gov.hmcts.reform.cmc.ccd.assertion;

import org.assertj.core.api.AbstractAssert;

import uk.gov.hmcts.cmc.ccd.domain.CCDAmountRow;
import uk.gov.hmcts.cmc.ccd.domain.CCDCase;
import uk.gov.hmcts.cmc.domain.models.ClaimData;
import uk.gov.hmcts.cmc.domain.models.StatementOfTruth;
import uk.gov.hmcts.cmc.domain.models.amount.Amount;
import uk.gov.hmcts.cmc.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.cmc.domain.models.amount.AmountRange;
import uk.gov.hmcts.cmc.domain.models.amount.AmountRow;
import uk.gov.hmcts.cmc.domain.models.amount.NotKnown;
import uk.gov.hmcts.cmc.domain.models.particulars.DamagesExpectation;
import uk.gov.hmcts.cmc.domain.models.particulars.HousingDisrepair;
import uk.gov.hmcts.cmc.domain.models.particulars.PersonalInjury;

import java.time.LocalDate;
import java.util.Objects;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;

public class ClaimAssert extends AbstractAssert<ClaimAssert, ClaimData> {

    public ClaimAssert(ClaimData actual) {
        super(actual, ClaimAssert.class);
    }

    public ClaimAssert isEqualTo(CCDCase ccdCase) {
        isNotNull();

//////////
//    NEED TO DECIDED WHTA TO DO WHIT THAT FIELD
//////////
//        if (!Objects.equals(actual.getReferenceNumber(), ccdCase.getReferenceNumber())) {
//            failWithMessage("Expected CCDCase.referenceNumber to be <%s> but was <%s>",
//                ccdCase.getReferenceNumber(), actual.getReferenceNumber());
//        }
//
//        if (!Objects.equals(actual.getSubmitterId(), ccdCase.getSubmitterId())) {
//            failWithMessage("Expected CCDCase.submitterId to be <%s> but was <%s>",
//                ccdCase.getSubmitterId(), actual.getSubmitterId());
//        }
//
//        if (!Objects.equals(actual.getCreatedAt(), ccdCase.getSubmittedOn())) {
//            failWithMessage("Expected CCDCase.submittedOn to be <%s> but was <%s>",
//                ccdCase.getSubmittedOn(), actual.getCreatedAt());
//        }
//
//        if (!Objects.equals(actual.getExternalId(), ccdCase.getExternalId())) {
//            failWithMessage("Expected CCDCase.externalId to be <%s> but was <%s>",
//                ccdCase.getExternalId(), actual.getExternalId());
//        }
//
//        if (!Objects.equals(actual.getIssuedOn(), ccdCase.getIssuedOn())) {
//            failWithMessage("Expected CCDCase.issuedOn to be <%s> but was <%s>",
//                ccdCase.getIssuedOn(), actual.getIssuedOn());
//        }
//
//        if (!Objects.equals(actual.getSubmitterEmail(), ccdCase.getSubmitterEmail())) {
//            failWithMessage("Expected CCDCase.submitterEmail to be <%s> but was <%s>",
//                ccdCase.getSubmitterEmail(), actual.getSubmitterEmail());
//        }

        if (!Objects.equals(actual.getReason(), ccdCase.getReason())) {
            failWithMessage("Expected CCDClaim.reason to be <%s> but was <%s>",
                ccdCase.getReason(), actual.getReason());
        }


        if (!Objects.equals(actual.getFeeAccountNumber(), ccdCase.getFeeAccountNumber())) {
            failWithMessage("Expected CCDClaim.feeAccountNumber to be <%s> but was <%s>",
                ccdCase.getFeeAccountNumber(), actual.getFeeAccountNumber());
        }

        if (!Objects.equals(actual.getExternalReferenceNumber(), ccdCase.getExternalReferenceNumber())) {
            failWithMessage("Expected CCDClaim.externalReferenceNumber to be <%s> but was <%s>",
                ccdCase.getExternalReferenceNumber(), actual.getExternalReferenceNumber());
        }

        if (!Objects.equals(actual.getExternalId().toString(), ccdCase.getExternalId())) {
            failWithMessage("Expected CCDClaim.externalId to be <%s> but was <%s>",
                ccdCase.getExternalId(), actual.getExternalId().toString());
        }

        if (!Objects.equals(actual.getPreferredCourt(), ccdCase.getPreferredCourt())) {
            failWithMessage("Expected CCDClaim.preferredCourt to be <%s> but was <%s>",
                ccdCase.getPreferredCourt(), actual.getPreferredCourt());
        }

        Amount amount = actual.getAmount();
        if (amount instanceof AmountBreakDown) {
            AmountBreakDown amountBreakDown = (AmountBreakDown) amount;

            AmountRow amountRow = amountBreakDown.getRows().get(0);
            CCDAmountRow ccdAmountRow = ccdCase.getAmountBreakDown().get(0).getValue();

            if (!Objects.equals(amountRow.getReason(), ccdAmountRow.getReason())) {
                failWithMessage("Expected CCDCase.amountRowReason to be <%s> but was <%s>",
                    ccdAmountRow.getReason(), amountRow.getReason());
            }

            if (!Objects.equals(amountRow.getAmount(), ccdAmountRow.getAmount())) {
                failWithMessage("Expected CCDCase.amount to be <%s> but was <%s>",
                    ccdAmountRow.getAmount(), amountRow.getAmount());
            }

        } else if (amount instanceof AmountRange) {

            AmountRange amountRange = (AmountRange) amount;

            if (!Objects.equals(amountRange.getHigherValue(), ccdCase.getAmountHigherValue())) {
                failWithMessage("Expected CCDCase.amountHigherValue to be <%s> but was <%s>",
                    ccdCase.getAmountHigherValue(), amountRange.getHigherValue());
            }

            if (!Objects.equals(amountRange.getLowerValue(), ccdCase.getAmountLowerValue())) {
                failWithMessage("Expected CCDCase.amountLowerValue to be <%s> but was <%s>",
                    ccdCase.getAmountLowerValue(), amountRange.getLowerValue());
            }

        } else {
            assertThat(amount).isInstanceOf(NotKnown.class);
        }

        ofNullable(actual.getInterest()).ifPresent(interest -> {
                if (!Objects.equals(interest.getRate(), ccdCase.getInterestRate())) {
                    failWithMessage("Expected CCDCase.interestRate to be <%s> but was <%s>",
                        ccdCase.getInterestRate(), interest.getRate());
                }
                if (!Objects.equals(interest.getType().name(), ccdCase.getInterestType().name())) {
                    failWithMessage("Expected CCDCase.interestType to be <%s> but was <%s>",
                        ccdCase.getInterestType(), interest.getType());
                }
                if (!Objects.equals(interest.getReason(), ccdCase.getInterestReason())) {
                    failWithMessage("Expected CCDCase.interestReason to be <%s> but was <%s>",
                        ccdCase.getInterestReason(), interest.getRate());
                }
                if (!Objects.equals(interest.getSpecificDailyAmount(), ccdCase.getInterestSpecificDailyAmount())) {
                    failWithMessage("Expected CCDCase.interestSpecificDailyAmount to be <%s> but was <%s>",
                        ccdCase.getInterestSpecificDailyAmount(), interest.getSpecificDailyAmount());

                }

                ofNullable(interest.getInterestDate()).ifPresent(interestDate -> {
                    if (!Objects.equals(interestDate.getDate(), ccdCase.getInterestClaimStartDate())) {
                        failWithMessage("Expected CCDCase.interestClaimStartDate to be <%s> but was <%s>",
                            ccdCase.getInterestClaimStartDate(), interestDate.getDate());
                    }
                    if (!Objects.equals(interestDate.getType().name(), ccdCase.getInterestDateType().name())) {
                        failWithMessage("Expected CCDCase.interestDateType to be <%s> but was <%s>",
                            ccdCase.getInterestDateType(), interestDate.getType());
                    }

                    if (!Objects.equals(interestDate.getReason(), ccdCase.getInterestStartDateReason())) {
                        failWithMessage("Expected CCDCase.interestStartDateReason to be <%s> but was <%s>",
                            ccdCase.getInterestStartDateReason(), interestDate.getReason());
                    }

                    if (!Objects.equals(interestDate.getEndDateType().name(),
                        ccdCase.getInterestEndDateType().name())
                    ) {
                        failWithMessage("Expected CCDCase.interestEndDateType to be <%s> but was <%s>",
                            ccdCase.getInterestEndDateType(), interestDate.getEndDateType());
                    }
                });
            }
        );

        ofNullable(actual.getPayment()).ifPresent(payment -> {
                if (!Objects.equals(payment.getId(), ccdCase.getPaymentId())) {
                    failWithMessage("Expected CCDCase.paymentId to be <%s> but was <%s>",
                        ccdCase.getPaymentId(), payment.getId());
                }
                if (!Objects.equals(payment.getReference(), ccdCase.getPaymentReference())) {
                    failWithMessage("Expected CCDCase.paymentReference to be <%s> but was <%s>",
                        ccdCase.getPaymentReference(), payment.getReference());
                }
                if (!Objects.equals(payment.getAmount(), ccdCase.getPaymentAmount())) {
                    failWithMessage("Expected CCDCase.paymentAmount to be <%s> but was <%s>",
                        ccdCase.getPaymentAmount(), payment.getAmount());
                }
                if (!Objects.equals(LocalDate.parse(payment.getDateCreated(), ISO_DATE),
                    ccdCase.getPaymentDateCreated())
                ) {
                    failWithMessage("Expected CCDCase.paymentDateCreated to be <%s> but was <%s>",
                        ccdCase.getPaymentDateCreated(), payment.getDateCreated());
                }
                if (!Objects.equals(payment.getStatus(), ccdCase.getPaymentStatus())) {
                    failWithMessage("Expected CCDCase.paymentStatus to be <%s> but was <%s>",
                        ccdCase.getPaymentStatus(), payment.getStatus());
                }
            }
        );


        PersonalInjury personalInjury = actual.getPersonalInjury();
        if (personalInjury != null) {
            if (!Objects.equals(personalInjury.getGeneralDamages().name(), ccdCase.getPersonalInjuryGeneralDamages())) {
                failWithMessage("Expected CCDCase.personalInjuryGeneralDamages to be <%s> but was <%s>",
                    ccdCase.getPersonalInjuryGeneralDamages(), personalInjury.getGeneralDamages());
            }
        }

        if (actual.getHousingDisrepair() != null) {
            HousingDisrepair housingDisrepair = actual.getHousingDisrepair();
            if (!Objects.equals(housingDisrepair.getCostOfRepairsDamages().name(),
                ccdCase.getHousingDisrepairCostOfRepairDamages())
            ) {
                failWithMessage("Expected CCDCase.housingDisrepairCostOfRepairDamages to be <%s> but was <%s>",
                    ccdCase.getHousingDisrepairCostOfRepairDamages(), housingDisrepair.getCostOfRepairsDamages());
            }

            if (housingDisrepair.getOtherDamages() != null) {
                DamagesExpectation damagesExpectation = housingDisrepair.getOtherDamages();
                if (!Objects.equals(damagesExpectation.name(), ccdCase.getHousingDisrepairOtherDamages())) {
                    failWithMessage("Expected CCDCase.housingDisrepairOtherDamages to be <%s> but was <%s>",
                        ccdCase.getHousingDisrepairOtherDamages(), damagesExpectation.name());
                }
            }

        }

        if (actual.getStatementOfTruth() != null) {
            StatementOfTruth statementOfTruth = actual.getStatementOfTruth();
            if (!Objects.equals(statementOfTruth.getSignerName(), ccdCase.getSotSignerName())) {
                failWithMessage("Expected CCDCase.amountLowerValue to be <%s> but was <%s>",
                    ccdCase.getSotSignerName(), statementOfTruth.getSignerName());
            }

            if (!Objects.equals(statementOfTruth.getSignerRole(), ccdCase.getSotSignerRole())) {
                failWithMessage("Expected CCDCase.sotSignerRole to be <%s> but was <%s>",
                    ccdCase.getSotSignerRole(), statementOfTruth.getSignerRole());
            }
        }

        assertThat(actual.getClaimants().size()).isEqualTo(ccdCase.getClaimants().size());
        assertThat(actual.getDefendants().size()).isEqualTo(ccdCase.getDefendants().size());

        return this;
    }

}
