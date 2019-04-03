package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.amount;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdAmountRow;
import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRow;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AmountBreakDownMapper implements BuilderMapper<CcdCase, AmountBreakDown, CcdCase.CcdCaseBuilder> {

    @Override
    public void to(AmountBreakDown amountBreakDown, CcdCase.CcdCaseBuilder builder) {
        builder.amountBreakDown(amountBreakDown.getRows().stream()
            .map(this::to)
            .filter(Objects::nonNull)
            .collect(Collectors.toList()));

    }

    private CcdCollectionElement<CcdAmountRow> to(AmountRow amountRow) {
        if (amountRow.getAmount() == null) {
            return null;
        }

        return CcdCollectionElement.<CcdAmountRow>builder()
            .value(CcdAmountRow.builder().reason(amountRow.getReason()).amount(amountRow.getAmount()).build())
            .id(amountRow.getId())
            .build();
    }

    @Override
    public AmountBreakDown from(CcdCase ccdCase) {

        AmountBreakDown amountBreakDown = new AmountBreakDown();
        amountBreakDown.setRows(ccdCase.getAmountBreakDown().stream()
                .map(this::from)
                .collect(Collectors.toList())
        );
        return amountBreakDown;
    }

    private AmountRow from(CcdCollectionElement<CcdAmountRow> collectionElement) {
        CcdAmountRow ccdAmountRow = collectionElement.getValue();

        AmountRow amountRow = new AmountRow();
        amountRow.setId(collectionElement.getId());
        amountRow.setReason(ccdAmountRow.getReason());
        amountRow.setAmount(ccdAmountRow.getAmount());

        return amountRow;
    }

}
