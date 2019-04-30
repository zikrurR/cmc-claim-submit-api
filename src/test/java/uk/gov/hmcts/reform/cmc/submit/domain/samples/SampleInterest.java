package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;

import java.math.BigDecimal;

public class SampleInterest {

    private SampleInterest() {
        super();
    }

    public static Interest validDefaults() {
        Interest interest = new Interest();

        interest.setInterestBreakdown(null);
        interest.setInterestDate(SampleInterestDate.validDefaults());
        interest.setRate(new BigDecimal(11));
        interest.setReason("A reason");
        interest.setSpecificDailyAmount(null);
        interest.setType(Interest.InterestType.DIFFERENT);

        return interest;
    }

    public static Interest standard() {
        Interest noInterest = validDefaults();
        noInterest.setType(Interest.InterestType.STANDARD);
        noInterest.setRate(new BigDecimal("8"));
        noInterest.setReason(null);
        return noInterest;
    }

    public static Interest noInterest() {
        Interest noInterest = validDefaults();
        noInterest.setType(Interest.InterestType.NO_INTEREST);
        noInterest.setRate(null);
        noInterest.setReason(null);

        return noInterest;
    }

    public static Interest breakdownOnly() {
        Interest breakdownInterest = validDefaults();

        breakdownInterest.setType(Interest.InterestType.BREAKDOWN);

        breakdownInterest.setInterestBreakdown(SampleInterestBreakdown.validDefaults());
        breakdownInterest.setRate(null);
        breakdownInterest.setReason(null);

        return breakdownInterest;
    }

}
