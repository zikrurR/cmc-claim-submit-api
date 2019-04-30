package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Address;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SampleTheirDetails {

    private SampleTheirDetails() {
        super();
    }

    public static final String DEFENDANT_EMAIL = "j.smith@example.com";

    private static String name = "John";
    private static String firstName = "John";
    private static String lastName = "Smith";
    private static Address address = SampleAddress.validDefaults();
    private static String email = DEFENDANT_EMAIL;
    private static String contactPerson = "Arnold Schwarzenegger";
    private static String businessName = "Sole Trading & Sons";
    private static String title = "Dr.";
    private static Representative representative;
    private static String companiesHouseNumber;
    private static Address serviceAddress;
    private static LocalDate dateOfBirth;
    private static String collectionId = "3d0bc933-0d46-4564-94bd-79e6e69b838b";

    public static TheirDetails partyDetails() {
        return individualDetails();
    }

    public static IndividualDetails individualDetails() {
        IndividualDetails individualDetails = new IndividualDetails();

        individualDetails.setAddress(address);
        individualDetails.setDateOfBirth(dateOfBirth);
        individualDetails.setEmail(email);
        individualDetails.setId(collectionId);
        individualDetails.setFirstName(firstName);
        individualDetails.setLastName(lastName);
        individualDetails.setTitle(title);
        individualDetails.setRepresentative(representative);
        individualDetails.setServiceAddress(serviceAddress);

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

        companyDetails.setAddress(address);
        companyDetails.setContactPerson(contactPerson);;
        companyDetails.setEmail(email);
        companyDetails.setId(collectionId);
        companyDetails.setName(name);
        companyDetails.setRepresentative(representative);
        companyDetails.setServiceAddress(serviceAddress);

        return companyDetails;
    }

    public static OrganisationDetails organisationDetails() {

        OrganisationDetails organisationDetails = new OrganisationDetails();

        organisationDetails.setAddress(address);
        organisationDetails.setContactPerson(contactPerson);;
        organisationDetails.setEmail(email);
        organisationDetails.setId(collectionId);
        organisationDetails.setName(name);
        organisationDetails.setRepresentative(representative);
        organisationDetails.setServiceAddress(serviceAddress);
        organisationDetails.setCompaniesHouseNumber(companiesHouseNumber);

        return organisationDetails;
    }

    public static SoleTraderDetails soleTraderDetails() {

        SoleTraderDetails soleTraderDetails = new SoleTraderDetails();

        soleTraderDetails.setAddress(address);
        soleTraderDetails.setTitle(title);
        soleTraderDetails.setBusinessName(businessName);
        soleTraderDetails.setEmail(email);
        soleTraderDetails.setId(collectionId);
        soleTraderDetails.setFirstName(firstName);
        soleTraderDetails.setLastName(lastName);
        soleTraderDetails.setRepresentative(representative);
        soleTraderDetails.setServiceAddress(serviceAddress);

        return soleTraderDetails;
    }

}
