package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdClaimant;
import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.AddressMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.exception.MappingException;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Company;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Individual;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Organisation;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.Party;
import uk.gov.hmcts.reform.cmc.submit.domain.models.claimants.SoleTrader;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;

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

    public List<Party> from(List<CcdCollectionElement<CcdClaimant>> ccdEvidence) {
        if (ccdEvidence == null) {
            return new ArrayList<>();
        }

        return ccdEvidence.stream()
                .filter(Objects::nonNull)
                .map(this::from)
                .collect(Collectors.toList());
    }

    private Party from(CcdCollectionElement<CcdClaimant> ccdClaimant) {
        switch (ccdClaimant.getValue().getPartyType()) {
            case COMPANY:
                return companyFrom(ccdClaimant);
            case INDIVIDUAL:
                return individualFrom(ccdClaimant);
            case SOLE_TRADER:
                return soleTraderFrom(ccdClaimant);
            case ORGANISATION:
                return organisationFrom(ccdClaimant);
            default:
                throw new MappingException("Invalid claimant type, " + ccdClaimant.getValue().getPartyType());
        }
    }

    public Company companyFrom(CcdCollectionElement<CcdClaimant> claimant) {
        CcdClaimant value = claimant.getValue();
        Company company = new Company();

        company.setId(claimant.getId());
        company.setName(value.getPartyName());
        company.setAddress(addressMapper.from(value.getPartyAddress()));
        company.setCorrespondenceAddress(addressMapper.from(value.getPartyCorrespondenceAddress()));
        company.setMobilePhone(value.getPartyPhone());
        company.setRepresentative(representativeFrom(value));
        company.setContactPerson(value.getPartyContactPerson());

        return company;
    }

    public Individual individualFrom(CcdCollectionElement<CcdClaimant> claimant) {
        CcdClaimant value = claimant.getValue();

        Individual individual = new Individual();
        individual.setId(claimant.getId());
        individual.setName(value.getPartyName());
        individual.setAddress(addressMapper.from(value.getPartyAddress()));
        individual.setCorrespondenceAddress(addressMapper.from(value.getPartyCorrespondenceAddress()));
        individual.setMobilePhone(value.getPartyPhone());
        individual.setRepresentative(representativeFrom(value));
        individual.setDateOfBirth(value.getPartyDateOfBirth());

        return individual;
    }

    public SoleTrader soleTraderFrom(CcdCollectionElement<CcdClaimant> claimant) {
        CcdClaimant value = claimant.getValue();
        SoleTrader soletrader = new SoleTrader();

        soletrader.setId(claimant.getId());
        soletrader.setName(value.getPartyName());
        soletrader.setAddress(addressMapper.from(value.getPartyAddress()));
        soletrader.setCorrespondenceAddress(addressMapper.from(value.getPartyCorrespondenceAddress()));
        soletrader.setMobilePhone(value.getPartyPhone());
        soletrader.setRepresentative(representativeFrom(value));
        soletrader.setTitle(value.getPartyTitle());
        soletrader.setBusinessName(value.getPartyBusinessName());

        return soletrader;
    }

    public Organisation organisationFrom(CcdCollectionElement<CcdClaimant> claimant) {
        if (claimant == null) return null;

        CcdClaimant value = claimant.getValue();

        Organisation organisation = new Organisation();

        organisation.setId(claimant.getId());
        organisation.setName(value.getPartyName());
        organisation.setAddress(addressMapper.from(value.getPartyAddress()));
        organisation.setCorrespondenceAddress(addressMapper.from(value.getPartyCorrespondenceAddress()));
        organisation.setMobilePhone(value.getPartyPhone());
        organisation.setRepresentative(representativeFrom(value));
        organisation.setContactPerson(value.getPartyContactPerson());
        organisation.setCompaniesHouseNumber(value.getPartyCompaniesHouseNumber());

        return organisation;
    }

    public Representative representativeFrom(CcdClaimant ccdClaimant) {
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

    public ContactDetails contactDetailsFrom(CcdClaimant ccdClaimant) {
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
