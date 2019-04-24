package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdRespondent;
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

    public List<TheirDetails> from(List<CcdCollectionElement<CcdRespondent>> defendant) {
        if (defendant == null) {
            return new ArrayList<>();
        }

        return defendant.stream().filter(Objects::nonNull).map(this::from).collect(Collectors.toList());
    }


    private TheirDetails from(CcdCollectionElement<CcdRespondent> ccdDefendant) {
        switch (ccdDefendant.getValue().getClaimantProvidedDetail().getType()) {
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
                    + ccdDefendant.getValue().getClaimantProvidedDetail().getType());
        }
    }

    private CompanyDetails companyDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {
        CcdRespondent ccdDefendant = collectionElement.getValue();

        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setId(collectionElement.getId());
        companyDetails.setName(ccdDefendant.getClaimantProvidedPartyName());
        companyDetails.setAddress(addressMapper.from(ccdDefendant.getClaimantProvidedDetail().getPrimaryAddress()));
        companyDetails.setEmail(ccdDefendant.getClaimantProvidedDetail().getEmailAddress());
        companyDetails.setRepresentative(representativeFrom(ccdDefendant));
        companyDetails.setServiceAddress(addressMapper.from(ccdDefendant.getClaimantProvidedDetail().getCorrespondenceAddress()));
        companyDetails.setContactPerson(ccdDefendant.getClaimantProvidedDetail().getContactPerson());

        return companyDetails;
    }

    private IndividualDetails individualDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {
        CcdRespondent ccdDefendant = collectionElement.getValue();

        IndividualDetails individualDetails = new IndividualDetails();
        individualDetails.setId(collectionElement.getId());
        individualDetails.setName(ccdDefendant.getClaimantProvidedPartyName());
        individualDetails.setAddress(addressMapper.from(ccdDefendant.getClaimantProvidedDetail().getPrimaryAddress()));
        individualDetails.setEmail(ccdDefendant.getClaimantProvidedDetail().getEmailAddress());
        individualDetails.setRepresentative(representativeFrom(ccdDefendant));
        individualDetails.setServiceAddress(addressMapper.from(ccdDefendant.getClaimantProvidedDetail().getCorrespondenceAddress()));
        individualDetails.setDateOfBirth(ccdDefendant.getClaimantProvidedDetail().getDateOfBirth());

        return individualDetails;
    }

    private SoleTraderDetails soleTraderDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {
        if (collectionElement == null) {
            return null;
        }

        CcdRespondent ccdDefendant = collectionElement.getValue();

        SoleTraderDetails ccdSoleTrader = new SoleTraderDetails();
        ccdSoleTrader.setId(collectionElement.getId());
        ccdSoleTrader.setName(ccdDefendant.getClaimantProvidedPartyName());
        ccdSoleTrader.setAddress(addressMapper.from(ccdDefendant.getClaimantProvidedDetail().getPrimaryAddress()));
        ccdSoleTrader.setEmail(ccdDefendant.getClaimantProvidedDetail().getEmailAddress());
        ccdSoleTrader.setRepresentative(representativeFrom(ccdDefendant));
        ccdSoleTrader.setServiceAddress(addressMapper.from(ccdDefendant.getClaimantProvidedDetail().getCorrespondenceAddress()));

        ccdSoleTrader.setTitle(ccdDefendant.getClaimantProvidedDetail().getTitle());
        ccdSoleTrader.setBusinessName(ccdDefendant.getClaimantProvidedDetail().getBusinessName());

        return ccdSoleTrader;
    }

    private OrganisationDetails organisationDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {
        if (collectionElement == null) {
            return null;
        }

        CcdRespondent ccdDefendant = collectionElement.getValue();

        OrganisationDetails organisationDetails = new OrganisationDetails();
        organisationDetails.setId(collectionElement.getId());
        organisationDetails.setName(ccdDefendant.getClaimantProvidedPartyName());
        organisationDetails.setAddress(addressMapper.from(ccdDefendant.getClaimantProvidedDetail().getPrimaryAddress()));
        organisationDetails.setEmail(ccdDefendant.getClaimantProvidedDetail().getEmailAddress());
        organisationDetails.setRepresentative(representativeFrom(ccdDefendant));
        organisationDetails.setServiceAddress(addressMapper.from(ccdDefendant.getClaimantProvidedDetail().getCorrespondenceAddress()));

        organisationDetails.setContactPerson(ccdDefendant.getClaimantProvidedDetail().getContactPerson());
        organisationDetails.setCompaniesHouseNumber(ccdDefendant.getClaimantProvidedDetail().getCompaniesHouseNumber());

        return organisationDetails;
    }

    private Representative representativeFrom(CcdRespondent ccdDefendant) {
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

    private ContactDetails contactDetailsFrom(CcdRespondent ccdDefendant) {
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
