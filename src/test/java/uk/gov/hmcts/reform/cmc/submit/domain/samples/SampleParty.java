package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Address;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SampleParty {

    private String name = "John Rambo";
    private String businessName = "Trading as name";
    private String contactPerson = "Steven Seagal";
    private Address address = SampleAddress.validDefaults();
    private Address correspondenceAddress = SampleAddress.validDefaults();
    private String title = "Dr.";
    private String mobilePhone = "07873727165";
    private LocalDate dateOfBirth = LocalDate.of(1968, 1, 2);
    private Representative representative = SampleRepresentative.validDefaults();
    private String companiesHouseNumber;
    private String collectionId = "acd82549-d279-4adc-b38c-d195dd0db0d6";

    public static SampleParty builder() {
        return new SampleParty();
    }


    public static SampleParty individualBuilder() {
        // TODO Auto-generated method stub
        return null;
    }

    public SampleParty withName(String name) {
        this.name = name;
        return this;
    }

    public SampleParty withDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public SampleParty withContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public SampleParty withBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public SampleParty withAddress(Address address) {
        this.address = address;
        return this;
    }

    public SampleParty withCorrespondenceAddress(Address correspondenceAddress) {
        this.correspondenceAddress = correspondenceAddress;
        return this;
    }

    public SampleParty withMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public SampleParty withRepresentative(Representative representative) {
        this.representative = representative;
        return this;
    }

    public SampleParty withTitle(String title) {
        this.title = title;
        return this;
    }

    public SampleParty withCompaniesHouseNumber(String companiesHouseNumber) {
        this.companiesHouseNumber = companiesHouseNumber;
        return this;
    }

    public SampleParty withCollectionId(String collectionId) {
        this.collectionId = collectionId;
        return this;
    }

    public static Party party() {
        return individual();
    }

    public static List<Party> individualDetails(int count) {
        List<Party> individualDetailsList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            individualDetailsList.add(
                    individual()
            );
        }
        return individualDetailsList;
    }

    public static Individual individual() {
        SampleParty builder = builder();
        Individual individual = new Individual();

        individual.setAddress(builder.address);
        individual.setCorrespondenceAddress(builder.correspondenceAddress);
        individual.setDateOfBirth(builder.dateOfBirth);
        individual.setId(builder.collectionId);
        individual.setMobilePhone(builder.mobilePhone);
        individual.setName(builder.name);
        individual.setRepresentative(builder.representative);

        return individual;
    }

    public static SoleTrader soleTrader() {
        SampleParty builder = builder();
        SoleTrader soleTrader = new SoleTrader();

        soleTrader.setAddress(builder.address);
        soleTrader.setCorrespondenceAddress(builder.correspondenceAddress);
        soleTrader.setTitle(builder.title);
        soleTrader.setBusinessName(builder.businessName);
        soleTrader.setId(builder.collectionId);
        soleTrader.setMobilePhone(builder.mobilePhone);
        soleTrader.setName(builder.name);
        soleTrader.setRepresentative(builder.representative);

        return soleTrader;
    }

    public static Company company() {
        SampleParty builder = builder();
        Company company = new Company();

        company.setAddress(builder.address);
        company.setCorrespondenceAddress(builder.correspondenceAddress);
        company.setContactPerson(builder.contactPerson);
        company.setId(builder.collectionId);
        company.setMobilePhone(builder.mobilePhone);
        company.setName(builder.name);
        company.setRepresentative(builder.representative);

        return company;
    }

    public static Organisation organisation() {
        SampleParty builder = builder();
        Organisation company = new Organisation();

        company.setAddress(builder.address);
        company.setCorrespondenceAddress(builder.correspondenceAddress);
        company.setContactPerson(builder.contactPerson);
        company.setCompaniesHouseNumber(builder.companiesHouseNumber);
        company.setId(builder.collectionId);
        company.setMobilePhone(builder.mobilePhone);
        company.setName(builder.name);
        company.setRepresentative(builder.representative);

        return company;
    }

}
