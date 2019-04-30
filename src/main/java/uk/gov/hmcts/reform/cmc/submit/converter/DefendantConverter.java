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
import uk.gov.hmcts.reform.cmc.submit.mapper.exception.MappingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
class DefendantConverter {

    private final CommonAddressConverter addressConverter;

    @Autowired
    public DefendantConverter(CommonAddressConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    public List<TheirDetails> from(List<CcdCollectionElement<CcdRespondent>> ccdRespondent) {
        if (ccdRespondent == null) {
            return new ArrayList<>();
        }

        return ccdRespondent.stream().filter(Objects::nonNull).map(this::from).collect(Collectors.toList());
    }


    private TheirDetails from(CcdCollectionElement<CcdRespondent> ccdRespondent) {
        switch (ccdRespondent.getValue().getClaimantProvidedDetail().getType()) {
            case COMPANY:
                return companyDetailsFrom(ccdRespondent);
            case INDIVIDUAL:
                return individualDetailsFrom(ccdRespondent);
            case SOLE_TRADER:
                return soleTraderDetailsFrom(ccdRespondent);
            case ORGANISATION:
                return organisationDetailsFrom(ccdRespondent);
            default:
                throw new MappingException("Invalid defendant type, "
                    + ccdRespondent.getValue().getClaimantProvidedDetail().getType());
        }
    }

    private CompanyDetails companyDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {
        CcdRespondent ccdDefendant = collectionElement.getValue();

        CcdParty claimantProvidedDetail = ccdDefendant.getClaimantProvidedDetail();

        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setId(collectionElement.getId());
        companyDetails.setName(ccdDefendant.getClaimantProvidedPartyName());
        companyDetails.setAddress(addressConverter.from(claimantProvidedDetail.getPrimaryAddress()));
        companyDetails.setEmail(claimantProvidedDetail.getEmailAddress());
        companyDetails.setRepresentative(representativeFrom(ccdDefendant));
        companyDetails.setServiceAddress(addressConverter.from(claimantProvidedDetail.getCorrespondenceAddress()));
        companyDetails.setContactPerson(claimantProvidedDetail.getContactPerson());

        return companyDetails;
    }

    private IndividualDetails individualDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {
        CcdRespondent ccdDefendant = collectionElement.getValue();
        CcdParty claimantProvidedDetail = ccdDefendant.getClaimantProvidedDetail();

        IndividualDetails individualDetails = new IndividualDetails();
        individualDetails.setId(collectionElement.getId());
        individualDetails.setTitle(claimantProvidedDetail.getTitle());
        individualDetails.setFirstName(claimantProvidedDetail.getFirstName());
        individualDetails.setLastName(claimantProvidedDetail.getLastName());
        individualDetails.setAddress(addressConverter.from(claimantProvidedDetail.getPrimaryAddress()));
        individualDetails.setEmail(claimantProvidedDetail.getEmailAddress());
        individualDetails.setRepresentative(representativeFrom(ccdDefendant));
        individualDetails.setServiceAddress(addressConverter.from(claimantProvidedDetail.getCorrespondenceAddress()));
        individualDetails.setDateOfBirth(claimantProvidedDetail.getDateOfBirth());

        return individualDetails;
    }

    private SoleTraderDetails soleTraderDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {

        CcdRespondent ccdDefendant = collectionElement.getValue();
        CcdParty claimantProvidedDetail = ccdDefendant.getClaimantProvidedDetail();

        SoleTraderDetails ccdSoleTrader = new SoleTraderDetails();
        ccdSoleTrader.setId(collectionElement.getId());
        ccdSoleTrader.setTitle(claimantProvidedDetail.getTitle());
        ccdSoleTrader.setFirstName(claimantProvidedDetail.getFirstName());
        ccdSoleTrader.setLastName(claimantProvidedDetail.getLastName());
        ccdSoleTrader.setAddress(addressConverter.from(claimantProvidedDetail.getPrimaryAddress()));
        ccdSoleTrader.setEmail(claimantProvidedDetail.getEmailAddress());
        ccdSoleTrader.setRepresentative(representativeFrom(ccdDefendant));
        ccdSoleTrader.setServiceAddress(addressConverter.from(claimantProvidedDetail.getCorrespondenceAddress()));

        ccdSoleTrader.setTitle(claimantProvidedDetail.getTitle());
        ccdSoleTrader.setBusinessName(claimantProvidedDetail.getBusinessName());

        return ccdSoleTrader;
    }

    private OrganisationDetails organisationDetailsFrom(CcdCollectionElement<CcdRespondent> collectionElement) {

        CcdRespondent ccdDefendant = collectionElement.getValue();
        CcdParty claimantProvidedDetail = ccdDefendant.getClaimantProvidedDetail();

        OrganisationDetails organisationDetails = new OrganisationDetails();
        organisationDetails.setId(collectionElement.getId());
        organisationDetails.setName(ccdDefendant.getClaimantProvidedPartyName());
        organisationDetails.setAddress(addressConverter.from(claimantProvidedDetail.getPrimaryAddress()));
        organisationDetails.setEmail(claimantProvidedDetail.getEmailAddress());
        organisationDetails.setRepresentative(representativeFrom(ccdDefendant));
        organisationDetails.setServiceAddress(addressConverter.from(claimantProvidedDetail.getCorrespondenceAddress()));

        organisationDetails.setContactPerson(claimantProvidedDetail.getContactPerson());
        organisationDetails.setCompaniesHouseNumber(claimantProvidedDetail.getCompaniesHouseNumber());

        return organisationDetails;
    }

    private Representative representativeFrom(CcdRespondent ccdRespondent) {
        if (isBlank(ccdRespondent.getClaimantProvidedRepresentativeOrganisationName())
            && ccdRespondent.getClaimantProvidedRepresentativeOrganisationAddress() == null
            && isBlank(ccdRespondent.getClaimantProvidedRepresentativeOrganisationPhone())
            && isBlank(ccdRespondent.getClaimantProvidedRepresentativeOrganisationDxAddress())
            && isBlank(ccdRespondent.getClaimantProvidedRepresentativeOrganisationEmail())
        ) {
            return null;
        }

        CcdAddress claimantProvidedRepOrgAddress = ccdRespondent.getClaimantProvidedRepresentativeOrganisationAddress();

        Representative representative = new Representative();
        representative.setOrganisationName(ccdRespondent.getClaimantProvidedRepresentativeOrganisationName());
        representative.setOrganisationAddress(addressConverter.from(claimantProvidedRepOrgAddress));
        representative.setOrganisationContactDetails(contactDetailsFrom(ccdRespondent));

        return representative;
    }

    private ContactDetails contactDetailsFrom(CcdRespondent ccdRespondent) {
        if (isBlank(ccdRespondent.getClaimantProvidedRepresentativeOrganisationPhone())
            && isBlank(ccdRespondent.getClaimantProvidedRepresentativeOrganisationEmail())
            && ccdRespondent.getClaimantProvidedRepresentativeOrganisationDxAddress() == null
        ) {
            return null;
        }

        ContactDetails contractDetails = new ContactDetails();

        contractDetails.setPhone(ccdRespondent.getClaimantProvidedRepresentativeOrganisationPhone());
        contractDetails.setEmail(ccdRespondent.getClaimantProvidedRepresentativeOrganisationEmail());
        contractDetails.setDxAddress(ccdRespondent.getClaimantProvidedRepresentativeOrganisationDxAddress());

        return contractDetails;
    }
}
