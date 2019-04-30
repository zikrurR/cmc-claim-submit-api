package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAmountRow;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdAmountRowBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCaseBuilder;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.builders.CcdCollectionElementBuilder;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.Amount;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRange;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRow;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.NotKnown;

import java.util.Objects;
import java.util.stream.Collectors;

import static uk.gov.hmcts.reform.cmc.submit.ccd.domain.AmountType.BREAK_DOWN;
import static uk.gov.hmcts.reform.cmc.submit.ccd.domain.AmountType.NOT_KNOWN;
import static uk.gov.hmcts.reform.cmc.submit.ccd.domain.AmountType.RANGE;

@Component
class MergeCaseDataAmount implements MergeCaseDataDecorator {

    @Override
    public void merge(CcdCaseBuilder ccdCaseBuilder, ClaimInput claim) {

        Amount amount = claim.getAmount();

        if (amount instanceof AmountRange) {
            ccdCaseBuilder.amountType(RANGE);
            AmountRange amountRange = (AmountRange) amount;
            amountRange(amountRange, ccdCaseBuilder);
        } else if (amount instanceof AmountBreakDown) {
            ccdCaseBuilder.amountType(BREAK_DOWN);
            AmountBreakDown amountBreakDown = (AmountBreakDown) amount;
            amountBreakDown(amountBreakDown, ccdCaseBuilder);
        } else if (amount instanceof NotKnown) {
            ccdCaseBuilder.amountType(NOT_KNOWN);
        }

    }

    private void amountBreakDown(AmountBreakDown amountBreakDown, CcdCaseBuilder ccdCaseBuilder) {
        ccdCaseBuilder.amountBreakDown(amountBreakDown.getRows().stream()
            .map(this::amountRange)
            .filter(Objects::nonNull)
            .collect(Collectors.toList()));

    }

    private CcdCollectionElementBuilder<CcdAmountRow> amountRange(AmountRow amountRow) {
        return CcdCollectionElementBuilder.<CcdAmountRow>builder()
                .id(amountRow.getId())
                .value(CcdAmountRowBuilder.builder()
                        .reason(amountRow.getReason())
                        .amount(amountRow.getAmount()));
    }

    public void amountRange(AmountRange amountRange, CcdCaseBuilder ccdCaseBuilder) {
        ccdCaseBuilder.amountLowerValue(amountRange.getLowerValue());
        ccdCaseBuilder.amountHigherValue(amountRange.getHigherValue());
    }

}
