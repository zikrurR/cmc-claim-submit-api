package uk.gov.hmcts.reform.cmc.submit.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdAddress;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdCollectionElement;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdParty;
import uk.gov.hmcts.reform.cmc.submit.ccd.domain.CcdRespondent;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.ContactDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.common.Representative;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.CompanyDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.IndividualDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.OrganisationDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.SoleTraderDetails;
import uk.gov.hmcts.reform.cmc.submit.domain.models.defendants.TheirDetails;
import uk.gov.hmcts.reform.cmc.submit.mapper.AddressMapper;
import uk.gov.hmcts.reform.cmc.submit.mapper.exception.MappingException;

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

        CcdParty claimantProvidedDetail = ccdDefendant.getClaimantProvidedDetail();

        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setId(collectionElement.getId());
        companyDetails.setName(ccdDefendant.getClaimantProvidedPartyName());
        companyDetails.setAddress(addressMapper.from(claimantProvidedDetail.getPrimaryAddress()));
        companyDetails.setEmail(claimantProvidedDetail.getEmailAddress());
        companyDetails.setRepresentative(representativeFrom(ccdDefendant));
        companyDetails.setServiceAddress(addressMapper.from(claimantProvidedDetail.getCorrespondenceAddress()));
        companyDetails.setContactPerson(claimantProvidedDetail.getContactPerson());

        return companyDetails;
    }

    private IndividualDetails individualDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {
        CcdRespondent ccdDefendant = collectionElement.getValue();
        CcdParty claimantProvidedDetail = ccdDefendant.getClaimantProvidedDetail();

        IndividualDetails individualDetails = new IndividualDetails();
        individualDetails.setId(collectionElement.getId());
        individualDetails.setName(ccdDefendant.getClaimantProvidedPartyName());
        individualDetails.setAddress(addressMapper.from(claimantProvidedDetail.getPrimaryAddress()));
        individualDetails.setEmail(claimantProvidedDetail.getEmailAddress());
        individualDetails.setRepresentative(representativeFrom(ccdDefendant));
        individualDetails.setServiceAddress(addressMapper.from(claimantProvidedDetail.getCorrespondenceAddress()));
        individualDetails.setDateOfBirth(claimantProvidedDetail.getDateOfBirth());

        return individualDetails;
    }

    private SoleTraderDetails soleTraderDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {
        if (collectionElement == null) {
            return null;
        }

        CcdRespondent ccdDefendant = collectionElement.getValue();
        CcdParty claimantProvidedDetail = ccdDefendant.getClaimantProvidedDetail();

        SoleTraderDetails ccdSoleTrader = new SoleTraderDetails();
        ccdSoleTrader.setId(collectionElement.getId());
        ccdSoleTrader.setName(ccdDefendant.getClaimantProvidedPartyName());
        ccdSoleTrader.setAddress(addressMapper.from(claimantProvidedDetail.getPrimaryAddress()));
        ccdSoleTrader.setEmail(claimantProvidedDetail.getEmailAddress());
        ccdSoleTrader.setRepresentative(representativeFrom(ccdDefendant));
        ccdSoleTrader.setServiceAddress(addressMapper.from(claimantProvidedDetail.getCorrespondenceAddress()));

        ccdSoleTrader.setTitle(claimantProvidedDetail.getTitle());
        ccdSoleTrader.setBusinessName(claimantProvidedDetail.getBusinessName());

        return ccdSoleTrader;
    }

    private OrganisationDetails organisationDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {
        if (collectionElement == null) {
            return null;
        }

        CcdRespondent ccdDefendant = collectionElement.getValue();
        CcdParty claimantProvidedDetail = ccdDefendant.getClaimantProvidedDetail();

        OrganisationDetails organisationDetails = new OrganisationDetails();
        organisationDetails.setId(collectionElement.getId());
        organisationDetails.setName(ccdDefendant.getClaimantProvidedPartyName());
        organisationDetails.setAddress(addressMapper.from(claimantProvidedDetail.getPrimaryAddress()));
        organisationDetails.setEmail(claimantProvidedDetail.getEmailAddress());
        organisationDetails.setRepresentative(representativeFrom(ccdDefendant));
        organisationDetails.setServiceAddress(addressMapper.from(claimantProvidedDetail.getCorrespondenceAddress()));

        organisationDetails.setContactPerson(claimantProvidedDetail.getContactPerson());
        organisationDetails.setCompaniesHouseNumber(claimantProvidedDetail.getCompaniesHouseNumber());

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

        CcdAddress claimantProvidedRepOrgAddress = ccdDefendant.getClaimantProvidedRepresentativeOrganisationAddress();

        Representative representative = new Representative();
        representative.setOrganisationName(ccdDefendant.getClaimantProvidedRepresentativeOrganisationName());
        representative.setOrganisationAddress(addressMapper.from(claimantProvidedRepOrgAddress));
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
