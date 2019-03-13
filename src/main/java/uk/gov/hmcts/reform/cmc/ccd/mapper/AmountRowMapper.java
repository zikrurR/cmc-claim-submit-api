package uk.gov.hmcts.reform.cmc.ccd.mapper;

import uk.gov.hmcts.cmc.ccd.domain.CCDAmountRow;
import uk.gov.hmcts.cmc.ccd.domain.CCDCollectionElement;
import uk.gov.hmcts.cmc.domain.models.amount.AmountRow;

import org.springframework.stereotype.Component;

@Component
public class AmountRowMapper {

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

        return AmountRow.builder()
            .id(collectionElement.getId())
            .reason(ccdAmountRow.getReason())
            .amount(ccdAmountRow.getAmount())
            .build();
    }
}
