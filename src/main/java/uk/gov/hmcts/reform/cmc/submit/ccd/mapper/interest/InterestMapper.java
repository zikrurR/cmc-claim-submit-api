package uk.gov.hmcts.reform.cmc.submit.ccd.mapper.interest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCase;
import uk.gov.hmcts.cmc.ccd.domain.CcdInterestType;
import uk.gov.hmcts.cmc.domain.models.interest.Interest;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.BuilderMapper;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class InterestMapper implements BuilderMapper<CcdCase, Interest, CcdCase.CcdCaseBuilder> {

    private InterestBreakdownMapper interestBreakdownMapper;
    private InterestDateMapper interestDateMapper;

    @Autowired
    public InterestMapper(
        InterestBreakdownMapper interestBreakdownMapper,
        InterestDateMapper interestDateMapper
    ) {
        this.interestBreakdownMapper = interestBreakdownMapper;
        this.interestDateMapper = interestDateMapper;
    }

    @Override
    public void to(Interest interest, CcdCase.CcdCaseBuilder builder) {
        if (interest == null) {
            return;
        }

        builder.interestSpecificDailyAmount(interest.getSpecificDailyAmount());
        interestBreakdownMapper.to(interest.getInterestBreakdown(), builder);
        interestDateMapper.to(interest.getInterestDate(), builder);

        builder
            .interestType(CcdInterestType.valueOf(interest.getType().name()))
            .interestRate(interest.getRate())
            .interestReason(interest.getReason());
    }

    @Override
    public Interest from(CcdCase ccdCase) {
        if (ccdCase.getInterestType() == null
            && ccdCase.getInterestRate() == null
            && isBlank(ccdCase.getInterestReason())
            && ccdCase.getInterestSpecificDailyAmount() == null
            && ccdCase.getInterestBreakDownAmount() == null
            && isBlank(ccdCase.getInterestBreakDownExplanation())
            && ccdCase.getInterestDateType() == null
        ) {
            return null;
        }


        Interest interest = new Interest();
        interest.setInterestBreakdown(interestBreakdownMapper.from(ccdCase));
        interest.setReason(ccdCase.getInterestReason());
        interest.setInterestDate(interestDateMapper.from(ccdCase));
        interest.setRate(ccdCase.getInterestRate());
        interest.setSpecificDailyAmount(ccdCase.getInterestSpecificDailyAmount());
        interest.setType(Interest.InterestType.valueOf(ccdCase.getInterestType().name()));

        return interest;
    }
}
