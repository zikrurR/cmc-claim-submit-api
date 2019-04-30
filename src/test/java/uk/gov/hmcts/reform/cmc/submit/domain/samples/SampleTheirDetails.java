package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;

import java.util.ArrayList;
import java.util.List;

public class SampleTheirDetails {

    private SampleTheirDetails() {
        super();
    }


    public static TheirDetails partyDetails() {
        return individualDetails();
    }

    public static IndividualDetails individualDetails() {
        IndividualDetails individualDetails = new IndividualDetails();

        defaultTheirDetails(individualDetails);
        individualDetails.setDateOfBirth(null);
        individualDetails.setFirstName("John");
        individualDetails.setLastName("Smith");
        individualDetails.setTitle("Dr.");

        return individualDetails;
    }

    public static List<TheirDetails> individualDetails(int count) {
        List<TheirDetails> individualDetailsList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            individualDetailsList.add(
                    individualDetails()
            );
        }
        return individualDetailsList;
    }

    public static CompanyDetails companyDetails() {

        CompanyDetails companyDetails = new CompanyDetails();
        defaultTheirDetails(companyDetails);
        companyDetails.setName("John");
        companyDetails.setContactPerson("Arnold Schwarzenegger");
        companyDetails.setRepresentative(SampleRepresentative.validDefaults());

        return companyDetails;
    }

    public static OrganisationDetails organisationDetails() {

        OrganisationDetails organisationDetails = new OrganisationDetails();
        defaultTheirDetails(organisationDetails);
        organisationDetails.setContactPerson("Arnold Schwarzenegger");
        organisationDetails.setName("John");
        organisationDetails.setCompaniesHouseNumber("1223456");

        return organisationDetails;
    }

    public static SoleTraderDetails soleTraderDetails() {

        SoleTraderDetails soleTraderDetails = new SoleTraderDetails();
        defaultTheirDetails(soleTraderDetails);
        soleTraderDetails.setTitle("Dr.");
        soleTraderDetails.setBusinessName("Sole Trading & Sons");
        soleTraderDetails.setFirstName("John");
        soleTraderDetails.setLastName("Smith");

        return soleTraderDetails;
    }


    private static void defaultTheirDetails(TheirDetails individualDetails) {
        individualDetails.setAddress(SampleAddress.validDefaults());
        individualDetails.setEmail("j.smith@example.com");
        individualDetails.setId("3d0bc933-0d46-4564-94bd-79e6e69b838b");
    }

}
