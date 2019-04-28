package uk.gov.hmcts.reform.cmc.submit.ccd.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
