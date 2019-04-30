package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.interest.Interest;

import java.math.BigDecimal;

public class SampleInterest {

    private SampleInterest() {
        super();
    }

    public static Interest validDefaults() {
        return different();
    }

    public static Interest different() {
        Interest interest = new Interest();

        interest.setType(Interest.InterestType.DIFFERENT);
        interest.setInterestDate(SampleInterestDate.validDefaults());
        interest.setRate(new BigDecimal(11));
        interest.setReason("A reason");

        return interest;
    }

    public static Interest standard() {
        Interest standard = new Interest();

        standard.setType(Interest.InterestType.STANDARD);
        standard.setInterestDate(SampleInterestDate.validDefaults());
        standard.setRate(new BigDecimal("8"));
        return standard;
    }

    public static Interest noInterest() {
        Interest noInterest = new Interest();

        noInterest.setType(Interest.InterestType.NO_INTEREST);
        noInterest.setInterestDate(SampleInterestDate.validDefaults());

        return noInterest;
    }

    public static Interest breakdownOnly() {
        Interest breakdownInterest = new Interest();

        breakdownInterest.setType(Interest.InterestType.BREAKDOWN);
        breakdownInterest.setInterestDate(SampleInterestDate.validDefaults());
        breakdownInterest.setInterestBreakdown(SampleInterestBreakdown.validDefaults());

        return breakdownInterest;
    }

}
