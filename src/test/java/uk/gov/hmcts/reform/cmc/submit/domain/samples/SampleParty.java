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

    private static String name = "John Rambo";
    private static String businessName = "Trading as name";
    private static String contactPerson = "Steven Seagal";
    private static Address address = SampleAddress.validDefaults();
    private static Address correspondenceAddress = SampleAddress.validDefaults();
    private static String title = "Dr.";
    private static String mobilePhone = "07873727165";
    private static LocalDate dateOfBirth = LocalDate.of(1968, 1, 2);
    private static Representative representative = SampleRepresentative.validDefaults();
    private static String companiesHouseNumber;
    private static String collectionId = "acd82549-d279-4adc-b38c-d195dd0db0d6";

    private SampleParty() {
        super();
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
        Individual individual = new Individual();

        individual.setAddress(address);
        individual.setCorrespondenceAddress(correspondenceAddress);
        individual.setDateOfBirth(dateOfBirth);
        individual.setId(collectionId);
        individual.setMobilePhone(mobilePhone);
        individual.setName(name);
        individual.setRepresentative(representative);

        return individual;
    }

    public static SoleTrader soleTrader() {
        SoleTrader soleTrader = new SoleTrader();

        soleTrader.setAddress(address);
        soleTrader.setCorrespondenceAddress(correspondenceAddress);
        soleTrader.setTitle(title);
        soleTrader.setBusinessName(businessName);
        soleTrader.setId(collectionId);
        soleTrader.setMobilePhone(mobilePhone);
        soleTrader.setName(name);
        soleTrader.setRepresentative(representative);

        return soleTrader;
    }

    public static Company company() {
        Company company = new Company();

        company.setAddress(address);
        company.setCorrespondenceAddress(correspondenceAddress);
        company.setContactPerson(contactPerson);
        company.setId(collectionId);
        company.setMobilePhone(mobilePhone);
        company.setName(name);
        company.setRepresentative(representative);

        return company;
    }

    public static Organisation organisation() {
        Organisation company = new Organisation();

        company.setAddress(address);
        company.setCorrespondenceAddress(correspondenceAddress);
        company.setContactPerson(contactPerson);
        company.setCompaniesHouseNumber(companiesHouseNumber);
        company.setId(collectionId);
        company.setMobilePhone(mobilePhone);
        company.setName(name);

        return company;
    }

}
