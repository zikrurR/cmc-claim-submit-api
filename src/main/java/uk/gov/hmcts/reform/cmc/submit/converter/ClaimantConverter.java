package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdApplicant;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdPartyType;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;
import uk.gov.hmcts.reform.cmc.submit.mapper.AddressMapper;
import uk.gov.hmcts.reform.cmc.submit.mapper.exception.MappingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
class ClaimantConverter {

    private final AddressMapper addressMapper;

    public ClaimantConverter(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public List<Party> from(List<CcdCollectionElement<CcdApplicant>> ccdEvidence) {
        if (ccdEvidence == null) {
            return new ArrayList<>();
        }

        return ccdEvidence.stream()
                .filter(Objects::nonNull)
                .map(this::from)
                .collect(Collectors.toList());
    }

    private Party from(CcdCollectionElement<CcdApplicant> ccdApplicant) {
        CcdPartyType type = ccdApplicant.getValue().getPartyDetail().getType();
        switch (type) {
            case COMPANY:
                return companyFrom(ccdApplicant);
            case INDIVIDUAL:
                return individualFrom(ccdApplicant);
            case SOLE_TRADER:
                return soleTraderFrom(ccdApplicant);
            case ORGANISATION:
                return organisationFrom(ccdApplicant);
            default:
                throw new MappingException("Invalid claimant type, " + type);
        }
    }

    public Company companyFrom(CcdCollectionElement<CcdApplicant> claimant) {
        CcdApplicant value = claimant.getValue();
        Company company = new Company();

        company.setId(claimant.getId());
        company.setName(value.getPartyName());
        company.setAddress(addressMapper.from(value.getPartyDetail().getPrimaryAddress()));
        company.setCorrespondenceAddress(addressMapper.from(value.getPartyDetail().getCorrespondenceAddress()));
        company.setMobilePhone(value.getPartyDetail().getTelephoneNumber().getTelephoneNumber());
        company.setRepresentative(representativeFrom(value));
        company.setContactPerson(value.getPartyDetail().getContactPerson());

        return company;
    }

    public Individual individualFrom(CcdCollectionElement<CcdApplicant> claimant) {
        CcdApplicant value = claimant.getValue();

        Individual individual = new Individual();
        individual.setId(claimant.getId());
        individual.setName(value.getPartyName());
        individual.setAddress(addressMapper.from(value.getPartyDetail().getPrimaryAddress()));
        individual.setCorrespondenceAddress(addressMapper.from(value.getPartyDetail().getCorrespondenceAddress()));
        individual.setMobilePhone(value.getPartyDetail().getTelephoneNumber().getTelephoneNumber());
        individual.setRepresentative(representativeFrom(value));
        individual.setDateOfBirth(value.getPartyDetail().getDateOfBirth());

        return individual;
    }

    public SoleTrader soleTraderFrom(CcdCollectionElement<CcdApplicant> claimant) {
        CcdApplicant value = claimant.getValue();
        SoleTrader soletrader = new SoleTrader();

        soletrader.setId(claimant.getId());
        soletrader.setName(value.getPartyName());
        soletrader.setAddress(addressMapper.from(value.getPartyDetail().getPrimaryAddress()));
        soletrader.setCorrespondenceAddress(addressMapper.from(value.getPartyDetail().getCorrespondenceAddress()));
        soletrader.setMobilePhone(value.getPartyDetail().getTelephoneNumber().getTelephoneNumber());
        soletrader.setRepresentative(representativeFrom(value));
        soletrader.setTitle(value.getPartyDetail().getTitle());
        soletrader.setBusinessName(value.getPartyDetail().getBusinessName());

        return soletrader;
    }

    public Organisation organisationFrom(CcdCollectionElement<CcdApplicant> claimant) {
        if (claimant == null) {
            return null;
        }

        CcdApplicant value = claimant.getValue();

        Organisation organisation = new Organisation();

        organisation.setId(claimant.getId());
        organisation.setName(value.getPartyName());
        organisation.setAddress(addressMapper.from(value.getPartyDetail().getPrimaryAddress()));
        organisation.setCorrespondenceAddress(addressMapper.from(value.getPartyDetail().getCorrespondenceAddress()));
        organisation.setMobilePhone(value.getPartyDetail().getTelephoneNumber().getTelephoneNumber());
        organisation.setRepresentative(representativeFrom(value));
        organisation.setContactPerson(value.getPartyDetail().getContactPerson());
        organisation.setCompaniesHouseNumber(value.getPartyDetail().getCompaniesHouseNumber());

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
        representative.setOrganisationAddress(addressMapper.from(ccdClaimant.getRepresentativeOrganisationAddress()));
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
