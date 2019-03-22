package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.amount;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDAmountRow;
import uk.gov.hmcts.cmc.ccd.domain.CCDCase;
import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.cmc.domain.models.amount.AmountRow;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AmountBreakDownMapper implements BuilderMapper<CCDCase, AmountBreakDown, CCDCase.CCDCaseBuilder> {


    @Override
    public void to(AmountBreakDown amountBreakDown, CCDCase.CCDCaseBuilder builder) {
        builder.amountBreakDown(amountBreakDown.getRows().stream()
            .map(this::to)
            .filter(Objects::nonNull)
            .collect(Collectors.toList()));

    }

    @Override
    public AmountBreakDown from(CCDCase ccdCase) {

        AmountBreakDown amountBreakDown = new AmountBreakDown();
        amountBreakDown.setRows(ccdCase.getAmountBreakDown().stream()
                .map(this::from)
                .collect(Collectors.toList())
        );
        return amountBreakDown;
    }


    public CCDCollectionElement<CCDAmountRow> to(AmountRow amountRow) {
        if (amountRow.getAmount() == null) {
            return null;
        }

        return CCDCollectionElement.<CCDAmountRow>builder()
            .value(CCDAmountRow.builder().reason(amountRow.getReason()).amount(amountRow.getAmount()).build())
            .id(amountRow.getId())
            .build();
    }

    public AmountRow from(CCDCollectionElement<CCDAmountRow> collectionElement) {
        CCDAmountRow ccdAmountRow = collectionElement.getValue();

        AmountRow amountRow = new AmountRow();
        amountRow.setId(collectionElement.getId());
        amountRow.setReason(ccdAmountRow.getReason());
        amountRow.setAmount(ccdAmountRow.getAmount());

        return amountRow;
    }
}
