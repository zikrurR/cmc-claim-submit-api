package uk.gov.hmcts.reform.cmc.submit.domain.models.defendants;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrganisationDetails extends TheirDetails {

    private String contactPerson;

    private String companiesHouseNumber;

}
