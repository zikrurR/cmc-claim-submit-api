package uk.gov.hmcts.reform.cmc.ccd.builders;

import uk.gov.hmcts.cmc.domain.models.StatementOfTruth;

public class SampleStatementOfTruth {

    private SampleStatementOfTruth() {
        super();
    }

    public static StatementOfTruth validDefaults() {
        StatementOfTruth statementOfTruth = new StatementOfTruth();
        statementOfTruth.setSignerName("John Rambo");
        statementOfTruth.setSignerRole("Director");

        return statementOfTruth;
    }

}
