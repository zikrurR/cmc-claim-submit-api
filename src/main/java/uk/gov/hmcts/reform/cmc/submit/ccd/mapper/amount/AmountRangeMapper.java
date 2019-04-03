package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.amount;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRange;

@Component
public class AmountRangeMapper implements BuilderMapper<CcdCase, AmountRange, CcdCase.CcdCaseBuilder> {

    @Override
    public void to(AmountRange amountRange, CcdCase.CcdCaseBuilder builder) {

        builder.amountLowerValue(amountRange.getLowerValue());
        builder.amountHigherValue(amountRange.getHigherValue());
    }

    @Override
    public AmountRange from(CcdCase ccdAmountRange) {

        AmountRange amountRange = new AmountRange();
        amountRange.setHigherValue(ccdAmountRange.getAmountHigherValue());
        amountRange.setLowerValue(ccdAmountRange.getAmountLowerValue());

        return amountRange;
    }
}
