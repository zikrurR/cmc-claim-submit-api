package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.cmc.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.cmc.ccd.domain.defendant.CcdDefendant;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.AddressMapper;
import uk.gov.hmcts.reform.cmc.submit.ccd.mapper.exception.MappingException;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
class DefendantConverter {

    private final AddressMapper addressMapper;

    @Autowired
    public DefendantConverter(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public List<TheirDetails> from(List<CcdCollectionElement<CcdDefendant>> defendant) {
        if (defendant == null) {
            return new ArrayList<>();
        }

        return defendant.stream().filter(Objects::nonNull).map(this::from).collect(Collectors.toList());
    }


    private TheirDetails from(CcdCollectionElement<CcdDefendant> ccdDefendant) {
        switch (ccdDefendant.getValue().getClaimantProvidedType()) {
            case COMPANY:
                return companyDetailsFrom(ccdDefendant);
            case INDIVIDUAL:
                return individualDetailsFrom(ccdDefendant);
            case SOLE_TRADER:
                return soleTraderDetailsFrom(ccdDefendant);
            case ORGANISATION:
                return organisationDetailsFrom(ccdDefendant);
            default:
                throw new MappingException("Invalid defendant type, "
                    + ccdDefendant.getValue().getClaimantProvidedType());
        }
    }

    private CompanyDetails companyDetailsFrom(CcdCollectionElement<CcdDefendant> collectionElement) {
        CcdDefendant ccdDefendant = collectionElement.getValue();

        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setId(collectionElement.getId());
        companyDetails.setName(ccdDefendant.getClaimantProvidedName());
        companyDetails.setAddress(addressMapper.from(ccdDefendant.getClaimantProvidedAddress()));
        companyDetails.setEmail(ccdDefendant.getClaimantProvidedEmail());
        companyDetails.setRepresentative(representativeFrom(ccdDefendant));
        companyDetails.setServiceAddress(addressMapper.from(ccdDefendant.getClaimantProvidedServiceAddress()));
        companyDetails.setContactPerson(ccdDefendant.getClaimantProvidedContactPerson());

        return companyDetails;
    }

    private IndividualDetails individualDetailsFrom(CcdCollectionElement<CcdDefendant> defendant) {
        CcdDefendant value = defendant.getValue();

        IndividualDetails individualDetails = new IndividualDetails();
        individualDetails.setId(defendant.getId());
        individualDetails.setName(value.getClaimantProvidedName());
        individualDetails.setAddress(addressMapper.from(value.getClaimantProvidedAddress()));
        individualDetails.setEmail(value.getClaimantProvidedEmail());
        individualDetails.setRepresentative(representativeFrom(value));
        individualDetails.setServiceAddress(addressMapper.from(value.getClaimantProvidedServiceAddress()));
        individualDetails.setDateOfBirth(value.getClaimantProvidedDateOfBirth());

        return individualDetails;
    }

    private SoleTraderDetails soleTraderDetailsFrom(CcdCollectionElement<CcdDefendant> defendant) {
        if (defendant == null) {
            return null;
        }

        CcdDefendant value = defendant.getValue();

        SoleTraderDetails ccdSoleTrader = new SoleTraderDetails();
        ccdSoleTrader.setId(defendant.getId());
        ccdSoleTrader.setName(value.getClaimantProvidedName());
        ccdSoleTrader.setAddress(addressMapper.from(value.getClaimantProvidedAddress()));
        ccdSoleTrader.setEmail(value.getClaimantProvidedEmail());
        ccdSoleTrader.setRepresentative(representativeFrom(value));
        ccdSoleTrader.setServiceAddress(addressMapper.from(value.getClaimantProvidedServiceAddress()));
        ccdSoleTrader.setTitle(value.getClaimantProvidedTitle());
        ccdSoleTrader.setBusinessName(value.getClaimantProvidedBusinessName());

        return ccdSoleTrader;
    }

    private OrganisationDetails organisationDetailsFrom(CcdCollectionElement<CcdDefendant> defendant) {
        if (defendant == null) {
            return null;
        }

        CcdDefendant value = defendant.getValue();

        OrganisationDetails organisationDetails = new OrganisationDetails();
        organisationDetails.setId(defendant.getId());
        organisationDetails.setName(value.getClaimantProvidedName());
        organisationDetails.setAddress(addressMapper.from(value.getClaimantProvidedAddress()));
        organisationDetails.setEmail(value.getClaimantProvidedEmail());
        organisationDetails.setRepresentative(representativeFrom(value));
        organisationDetails.setServiceAddress(addressMapper.from(value.getClaimantProvidedServiceAddress()));
        organisationDetails.setContactPerson(value.getClaimantProvidedContactPerson());
        organisationDetails.setCompaniesHouseNumber(value.getClaimantProvidedCompaniesHouseNumber());

        return organisationDetails;
    }

    private Representative representativeFrom(CcdDefendant ccdDefendant) {
        if (isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationName())
            && ccdDefendant.getClaimantProvidedRepresentativeOrganisationAddress() == null
            && isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationPhone())
            && isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationDxAddress())
            && isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationEmail())
        ) {
            return null;
        }

        Representative representative = new Representative();
        representative.setOrganisationName(ccdDefendant.getClaimantProvidedRepresentativeOrganisationName());
        representative.setOrganisationAddress(addressMapper.from(ccdDefendant.getClaimantProvidedRepresentativeOrganisationAddress()));
        representative.setOrganisationContactDetails(contactDetailsFrom(ccdDefendant));

        return representative;
    }

    private ContactDetails contactDetailsFrom(CcdDefendant ccdDefendant) {
        if (isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationPhone())
            && isBlank(ccdDefendant.getClaimantProvidedRepresentativeOrganisationEmail())
            && ccdDefendant.getClaimantProvidedRepresentativeOrganisationDxAddress() == null
        ) {
            return null;
        }

        ContactDetails build = new ContactDetails();

        build.setPhone(ccdDefendant.getClaimantProvidedRepresentativeOrganisationPhone());
        build.setEmail(ccdDefendant.getClaimantProvidedRepresentativeOrganisationEmail());
        build.setDxAddress(ccdDefendant.getClaimantProvidedRepresentativeOrganisationDxAddress());

        return build;
    }
}
