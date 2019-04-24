package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CcdParty {
    private String partyId;
    private String idamId;
    private CcdPartyType type;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private CcdAddress primaryAddress;
    private String emailAddress;
    private CcdTelephone telephoneNumber;
    private CcdAddress correspondenceAddress;
    private String businessName;
    private String contactPerson;
    private String companiesHouseNumber;
}
