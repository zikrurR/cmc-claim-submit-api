package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.DamagesExpectation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.particulars.HousingDisrepair;

public class SampleHousingDisrepair {

    private SampleHousingDisrepair() {
        super();
    }

    public static HousingDisrepair validDefaults() {

        HousingDisrepair housingDisrepair = new HousingDisrepair();
        housingDisrepair.setCostOfRepairsDamages(DamagesExpectation.MORE_THAN_THOUSAND_POUNDS);
        housingDisrepair.setOtherDamages(DamagesExpectation.MORE_THAN_THOUSAND_POUNDS);

        return housingDisrepair;
    }

}
