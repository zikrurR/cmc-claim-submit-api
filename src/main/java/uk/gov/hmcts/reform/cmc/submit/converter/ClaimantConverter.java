package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdApplicant;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdParty;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;
import uk.gov.hmcts.reform.cmc.submit.mapper.exception.MappingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
class ClaimantConverter {

    private final CommonAddressConverter addressConverter;

    public ClaimantConverter(CommonAddressConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    public List<Party> from(List<CcdCollectionElement<CcdApplicant>> ccdApplicants) {
        if (ccdApplicants == null) {
            return new ArrayList<>();
        }

        return ccdApplicants.stream()
                .filter(Objects::nonNull)
                .map(this::from)
                .collect(Collectors.toList());
    }

    private Party from(CcdCollectionElement<CcdApplicant> ccdApplicantCollection) {
        CcdApplicant ccdApplicant = ccdApplicantCollection.getValue();
        CcdParty partyDetail = ccdApplicant.getPartyDetail();
        CcdPartyType type = partyDetail.getType();

        Party party;
        switch (type) {
            case COMPANY:
                party = companyFrom(partyDetail);
                break;
            case INDIVIDUAL:
                party = individualFrom(partyDetail);
                break;
            case SOLE_TRADER:
                party = soleTraderFrom(partyDetail);
                break;
            case ORGANISATION:
                party = organisationFrom(partyDetail);
                break;
            default:
                throw new MappingException("Invalid claimant type, " + type);
        }

        party.setId(ccdApplicantCollection.getId());
        party.setName(ccdApplicant.getPartyName());
        party.setRepresentative(representativeFrom(ccdApplicant));

        party.setAddress(addressConverter.from(partyDetail.getPrimaryAddress()));
        party.setCorrespondenceAddress(addressConverter.from(partyDetail.getCorrespondenceAddress()));
        if (partyDetail.getTelephoneNumber() != null) {
            party.setMobilePhone(partyDetail.getTelephoneNumber().getTelephoneNumber());
        }

        return party;
    }

    public Company companyFrom(CcdParty party) {
        Company company = new Company();
        company.setContactPerson(party.getContactPerson());

        return company;
    }

    public Individual individualFrom(CcdParty party) {
        Individual individual = new Individual();
        individual.setDateOfBirth(party.getDateOfBirth());

        return individual;
    }

    public SoleTrader soleTraderFrom(CcdParty party) {
        SoleTrader soletrader = new SoleTrader();
        soletrader.setTitle(party.getTitle());
        soletrader.setBusinessName(party.getBusinessName());

        return soletrader;
    }

    public Organisation organisationFrom(CcdParty party) {
        Organisation organisation = new Organisation();
        organisation.setContactPerson(party.getContactPerson());
        organisation.setCompaniesHouseNumber(party.getCompaniesHouseNumber());

        return organisation;
    }

    public Representative representativeFrom(CcdApplicant ccdClaimant) {
        if (isBlank(ccdClaimant.getRepresentativeOrganisationName())
            && ccdClaimant.getRepresentativeOrganisationAddress() == null
            && isBlank(ccdClaimant.getRepresentativeOrganisationEmail())
            && isBlank(ccdClaimant.getRepresentativeOrganisationPhone())
            && isBlank(ccdClaimant.getRepresentativeOrganisationDxAddress())
        ) {
            return null;
        }

        Representative representative = new Representative();
        representative.setOrganisationName(ccdClaimant.getRepresentativeOrganisationName());
        representative.setOrganisationAddress(addressConverter.from(ccdClaimant.getRepresentativeOrganisationAddress()));
        representative.setOrganisationContactDetails(contactDetailsFrom(ccdClaimant));

        return representative;

    }

    public ContactDetails contactDetailsFrom(CcdApplicant ccdClaimant) {
        if (isBlank(ccdClaimant.getRepresentativeOrganisationPhone())
            && isBlank(ccdClaimant.getRepresentativeOrganisationEmail())
            && ccdClaimant.getRepresentativeOrganisationDxAddress() == null
        ) {
            return null;
        }

        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setEmail(ccdClaimant.getRepresentativeOrganisationEmail());
        contactDetails.setPhone(ccdClaimant.getRepresentativeOrganisationPhone());
        contactDetails.setDxAddress(ccdClaimant.getRepresentativeOrganisationDxAddress());

        return contactDetails;
    }
}
