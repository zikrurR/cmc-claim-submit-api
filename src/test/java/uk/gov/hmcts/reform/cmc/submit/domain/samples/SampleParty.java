package uk.gov.hmcts.reform.cmc.submit.domain.samples;

import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SampleParty {

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

        defaultParty(individual);
        individual.setDateOfBirth(LocalDate.of(1968, 1, 2));

        return individual;
    }

    private static void defaultParty(Party party) {
        party.setAddress(SampleAddress.validDefaults());
        party.setCorrespondenceAddress(SampleAddress.validDefaults());
        party.setId("acd82549-d279-4adc-b38c-d195dd0db0d6");
        party.setName("John Rambo");
        party.setMobilePhone("07873727165");
        party.setRepresentative(SampleRepresentative.validDefaults());
    }

    public static SoleTrader soleTrader() {
        SoleTrader soleTrader = new SoleTrader();
        defaultParty(soleTrader);

        soleTrader.setTitle("Dr.");
        soleTrader.setBusinessName("Trading as name");

        return soleTrader;
    }

    public static Company company() {
        Company company = new Company();
        defaultParty(company);

        company.setContactPerson("Steven Seagal");
        return company;
    }

    public static Organisation organisation() {
        Organisation company = new Organisation();

        company.setContactPerson("Steven Seagal");
        company.setCompaniesHouseNumber("2344546");
        company.setRepresentative(null);

        return company;
    }

}
