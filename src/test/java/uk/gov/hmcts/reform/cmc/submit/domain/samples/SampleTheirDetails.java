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

    public static final String DEFENDANT_EMAIL = "j.smith@example.com";

    private String name = "John Smith";
    private Address address = SampleAddress.validDefaults();
    private String email = DEFENDANT_EMAIL;
    private String contactPerson = "Arnold Schwarzenegger";
    private String businessName = "Sole Trading & Sons";
    private String title = "Dr.";
    private Representative representative;
    private String companiesHouseNumber;
    private Address serviceAddress;
    private LocalDate dateOfBirth;
    private String collectionId = "3d0bc933-0d46-4564-94bd-79e6e69b838b";

    public static SampleTheirDetails builder() {
        return new SampleTheirDetails();
    }

    public SampleTheirDetails withName(String name) {
        this.name = name;
        return this;
    }

    public SampleTheirDetails withAddress(Address address) {
        this.address = address;
        return this;
    }

    public SampleTheirDetails withEmail(String email) {
        this.email = email;
        return this;
    }

    public SampleTheirDetails withTitle(String title) {
        this.title = title;
        return this;
    }

    public SampleTheirDetails withRepresentative(Representative representative) {
        this.representative = representative;
        return this;
    }

    public SampleTheirDetails withServiceAddress(Address serviceAddress) {
        this.serviceAddress = serviceAddress;
        return this;
    }

    public SampleTheirDetails withContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public SampleTheirDetails withBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public SampleTheirDetails withCompaniesHouseNumber(String companiesHouseNumber) {
        this.companiesHouseNumber = companiesHouseNumber;
        return this;
    }

    public SampleTheirDetails withDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public SampleTheirDetails withCollectionId(String collectionId) {
        this.collectionId = collectionId;
        return this;
    }


    public static TheirDetails partyDetails() {
        return individualDetails();
    }

    public static IndividualDetails individualDetails() {

        SampleTheirDetails builder = builder();
        IndividualDetails individualDetails = new IndividualDetails();

        individualDetails.setAddress(builder.address);
        individualDetails.setDateOfBirth(builder.dateOfBirth);
        individualDetails.setEmail(builder.email);
        individualDetails.setId(builder.collectionId);
        individualDetails.setName(builder.name);
        individualDetails.setRepresentative(builder.representative);
        individualDetails.setServiceAddress(builder.serviceAddress);

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

        SampleTheirDetails builder = builder();
        CompanyDetails companyDetails = new CompanyDetails();

        companyDetails.setAddress(builder.address);
        companyDetails.setContactPerson(builder.contactPerson);;
        companyDetails.setEmail(builder.email);
        companyDetails.setId(builder.collectionId);
        companyDetails.setName(builder.name);
        companyDetails.setRepresentative(builder.representative);
        companyDetails.setServiceAddress(builder.serviceAddress);

        return companyDetails;
    }

    public static OrganisationDetails organisationDetails() {

        SampleTheirDetails builder = builder();
        OrganisationDetails organisationDetails = new OrganisationDetails();

        organisationDetails.setAddress(builder.address);
        organisationDetails.setContactPerson(builder.contactPerson);;
        organisationDetails.setEmail(builder.email);
        organisationDetails.setId(builder.collectionId);
        organisationDetails.setName(builder.name);
        organisationDetails.setRepresentative(builder.representative);
        organisationDetails.setServiceAddress(builder.serviceAddress);
        organisationDetails.setCompaniesHouseNumber(builder.companiesHouseNumber);

        return organisationDetails;
    }

    public static SoleTraderDetails soleTraderDetails() {

        SampleTheirDetails builder = builder();
        SoleTraderDetails soleTraderDetails = new SoleTraderDetails();

        soleTraderDetails.setAddress(builder.address);
        soleTraderDetails.setTitle(builder.title);
        soleTraderDetails.setBusinessName(builder.businessName);
        soleTraderDetails.setEmail(builder.email);
        soleTraderDetails.setId(builder.collectionId);
        soleTraderDetails.setName(builder.name);
        soleTraderDetails.setRepresentative(builder.representative);
        soleTraderDetails.setServiceAddress(builder.serviceAddress);

        return soleTraderDetails;
    }

}
