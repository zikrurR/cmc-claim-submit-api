package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.amount;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CCDCase;
import uk.gov.hmcts.cmc.domain.models.amount.AmountRange;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;

@Component
public class AmountRangeMapper implements BuilderMapper<CCDCase, AmountRange, CCDCase.CCDCaseBuilder> {

    @Override
    public void to(AmountRange amountRange, CCDCase.CCDCaseBuilder builder) {

        builder.amountLowerValue(amountRange.getLowerValue());
        builder.amountHigherValue(amountRange.getHigherValue());
    }

    @Override
    public AmountRange from(CCDCase ccdAmountRange) {

        AmountRange amountRange = new AmountRange();
        amountRange.setHigherValue(ccdAmountRange.getAmountHigherValue());
        amountRange.setLowerValue(ccdAmountRange.getAmountLowerValue());

        return amountRange;
    }
}
