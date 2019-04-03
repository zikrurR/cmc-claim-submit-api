package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.amount;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.exception.MappingException;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.Amount;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountBreakDown;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.AmountRange;
import uk.gov.hmcts.reform.cmc.submit.domain.models.amount.NotKnown;

import static uk.gov.hmcts.cmc.ccd.domain.AmountType.BREAK_DOWN;
import static uk.gov.hmcts.cmc.ccd.domain.AmountType.NOT_KNOWN;
import static uk.gov.hmcts.cmc.ccd.domain.AmountType.RANGE;

@Component
public class AmountMapper implements BuilderMapper<CcdCase, Amount, CcdCase.CcdCaseBuilder> {

    private final AmountRangeMapper amountRangeMapper;
    private final AmountBreakDownMapper amountBreakDownMapper;

    public AmountMapper(AmountRangeMapper amountRangeMapper, AmountBreakDownMapper amountBreakDownMapper) {
        this.amountRangeMapper = amountRangeMapper;
        this.amountBreakDownMapper = amountBreakDownMapper;
    }

    @Override
    public void to(Amount amount, CcdCase.CcdCaseBuilder builder) {
        if (amount instanceof AmountRange) {
            builder.amountType(RANGE);
            AmountRange amountRange = (AmountRange) amount;
            amountRangeMapper.to(amountRange, builder);
        } else if (amount instanceof AmountBreakDown) {
            builder.amountType(BREAK_DOWN);
            AmountBreakDown amountBreakDown = (AmountBreakDown) amount;
            amountBreakDownMapper.to(amountBreakDown, builder);
        } else if (amount instanceof NotKnown) {
            builder.amountType(NOT_KNOWN);
        }
    }

    @Override
    public Amount from(CcdCase ccdCase) {

        switch (ccdCase.getAmountType()) {
            case RANGE:
                return amountRangeMapper.from(ccdCase);
            case NOT_KNOWN:
                return new NotKnown();
            case BREAK_DOWN:
                return amountBreakDownMapper.from(ccdCase);
            default:
                throw new MappingException();
        }
    }
}
