package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAmountRow;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.Amount;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRange;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRow;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.NotKnown;
import uk.gov.hmcts.reform.cmc.submit.mapper.exception.MappingException;

import java.util.stream.Collectors;

@Component
class AmountConverter {

    public Amount from(CcdCase ccdCase) {

        switch (ccdCase.getAmountType()) {
            case RANGE:
                return amountRangeFrom(ccdCase);
            case NOT_KNOWN:
                return new NotKnown();
            case BREAK_DOWN:
                return amountBreakDownFrom(ccdCase);
            default:
                throw new MappingException();
        }
    }

    public AmountRange amountRangeFrom(CcdCase ccdAmountRange) {

        AmountRange amountRange = new AmountRange();
        amountRange.setHigherValue(ccdAmountRange.getAmountHigherValue());
        amountRange.setLowerValue(ccdAmountRange.getAmountLowerValue());

        return amountRange;
    }

    public AmountBreakDown amountBreakDownFrom(CcdCase ccdCase) {

        AmountBreakDown amountBreakDown = new AmountBreakDown();
        amountBreakDown.setRows(ccdCase.getAmountBreakDown().stream()
                .map(this::fromCcdAmountRow)
                .collect(Collectors.toList())
        );
        return amountBreakDown;
    }

    private AmountRow fromCcdAmountRow(CcdCollectionElement<CcdAmountRow> collectionElement) {
        CcdAmountRow ccdAmountRow = collectionElement.getValue();

        AmountRow amountRow = new AmountRow();
        amountRow.setId(collectionElement.getId());
        amountRow.setReason(ccdAmountRow.getReason());
        amountRow.setAmount(ccdAmountRow.getAmount());

        return amountRow;
    }
}
