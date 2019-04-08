package uk.gov.hmcts.reform.cmc.submit.merger;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdAmountRow;
import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.domain.models.ClaimInput;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.Amount;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRange;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRow;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.NotKnown;

import java.util.Objects;
import java.util.stream.Collectors;

import static uk.gov.hmcts.cmc.ccd.domain.AmountType.BREAK_DOWN;
import static uk.gov.hmcts.cmc.ccd.domain.AmountType.NOT_KNOWN;
import static uk.gov.hmcts.cmc.ccd.domain.AmountType.RANGE;

@Component
class MergeCaseDataAmount implements MergeCaseDataDecorator {

    @Override
    public void merge(CcdCase ccdCase, ClaimInput claim) {

        Amount amount = claim.getAmount();

        if (amount instanceof AmountRange) {
            ccdCase.setAmountType(RANGE);
            AmountRange amountRange = (AmountRange) amount;
            amountRange(amountRange, ccdCase);
        } else if (amount instanceof AmountBreakDown) {
            ccdCase.setAmountType(BREAK_DOWN);
            AmountBreakDown amountBreakDown = (AmountBreakDown) amount;
            amountBreakDown(amountBreakDown, ccdCase);
        } else if (amount instanceof NotKnown) {
            ccdCase.setAmountType(NOT_KNOWN);
        }

    }

    private void amountBreakDown(AmountBreakDown amountBreakDown, CcdCase ccdCase) {
        ccdCase.setAmountBreakDown(amountBreakDown.getRows().stream()
            .map(this::amountRange)
            .filter(Objects::nonNull)
            .collect(Collectors.toList()));

    }

    private CcdCollectionElement<CcdAmountRow> amountRange(AmountRow amountRow) {
        if (amountRow.getAmount() == null) {
            return null;
        }

        return CcdCollectionElement.<CcdAmountRow>builder()
            .value(CcdAmountRow.builder().reason(amountRow.getReason()).amount(amountRow.getAmount()).build())
            .id(amountRow.getId())
            .build();
    }

    public void amountRange(AmountRange amountRange, CcdCase ccdCase) {
        ccdCase.setAmountLowerValue(amountRange.getLowerValue());
        ccdCase.setAmountHigherValue(amountRange.getHigherValue());
    }

}
